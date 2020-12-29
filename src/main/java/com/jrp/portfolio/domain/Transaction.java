package com.jrp.portfolio.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Value
public class Transaction {

  LocalDate date;
  String description;
  String symbol;
  int qty;

  @Field(targetType = FieldType.DECIMAL128)
  BigDecimal debit;

  @Field(targetType = FieldType.DECIMAL128)
  BigDecimal credit;

  @Field(targetType = FieldType.DECIMAL128)
  BigDecimal balance;

  public boolean isDividend() {
    return "Div".equals(description);
  }

}
