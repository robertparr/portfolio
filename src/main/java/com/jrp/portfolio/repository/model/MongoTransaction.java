package com.jrp.portfolio.repository.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document("transactions")
@Value
public class MongoTransaction {

  @Id
  String id;
  LocalDate date;
  String description;
  String symbol;
  int qty;

  @Field(targetType = FieldType.DECIMAL128)
  BigDecimal amount;

  @Field(targetType = FieldType.DECIMAL128)
  BigDecimal balance;
}
