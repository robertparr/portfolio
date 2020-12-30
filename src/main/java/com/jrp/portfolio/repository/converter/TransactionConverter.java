package com.jrp.portfolio.repository.converter;

import com.jrp.portfolio.domain.Transaction;
import com.jrp.portfolio.repository.model.MongoTransaction;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransactionConverter {

  public Transaction convert(MongoTransaction mongoTransaction) {
    log.debug("enter convert():: {}", mongoTransaction);

    LocalDate date = mongoTransaction.getDate();
    String description = mongoTransaction.getDescription();
    String symbol = mongoTransaction.getSymbol();
    Integer qty = mongoTransaction.getQty() == 0 ? null : mongoTransaction.getQty();
    BigDecimal debit = mongoTransaction.getAmount().compareTo(BigDecimal.ZERO) < 0 ? mongoTransaction.getAmount().abs().setScale(2) : null;
    BigDecimal credit = mongoTransaction.getAmount().compareTo(BigDecimal.ZERO) > 0 ? mongoTransaction.getAmount().setScale(2) : null;
    BigDecimal balance = mongoTransaction.getBalance();

    Transaction transaction = new Transaction(date, description, symbol, qty, debit, credit, balance);
    log.debug("exit convert():: {}", transaction);
    return transaction;
  }
}
