package com.jrp.portfolio.repository;

import com.jrp.portfolio.domain.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
