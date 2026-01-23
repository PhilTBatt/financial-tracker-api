package io.github.philtbatt.financialtrackerapi.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import io.github.philtbatt.financialtrackerapi.model.TransactionRecord;

@Service
public class DynamoDBService {
    private final DynamoDbTable<TransactionRecord> table;

    public DynamoDBService(DynamoDbEnhancedClient dynamoDbClient) {
        TableSchema<TransactionRecord> schema = TableSchema.fromBean(TransactionRecord.class);

        this.table = dynamoDbClient.table("financial-app-db", schema);

    }

    public TransactionRecord getById(String id) {
        Key key = Key.builder().partitionValue(id).build();

        return table.getItem(key);
    }
}
