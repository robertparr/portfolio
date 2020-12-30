package com.jrp.portfolio.service;

import com.jrp.portfolio.domain.Holding;
import com.jrp.portfolio.domain.Transaction;
import java.util.Collection;

public interface TransactionRepository {

  void save(Collection<Transaction> transactions);

  Collection<Transaction> get();

  Collection<Holding> getHoldings();
}
