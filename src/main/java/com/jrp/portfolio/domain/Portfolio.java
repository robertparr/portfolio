package com.jrp.portfolio.domain;

import java.math.BigDecimal;
import java.util.Collection;
import lombok.Value;

@Value
public class Portfolio {

  Collection<Holding> holdings;
  BigDecimal totalBookcost;

  public Portfolio(Collection<Holding> holdings) {
    this.holdings = holdings;
    this.totalBookcost = holdings.stream().map(Holding::getBookcost).reduce(BigDecimal.ZERO, BigDecimal::add);
  }

}
