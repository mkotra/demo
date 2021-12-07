package pl.mkotra.demo.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Transaction {
    private String id;
    private String customerId;
    private BigDecimal amount;
    private OffsetDateTime timestamp;

    public Transaction() {}

    public Transaction(String id, String customerId, BigDecimal amount, OffsetDateTime timestamp) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.timestamp = timestamp;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
