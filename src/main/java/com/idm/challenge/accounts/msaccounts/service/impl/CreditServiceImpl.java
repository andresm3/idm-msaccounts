package com.idm.challenge.accounts.msaccounts.service.impl;

import com.idm.challenge.accounts.msaccounts.domain.dto.CreditResponse;
import com.idm.challenge.accounts.msaccounts.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CreditServiceImpl implements CreditService {

  @Autowired
  private WebClient.Builder webClient;

  @Value("${external.credits.url}")
  private String urlCredit;

  @Override
  public Mono<CreditResponse> consumeClientOwnsCreditCard(String documentNumber) {

    return webClient
        .build()
        .get()
        .uri(urlCredit + "/clientOwnsCard/{documentNumber}", documentNumber)
        .retrieve()
        .bodyToMono(CreditResponse.class)
        .map(credit -> credit)
        .switchIfEmpty(Mono.empty());
  }

}
