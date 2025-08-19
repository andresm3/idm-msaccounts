package com.idm.challenge.accounts.msaccounts.service;

import com.idm.challenge.accounts.msaccounts.domain.dto.CreditResponse;
import java.math.BigDecimal;
import reactor.core.publisher.Mono;

public interface CreditService {

  Mono<CreditResponse> consumeClientOwnsCreditCard(String number);

}