package com.jrp.portfolio.repository.model;

import java.math.BigDecimal;
import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
public class MongoHolding {

  @Id
  String symbol;
  BigDecimal bookcost;
  BigDecimal quantity;
}
