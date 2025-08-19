package com.idm.challenge.accounts.msaccounts.domain.dto;

import com.idm.challenge.accounts.msaccounts.domain.model.Client;
import java.math.BigDecimal;

public record CreditResponse(
    String id,
    Client client,
    String number,
    boolean active,
    int type,
    BigDecimal creditTotal,
    BigDecimal creditBalance
) {}
