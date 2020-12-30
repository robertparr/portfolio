package com.jrp.portfolio.repository;

import com.jrp.portfolio.repository.model.MongoTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionMongoRepository extends MongoRepository<MongoTransaction, String> {
}
