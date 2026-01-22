package com.task.payconsumerservice.models;

import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Table("payment")
public class Payment
{
    UUID id;
    BigDecimal amount;
    Instant transactionTime;
    UUID fromId;
    UUID toId;
    public UUID getId() {
        return id;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public Instant getTransactionTime() {
        return transactionTime;
    }
    public UUID getFromId() {
        return fromId;
    }
    public UUID getToId() {
        return toId;
    }
    @Override
    public String toString() {
        return "ID = " + id.toString()
                + "\nFrom: " + fromId.toString()
                + "\nTo: " + toId.toString()
                + "\nAmount: " + amount.toString()
                + "\nTime: " + transactionTime.toString();
    }
}
