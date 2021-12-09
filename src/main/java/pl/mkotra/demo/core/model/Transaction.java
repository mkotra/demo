package pl.mkotra.demo.core.model;

import java.math.BigDecimal;
import java.time.Month;
import java.time.OffsetDateTime;
import java.util.Optional;

public class Transaction {

    private final static BigDecimal MAX_AMOUNT_FOR_POINTS_CALCULATION = new BigDecimal("10000.00");

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

    public int month() {
        return Optional.ofNullable(timestamp)
                .map(OffsetDateTime::getMonth)
                .map(Month::getValue).orElse(0);
    }

    public int points(int threshold, int thresholdFactor, int nextThreshold) {
        return Optional.ofNullable(amount)
                .map(amount -> amount.min(MAX_AMOUNT_FOR_POINTS_CALCULATION))
                .map(BigDecimal::intValue)
                .map(amount -> Integer.min(amount, nextThreshold))
                .map(amount -> Integer.max(amount - threshold, 0))
                .map(amount -> amount * thresholdFactor)
                .orElse(0);
    }
}
