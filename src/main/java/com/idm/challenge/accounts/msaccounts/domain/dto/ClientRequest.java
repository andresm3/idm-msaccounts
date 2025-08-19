package com.idm.challenge.accounts.msaccounts.domain.dto;

public record ClientRequest(
    String id,
    String documentNumber,
    String firstName,
    String lastName,
    Integer type,
    Integer profile
) {}
