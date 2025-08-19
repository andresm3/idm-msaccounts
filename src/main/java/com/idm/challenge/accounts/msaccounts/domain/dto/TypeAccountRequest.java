package com.idm.challenge.accounts.msaccounts.domain.dto;

import java.math.BigDecimal;

public record TypeAccountRequest(
    Integer option,
    BigDecimal maintenance,
    Integer maxTransactions,
    BigDecimal commission,
    Integer day
) {}