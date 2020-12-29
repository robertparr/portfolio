package com.jrp.portfolio.domain;

import java.math.BigDecimal;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Data
@Getter
@Setter
public class DivSummary {
  
  @Id
  String symbol;
  BigDecimal div;
}
