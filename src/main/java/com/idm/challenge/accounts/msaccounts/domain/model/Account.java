package com.idm.challenge.accounts.msaccounts.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
@Document("accounts")
@Builder
public class Account {

  @JsonProperty("id")
  private String id;
  private String debitCard;
  private String number;
  private int position;
  private Client client;
  private TypeAccount typeAccount;
  private List<String> holders;
  private List<String> signatories;
  @Field(targetType = FieldType.DECIMAL128)
  private BigDecimal balance;
  private boolean status;
}
