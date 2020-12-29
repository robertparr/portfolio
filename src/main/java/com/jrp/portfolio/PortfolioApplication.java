package com.jrp.portfolio;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import com.jrp.portfolio.domain.DivSummary;
import com.jrp.portfolio.domain.Transaction;
import com.jrp.portfolio.repository.TransactionRepository;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;

@SpringBootApplication
public class PortfolioApplication implements CommandLineRunner {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private MongoTemplate mongoTemplate;

  public static void main(String[] args) {
    SpringApplication.run(PortfolioApplication.class, args);
  }

  @Override
  public void run(String... args) throws IOException {

    String[] HEADERS = {"author", "title"};

    Reader in = new FileReader("/Users/parrr2/transactions.csv");


    Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(HEADERS).withFirstRecordAsHeader().parse(in);


    Collection<Transaction> transactions = new ArrayList<>();

    for (CSVRecord record : records) {
      //System.out.println(record);
      LocalDate date = LocalDate.parse(record.get(0), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
      String description = record.get(1);
      String symbol = record.get(2);
      int qty = record.get(3).equals("") ? 0 : Integer.valueOf(record.get(3));
      BigDecimal debit = get(record.get(4));
      BigDecimal credit = get(record.get(5));
      BigDecimal balance = get(record.get(6));
      Transaction transaction = new Transaction(date, description, symbol, qty, debit, credit, balance);
      transactions.add(transaction);
    }

    transactionRepository.deleteAll();
    transactionRepository.saveAll(transactions);

    MatchOperation descriptionMatches = match(new Criteria("description").in("Div"));
    GroupOperation groupByStateAndSumPop = group("symbol").sum("credit").as("div");
    // MatchOperation filterStates = match(new Criteria("div").gt(0));
    SortOperation sortByPopDesc = sort(Sort.by(Direction.DESC, "div"));

    Aggregation aggregation = newAggregation(descriptionMatches, groupByStateAndSumPop, sortByPopDesc);//, filterStates, sortByPopDesc);
    AggregationResults<DivSummary> result = mongoTemplate.aggregate(aggregation, "transaction", DivSummary.class);
    System.out.println("DIV=" + result.getMappedResults());

    //var totals = transactions.stream().filter(Transaction::isDividend).collect(toMap(Transaction::getSymbol, Transaction::getCredit, BigDecimal::add));

    //System.out.println(totals);

    //SpringApplication.run(PortfolioApplication.class, args);
  }

  private static BigDecimal get(String s) {
    return "".equals(s) ? BigDecimal.ZERO : new BigDecimal(s.trim().replace(",", "").replace("£", ""));
  }

}
