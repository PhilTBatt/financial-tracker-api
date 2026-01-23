package io.github.philtbatt.financialtrackerapi.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
public class Transaction {

    private String date;
    private Double amount;
    private String memo;
    private String payee;

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getMemo() { return memo; }
    public void setMemo(String memo) { this.memo = memo; }

    public String getPayee() { return payee; }
    public void setPayee(String payee) { this.payee = payee; }
}
