package com.idm.challenge.accounts.msaccounts.service;

import com.idm.challenge.accounts.msaccounts.domain.dto.AccountRequest;
import com.idm.challenge.accounts.msaccounts.domain.model.Account;
import java.math.BigDecimal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

  Flux<Account> getAll();
  Mono<Account> createAccount(AccountRequest account);
  Mono<Account> getAccountById(String id);
  Mono<Account> getAccountByNumber(String number);
  Flux<Account> getAccountsByClientDocumentNumber(String documentNumber);
  Mono<Account> getMainAccountByClientDocumentNumber(String documentNumber);
  Flux<Account> getAccountsByClientDebitCard(String debitCard);
  Mono<BigDecimal> getTotalBalanceByDebitCard(String debitCard);

  Mono<Account> updateBalance(String id, BigDecimal amount);
}
