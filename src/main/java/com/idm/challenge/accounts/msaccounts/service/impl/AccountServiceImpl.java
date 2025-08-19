package com.idm.challenge.accounts.msaccounts.service.impl;

import com.idm.challenge.accounts.msaccounts.domain.dto.AccountRequest;
import com.idm.challenge.accounts.msaccounts.domain.model.Account;
import com.idm.challenge.accounts.msaccounts.exception.CustomInformationException;
import com.idm.challenge.accounts.msaccounts.exception.CustomNotFoundException;
import com.idm.challenge.accounts.msaccounts.repository.AccountRepository;
import com.idm.challenge.accounts.msaccounts.service.AccountService;
import com.idm.challenge.accounts.msaccounts.service.CreditService;
import com.idm.challenge.accounts.msaccounts.utils.Constants;
import com.idm.challenge.accounts.msaccounts.utils.Validations;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private CreditService creditService;

  private static final String FLUX_NOT_FOUND_MESSAGE = "Data not found";
  private static final String MONO_NOT_FOUND_MESSAGE = "Account not found";

  @Override
  public Flux<Account> getAll() {
    return accountRepository.findAll();
  }

  @Override
  public Mono<Account> createAccount(AccountRequest request) {
    return accountRepository.findByNumber(request.number())
        .doOnNext(ac -> {
          throw new CustomInformationException("Account number has already been created");
        })
        .switchIfEmpty(Validations.validateFields(request)
            .flatMap(a -> accountRepository
                .countByClientDocumentNumberAndType(request.client().getDocumentNumber(),
                    request.typeAccount().getOption())
                .flatMap(co -> Validations.validateCreateAccount(co, request))
                .flatMap(this::checkIfRequiresCreditCard)
                .map(this::mapToAccount)
                .flatMap(ac -> accountRepository.save(ac)
                    .map(c -> {
                      System.out.println(">>Created a new id = {} for the account with number=> Saving " + request.number());
                      return c;
                    })))
        );
  }

  private Account mapToAccount(AccountRequest request) {
    return Account.builder()
        .balance(request.balance())
        .status(request.status())
        .number(request.number())
        .debitCard(request.debitCard())
        .client(request.client())
        .typeAccount(request.typeAccount())
        .build();
  }
  @Override
  public Mono<Account> getAccountById(String id) {
    return accountRepository.findById(id)
        .switchIfEmpty(Mono.error(new CustomNotFoundException(MONO_NOT_FOUND_MESSAGE)));

  }


  @Override
  public Mono<Account> getAccountByNumber(String number) {
    return accountRepository.findByNumber(number)
        .switchIfEmpty(Mono.error(new CustomNotFoundException(MONO_NOT_FOUND_MESSAGE)));

  }

  @Override
  public Flux<Account> getAccountsByClientDocumentNumber(String documentNumber) {
    return accountRepository.findByClientDocumentNumber(documentNumber)
        .switchIfEmpty(Flux.error(new CustomNotFoundException(FLUX_NOT_FOUND_MESSAGE)));
  }

  @Override
  public Mono<Account> getMainAccountByClientDocumentNumber(String documentNumber) {
    return accountRepository.findByClientDocumentNumberAndPosition(documentNumber)
        .switchIfEmpty(Mono.error(new CustomNotFoundException(FLUX_NOT_FOUND_MESSAGE)));
  }

  @Override
  public Flux<Account> getAccountsByClientDebitCard(String debitCard) {
    return accountRepository.findByDebitCard(debitCard)
        .switchIfEmpty(Flux.error(new CustomNotFoundException(FLUX_NOT_FOUND_MESSAGE)));

  }

  @Override
  public Mono<BigDecimal> getTotalBalanceByDebitCard(String debitCard) {
    return accountRepository.findByDebitCard(debitCard)
        .map(Account::getBalance)
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .switchIfEmpty(Mono.error(new CustomNotFoundException(FLUX_NOT_FOUND_MESSAGE)));

  }

  private Mono<AccountRequest> checkIfRequiresCreditCard(AccountRequest account) {
    if (account.client().getType() == Constants.ClientType.PERSONAL
        && account.client().getProfile() == Constants.ClientProfile.VIP
        && account.typeAccount().getOption() == Constants.AccountType.SAVING
        || account.client().getType() == Constants.ClientType.BUSINESS
        && account.client().getProfile() == Constants.ClientProfile.PYME) {
      return creditService.consumeClientOwnsCreditCard(
              account.client().getDocumentNumber())
          .switchIfEmpty(
              Mono.error(new CustomInformationException("The account type requires "
                  + "that the client owns a credit card")))
          .flatMap(cr -> Mono.just(account));
    } else {
      return Mono.just(account);
    }
  }


  @Override
  public Mono<Account> updateBalance(String id,
      BigDecimal amount) {
    return accountRepository.findById(id)
        .flatMap(account -> {
          account.setBalance(account.getBalance().add(amount));
          System.out.println("Update balance for the account with id = {} " + account.getId());
          return accountRepository.save(account);
        })
        .switchIfEmpty(Mono.error(new CustomNotFoundException(MONO_NOT_FOUND_MESSAGE)));
  }
}