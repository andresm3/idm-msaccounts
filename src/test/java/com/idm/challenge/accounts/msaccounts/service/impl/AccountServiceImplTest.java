package com.idm.challenge.accounts.msaccounts.service.impl;

import com.idm.challenge.accounts.msaccounts.domain.dto.AccountRequest;
import com.idm.challenge.accounts.msaccounts.domain.model.Account;
import com.idm.challenge.accounts.msaccounts.exception.CustomInformationException;
import com.idm.challenge.accounts.msaccounts.exception.CustomNotFoundException;
import com.idm.challenge.accounts.msaccounts.mock.MockData;
import com.idm.challenge.accounts.msaccounts.repository.AccountRepository;
import com.idm.challenge.accounts.msaccounts.service.CreditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountServiceImplTest {

  @InjectMocks
  private AccountServiceImpl accountService;

  @Mock
  private AccountRepository accountRepository;

  @Mock
  private CreditService creditService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAll() {
    Mockito.when(accountRepository.findAll()).thenReturn(Flux.just(MockData.mockAccount()));

    Flux<Account> accounts = accountService.getAll();

    assertThat(accounts.collectList().block()).contains(MockData.mockAccount());
  }

  @Test
  void testCreateAccount() {
    AccountRequest request = MockData.mockAccountRequest();

    Mockito.when(accountRepository.findByNumber(request.number())).thenReturn(Mono.empty());
    Mockito.when(accountRepository.countByClientDocumentNumberAndType(
        request.client().getDocumentNumber(), request.typeAccount().getOption()))
        .thenReturn(Mono.just(0L));
    Mockito.when(accountRepository.save(Mockito.any(Account.class)))
        .thenReturn(Mono.just(MockData.mockAccount()));

    Account account = accountService.createAccount(request).block();

    assertThat(account).isEqualTo(MockData.mockAccount());
  }

  @Test
  void testCreateAccountThrowsExceptionForDuplicateNumber() {
    AccountRequest request = MockData.mockAccountRequest();

    Mockito.when(accountRepository.findByNumber(request.number()))
        .thenReturn(Mono.just(MockData.mockAccount()));

    assertThrows(CustomInformationException.class, () -> accountService.createAccount(request).block());
  }

  @Test
  void testGetAccountById() {
    String id = "1";

    Mockito.when(accountRepository.findById(id)).thenReturn(Mono.just(MockData.mockAccount()));

    Account account = accountService.getAccountById(id).block();

    assertThat(account).isEqualTo(MockData.mockAccount());
  }

  @Test
  void testGetAccountByIdThrowsNotFoundException() {
    String id = "1";

    Mockito.when(accountRepository.findById(id)).thenReturn(Mono.empty());

    assertThrows(CustomNotFoundException.class, () -> accountService.getAccountById(id).block());
  }

}