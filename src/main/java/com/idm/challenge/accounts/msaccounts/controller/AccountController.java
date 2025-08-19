package com.idm.challenge.accounts.msaccounts.controller;


import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

import com.idm.challenge.accounts.msaccounts.domain.dto.AccountRequest;
import com.idm.challenge.accounts.msaccounts.domain.model.Account;
import com.idm.challenge.accounts.msaccounts.service.AccountService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

  @Autowired
  private AccountService service;

  @PostMapping("/create")
  @ResponseStatus(CREATED)
  public Mono<Account> create(@RequestBody AccountRequest request) {
    return service.createAccount(request);
  }
  @GetMapping("/id/{id}")
  public Mono<Account> findById(@PathVariable String id) {
    System.out.println(">>id>>>>>>> " + id);
    return service.getAccountById(id);
  }

  @GetMapping("/number/{number}")
  public Mono<Account> findByNumber(@PathVariable String number) {
    System.out.println(">>>number>>>>>> " + number);
    return service.getAccountByNumber(number);
  }
  @GetMapping(value = "/client/documentNumber/{documentNumber}")
  public Flux<Account> listByClientDocumentNumber(@PathVariable String documentNumber) {
    return service.getAccountsByClientDocumentNumber(documentNumber);
  }

  @GetMapping(value = "/client/main/documentNumber/{documentNumber}")
  public Mono<Account> findMainAccountByClientDocumentNumber(@PathVariable String documentNumber) {
    return service.getMainAccountByClientDocumentNumber(documentNumber);
  }

  @GetMapping(value = "/debitCard/{debitCard}")
  public Flux<Account> listByDebitCard(@PathVariable String debitCard) {
    return service.getAccountsByClientDebitCard(debitCard);
  }

  @GetMapping("/totalBalance/{debitCard}")
  public Mono<BigDecimal> getTotalBalanceByDebitCard(@PathVariable String debitCard) {
    return service.getTotalBalanceByDebitCard(debitCard);
  }

  @GetMapping(value = "/all", produces = TEXT_EVENT_STREAM_VALUE)
  public Flux<Account> listAll() {
    return service.getAll();
  }

  @PutMapping("/balance/{id}/amount/{amount}")
  public Mono<Account> updateBalance(@PathVariable String id,
      @PathVariable BigDecimal amount) {
    return service.updateBalance(id, amount);
  }
}