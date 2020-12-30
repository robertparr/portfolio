package com.jrp.portfolio.repository;

import com.jrp.portfolio.repository.model.MongoTransaction;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionMongoRepository extends MongoRepository<MongoTransaction, String> {

  List<MongoTransaction> findAllByOrderByDateDesc();

  List<MongoTransaction> findAllByDescriptionOrderByDateDesc(String description);
}
