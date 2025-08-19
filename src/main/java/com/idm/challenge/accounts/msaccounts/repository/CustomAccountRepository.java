package com.idm.challenge.accounts.msaccounts.repository;

import com.idm.challenge.accounts.msaccounts.domain.model.Account;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CustomAccountRepository {
  Flux<Account> findByClientFirstName(String firstName);

  Flux<Account> findByClientFirstNameAndLastName(String firstName, String lastName);

  Flux<Account> findByClientDocumentNumber(String documentNumber);

  Flux<Account> findByDebitCard(String debitCard);

  Mono<Account> findByNumberAndClientDocumentNumber(String number, String documentNumber);

  Mono<Long> countByClientDocumentNumberAndType(String documentNumber, Integer option);

  Mono<Account> getLastByDebitCard(String debitCard);

  Mono<Account> findByClientDocumentNumberAndPosition(String documentNumber);
}