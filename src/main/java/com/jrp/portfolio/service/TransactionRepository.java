package com.jrp.portfolio.service;

import com.jrp.portfolio.domain.Holding;
import com.jrp.portfolio.domain.Transaction;
import com.jrp.portfolio.repository.model.MongoTransaction;
import java.util.Collection;

public interface TransactionRepository {

  void save(Collection<Transaction> transactions);

  Collection<MongoTransaction> get();

  Collection<Holding> getHoldings();
}
