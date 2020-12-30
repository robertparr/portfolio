package com.jrp.portfolio.service;

import com.jrp.portfolio.domain.Holding;
import com.jrp.portfolio.domain.Transaction;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {

  private final TransactionRepository transactionRepository;

  //@PostConstruct
  public void startup() throws IOException {
    this.load();
  }

  public Collection<Holding> getHoldings() {
    log.info("enter getHoldings()");
    Collection<Holding> holdings = transactionRepository.getHoldings();
    log.info("exit getHoldings():: {}", holdings);
    return holdings;
  }

  public Collection<Transaction> get() {
    log.info("enter getHoldings()");
    Collection<Transaction> holdings = transactionRepository.get();
    log.info("exit getHoldings():: {}", holdings);
    return holdings;
  }

  public void load() throws IOException {
    log.info("enter load()");
    String[] HEADERS = {"author", "title"};

    Reader in = new FileReader("/Users/parrr2/transactions.csv");


    Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(HEADERS).withFirstRecordAsHeader().parse(in);


    Collection<Transaction> transactions = new ArrayList<>();

    for (CSVRecord record : records) {
      log.info("{}", record);
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

    transactionRepository.save(transactions);
    log.info("exit load()");
  }

  private static BigDecimal get(String s) {
    return "".equals(s) ? BigDecimal.ZERO : new BigDecimal(s.trim().replace(",", "").replace("£", ""));
  }
}
