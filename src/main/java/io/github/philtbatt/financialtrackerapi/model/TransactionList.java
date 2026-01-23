package io.github.philtbatt.financialtrackerapi.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;

import java.util.List;

@DynamoDbBean
public class TransactionList {
    private String id;
    private List<TransactionItem> transactions;
    private String createdAt;

    @DynamoDbPartitionKey
    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public List<TransactionItem> getTransactions() { return this.transactions; }

    public void setTransactions(List<TransactionItem> transactions) { this.transactions = transactions;}

    @DynamoDbAttribute("created_at")
    public String getCreatedAt() { return this.createdAt; }

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

}
