package com.idm.challenge.accounts.msaccounts.utils;

import com.idm.challenge.accounts.msaccounts.domain.dto.AccountRequest;
import com.idm.challenge.accounts.msaccounts.domain.model.Account;
import com.idm.challenge.accounts.msaccounts.exception.CustomInformationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Validation class.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Validations {
  /**
   * Validate fields of account.
   *
   * @param account Account object
   */
  public static Mono<AccountRequest> validateFields(AccountRequest account) {
    if (account.typeAccount().getOption() == Constants.AccountType.SAVING) {
      System.out.println(">>validateFields> Saving ");
      return validateForSavingAccount(account);
    }
    return Mono.just(account);
  }

  /**
   * Validate before creating an account.
   *
   * @param count   Number of accounts per type
   * @param account Account object
   */
  public static Mono<AccountRequest> validateCreateAccount(Long count, AccountRequest account) {
    if (account.client().getType() == Constants.ClientType.PERSONAL && count > 0) {
      return Mono.error(new
          CustomInformationException("The type of client can only have 1 account of this type"));
    } else if (account.client().getType() == Constants.ClientType.BUSINESS
        && account.typeAccount().getOption() != Constants.AccountType.CHECKING) {
      return Mono.error(new
          CustomInformationException("The type of client can only have multiple current accounts"));
    } else if (account.client().getType() == Constants.ClientType.BUSINESS
        && (account.holders() == null || account.holders().isEmpty())) {
      return Mono.error(new
          CustomInformationException("The account type requires at least one holder"));
    }
    return Mono.just(account);
  }

  private static Mono<AccountRequest> validateForSavingAccount(AccountRequest account) {
    if (account.typeAccount().getMaxTransactions() == null) {
      return Mono.error(new CustomInformationException("Field maxTransactions for "
          + "typeAccount must be required"));
    } else if (account.typeAccount().getMaxTransactions() == 0) {
      return Mono.error(new CustomInformationException("Field maxTransactions for "
          + "typeAccount must be at least 1"));
    }
    return Mono.just(account);
  }

}
