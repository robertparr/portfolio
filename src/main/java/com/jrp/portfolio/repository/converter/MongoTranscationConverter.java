package com.jrp.portfolio.repository.converter;

import com.jrp.portfolio.domain.Transaction;
import com.jrp.portfolio.repository.model.MongoTransaction;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MongoTranscationConverter {

  public MongoTransaction convert(Transaction transaction) {
    log.debug("enter convert():: {}", transaction);
    String id = null;
    LocalDate date = transaction.getDate();
    String description = transaction.getDescription();
    String symbol = transaction.getSymbol();
    int qty = transaction.getQty();
    BigDecimal amount = transaction.getCredit().compareTo(BigDecimal.ZERO) > 0 ? transaction.getCredit() : transaction.getDebit().negate();
    BigDecimal balance = transaction.getBalance();
    MongoTransaction mongoTransaction = new MongoTransaction(id, date, description, symbol, qty, amount, balance);
    log.debug("exit convert():: {}", mongoTransaction);
    return mongoTransaction;
  }
}
