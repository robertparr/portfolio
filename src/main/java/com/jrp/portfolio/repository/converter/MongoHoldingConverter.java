package com.jrp.portfolio.repository.converter;

import com.jrp.portfolio.domain.Holding;
import com.jrp.portfolio.repository.model.MongoHolding;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MongoHoldingConverter {

  public Holding convert(MongoHolding mongoHolding) {
    log.debug("enter convert():: {}", mongoHolding);
    var holding = new Holding(mongoHolding.getSymbol(), mongoHolding.getBookcost().abs(), mongoHolding.getQuantity());
    log.debug("exit convert():: {}", holding);
    return holding;
  }
}
