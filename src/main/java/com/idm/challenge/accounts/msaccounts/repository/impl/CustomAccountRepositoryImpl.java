package com.idm.challenge.accounts.msaccounts.repository.impl;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.idm.challenge.accounts.msaccounts.domain.model.Account;
import com.idm.challenge.accounts.msaccounts.repository.CustomAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CustomAccountRepositoryImpl implements CustomAccountRepository {
  private static final String FIELD_CLIENT_DOCUMENT_NUMBER = "client.documentNumber";

  @Autowired
  private ReactiveMongoTemplate mongoTemplate;

  @Override
  public Flux<Account> findByClientFirstName(String firstName) {
    Query query = new Query(where("client.firstName").is(firstName));
    return mongoTemplate.find(query, Account.class);
  }

  @Override
  public Flux<Account> findByClientFirstNameAndLastName(String firstName,
      String lastName) {
    Query query = new Query(where("client.firstName").is(firstName)
        .and("client.lastName").is(lastName));
    return mongoTemplate.find(query, Account.class);
  }

  @Override
  public Flux<Account> findByClientDocumentNumber(String documentNumber) {
    Query query = new Query(where(FIELD_CLIENT_DOCUMENT_NUMBER).is(documentNumber));
    return mongoTemplate.find(query, Account.class);
  }

  @Override
  public Mono<Account> findByClientDocumentNumberAndPosition(String documentNumber) {
    Query query = new Query(where(FIELD_CLIENT_DOCUMENT_NUMBER).is(documentNumber)
        .and("position").is(0));
    return mongoTemplate.findOne(query, Account.class);
  }

  @Override
  public Flux<Account> findByDebitCard(String debitCard) {
    Query query = new Query(where("debitCard").is(debitCard));
    query.with(Sort.by(ASC, "position"));
    return mongoTemplate.find(query, Account.class);
  }

  @Override
  public Mono<Account> findByNumberAndClientDocumentNumber(String number, String documentNumber) {
    Query query = new Query(where("number").is(number)
        .and(FIELD_CLIENT_DOCUMENT_NUMBER).is(documentNumber));
    return mongoTemplate.findOne(query, Account.class);
  }

  @Override
  public Mono<Long> countByClientDocumentNumberAndType(String documentNumber, Integer option) {
    Query query = new Query(where(FIELD_CLIENT_DOCUMENT_NUMBER).is(documentNumber)
        .and("typeAccount.option").is(option));
    return mongoTemplate.count(query, Account.class);
  }

  @Override
  public Mono<Account> getLastByDebitCard(String debitCard) {
    Query query = new Query(where("debitCard").is(debitCard));
    query.limit(1);
    query.with(Sort.by(DESC, "position"));
    return mongoTemplate.findOne(query, Account.class);
  }
}
