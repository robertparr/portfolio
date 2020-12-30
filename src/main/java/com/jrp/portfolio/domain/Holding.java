package com.jrp.portfolio.domain;

import java.math.BigDecimal;
import lombok.Value;

@Value
public class Holding {
  
  String symbol;
  BigDecimal bookcost;
  BigDecimal quantity;
}
