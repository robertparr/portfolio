package com.jrp.portfolio.controller;

import com.jrp.portfolio.domain.Portfolio;
import com.jrp.portfolio.domain.Transaction;
import com.jrp.portfolio.service.TransactionService;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class TransactionController {

  private final TransactionService service;

  @GetMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
  public Collection<Transaction> get() {
    log.info("enter get()");
    return service.get();
  }

  @GetMapping(value = "/portfolio", produces = MediaType.APPLICATION_JSON_VALUE)
  public Portfolio getHoldings() {
    log.info("enter get()");
    return new Portfolio(service.getHoldings());
  }

}
