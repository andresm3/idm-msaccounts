package com.idm.challenge.accounts.msaccounts.controller;

import com.idm.challenge.accounts.msaccounts.domain.dto.AccountRequest;
import com.idm.challenge.accounts.msaccounts.domain.model.Account;
import com.idm.challenge.accounts.msaccounts.mock.MockData;
import com.idm.challenge.accounts.msaccounts.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.List;

@WebFluxTest(AccountController.class)
class AccountControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockitoBean
  private AccountService accountService;

  @Test
  void testCreateAccount() {
    AccountRequest request = new AccountRequest(
        "123456789",
        "ACC123",
        null, // Mock ClientRequest
        null, // Mock TypeAccountRequest
        List.of("Holder1"),
        List.of("Signatory1"),
        BigDecimal.valueOf(1000),
        true
    );


    Mockito.when(accountService.createAccount(request)).thenReturn(Mono.just(MockData.mockAccount()));

    webTestClient.post()
        .uri("/accounts/create")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .exchange()
        .expectStatus().isCreated()
        .expectBody(Account.class)
        .isEqualTo(MockData.mockAccount());
  }

  @Test
  void testFindById() {
    String id = "1";

    Mockito.when(accountService.getAccountById(id)).thenReturn(Mono.just(MockData.mockAccount()));

    webTestClient.get()
        .uri("/accounts/id/{id}", id)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Account.class)
        .isEqualTo(MockData.mockAccount());
  }

  @Test
  void testFindByNumber() {
    String number = "0001-0001";

    Mockito.when(accountService.getAccountByNumber(number)).thenReturn(Mono.just(MockData.mockAccount()));

    webTestClient.get()
        .uri("/accounts/number/{number}", number)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Account.class)
        .isEqualTo(MockData.mockAccount());
  }

  @Test
  void testListByClientDocumentNumber() {
    String documentNumber = "123456789";

    Mockito.when(accountService.getAccountsByClientDocumentNumber(documentNumber))
        .thenReturn(Flux.just(MockData.mockAccount()));

    webTestClient.get()
        .uri("/accounts/client/documentNumber/{documentNumber}", documentNumber)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Account.class)
        .contains(MockData.mockAccount());
  }

}