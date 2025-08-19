package com.idm.challenge.accounts.msaccounts.domain.dto;

import com.idm.challenge.accounts.msaccounts.domain.model.Client;
import com.idm.challenge.accounts.msaccounts.domain.model.TypeAccount;
import java.math.BigDecimal;
import java.util.List;

public record AccountRequest(
    String debitCard,
    String number,
    Client client,
    TypeAccount typeAccount,
    List<String> holders,
    List<String> signatories,
    BigDecimal balance,
    boolean status
) {}