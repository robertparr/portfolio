package com.jrp.portfolio.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Value;

@Value
public class Transaction {

  LocalDate date;
  String description;
  String symbol;
  Integer qty;
  BigDecimal debit;
  BigDecimal credit;
  BigDecimal balance;

  public boolean isDividend() {
    return "Div".equals(description);
  }

}
