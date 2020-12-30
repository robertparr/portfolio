package com.jrp.portfolio.repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import com.jrp.portfolio.domain.DivSummary;
import com.jrp.portfolio.domain.Holding;
import com.jrp.portfolio.domain.Transaction;
import com.jrp.portfolio.repository.converter.MongoHoldingConverter;
import com.jrp.portfolio.repository.converter.MongoTranscationConverter;
import com.jrp.portfolio.repository.model.MongoHolding;
import com.jrp.portfolio.repository.model.MongoTransaction;
import com.jrp.portfolio.service.TransactionRepository;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
@Slf4j
public class TransactionSummaryMongoRepository implements TransactionRepository {

  private final MongoTranscationConverter mongoTranscationConverter = new MongoTranscationConverter();
  private final MongoHoldingConverter mongoHoldingConverter = new MongoHoldingConverter();

  private final TransactionMongoRepository transactionMongoRepository;
  private final MongoTemplate mongoTemplate;

  public void get(Collection<Transaction> transactions) {
    MatchOperation descriptionMatches = match(new Criteria("description").in("Div"));
    GroupOperation groupByStateAndSumPop = group("symbol").sum("credit").as("div");
    // MatchOperation filterStates = match(new Criteria("div").gt(0));
    SortOperation sortByPopDesc = sort(Sort.by(Direction.DESC, "div"));

    Aggregation aggregation = newAggregation(descriptionMatches, groupByStateAndSumPop, sortByPopDesc);//, filterStates, sortByPopDesc);
    AggregationResults<DivSummary> result = mongoTemplate.aggregate(aggregation, "transaction", DivSummary.class);
    System.out.println("DIV=" + result.getMappedResults());

  }

  @Override
  public void save(Collection<Transaction> transactions) {
    log.info("enter save()");
    var mongoTransactions = transactions.stream().map(mongoTranscationConverter::convert).collect(Collectors.toList());
    transactionMongoRepository.deleteAll();
    transactionMongoRepository.saveAll(mongoTransactions);
    log.info("exit save()");
  }

  @Override
  public Collection<MongoTransaction> get() {
    log.info("enter get()");
    var mongoTransactions = transactionMongoRepository.findAll();
    log.info("exit get():: {}", mongoTransactions);
    return mongoTransactions;
  }

  @Override
  public Collection<Holding> getHoldings() {
    var collectionName = "transactions";
    MatchOperation descriptionMatches = match(new Criteria("description").in("Buy", "Sell"));
    GroupOperation groupByStateAndSumPop = group("symbol").sum("qty").as("quantity").sum("amount").as("bookcost");
    MatchOperation filterStates = match(new Criteria("quantity").gt(0));
    SortOperation sortByPopDesc = sort(Sort.by(Direction.DESC, "bookcost"));

    Aggregation aggregation = newAggregation(descriptionMatches, groupByStateAndSumPop, filterStates, sortByPopDesc);//, filterStates, sortByPopDesc);
    AggregationResults<MongoHolding> result = mongoTemplate.aggregate(aggregation, collectionName, MongoHolding.class);
    var holdings = result.getMappedResults().stream().map(mongoHoldingConverter::convert).collect(Collectors.toList());
    log.info("exit getHoldings():: {}", holdings);
    return holdings;
  }
}
