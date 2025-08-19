package com.idm.challenge.accounts.msaccounts.domain.model;

import com.idm.challenge.accounts.msaccounts.domain.dto.TypeAccountRequest;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeAccount {

  private int option;
  @Field(targetType = FieldType.DECIMAL128)
  private BigDecimal maintenance;
  private Integer maxTransactions;
  @Field(targetType = FieldType.DECIMAL128)
  private BigDecimal commission;
  private Integer day;

  public TypeAccount(TypeAccountRequest request) {
    option = request.option();
    maxTransactions = request.maxTransactions();
    maintenance = request.maintenance();
    commission = request.commission();
    day = request.day();
  }
}