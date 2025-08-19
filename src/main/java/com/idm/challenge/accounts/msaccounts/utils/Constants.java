package com.idm.challenge.accounts.msaccounts.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
  /**
   * Client types.
   */
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class ClientType {
    public static final int PERSONAL = 1;
    public static final int BUSINESS = 2;
  }

  /**
   * Client profiles.
   */
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class ClientProfile {
    public static final int REGULAR = 1;
    public static final int VIP = 2;
    public static final int PYME = 3;
  }

  /**
   * Account types.
   */
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class AccountType {
    public static final int SAVING = 1;
    public static final int CHECKING = 2;
    public static final int FIXED_TERM = 3;
  }
}
