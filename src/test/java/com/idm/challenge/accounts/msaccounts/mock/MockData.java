package com.idm.challenge.accounts.msaccounts.mock;

import com.idm.challenge.accounts.msaccounts.domain.dto.AccountRequest;
import com.idm.challenge.accounts.msaccounts.domain.model.Account;
import com.idm.challenge.accounts.msaccounts.domain.model.Client;
import com.idm.challenge.accounts.msaccounts.domain.model.TypeAccount;
import java.math.BigDecimal;
import java.util.List;

public class MockData {

  public static Client mockClient() {
    return Client.builder()
        .id("1")
        .documentNumber("123456789")
        .firstName("John")
        .lastName("Doe")
        .type(1)
        .profile(1)
        .active(true)
        .idClientCategory("category1")
        .build();
  }

  public static TypeAccount mockTypeAccount() {
    return TypeAccount.builder()
        .option(1)
        .maintenance(BigDecimal.valueOf(10.00))
        .maxTransactions(5)
        .commission(BigDecimal.valueOf(2.00))
        .day(1)
        .build();
  }
  public static Account mockAccount() {
    return Account.builder()
        .id("1")
        .debitCard("1234567890")
        .number("0001-0001")
        .client(MockData.mockClient())
        .typeAccount(MockData.mockTypeAccount())
        .holders(List.of("holder1", "holder2"))
        .signatories(List.of("signatory1", "signatory2"))
        .balance(BigDecimal.valueOf(1000.00))
        .status(true)
        .build();
  }

  public static AccountRequest mockAccountRequest() {
    return new AccountRequest(
        "1",
        "1234567890",
        MockData.mockClient(),
        MockData.mockTypeAccount(),
        List.of("holder1", "holder2"),
        List.of("signatory1", "signatory2"),
        BigDecimal.valueOf(1000.00),
        true
    );
  }
}
