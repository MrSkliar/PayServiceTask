package com.task.paydealerservice.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;

public class Payment
{
    Random random;
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

    public Payment()
    {
        random = new Random();
        id = UUID.randomUUID();
        fromId = UUID.randomUUID();
        toId = UUID.randomUUID();
        amount = getRandAmount();
        transactionTime = getRandTime();
    }

    BigDecimal getRandAmount()
    {
        return new BigDecimal(random.nextDouble())
                .multiply(new BigDecimal(10000))
                .setScale(2, RoundingMode.HALF_UP);
    }

    //Now - 5 min
    Instant getRandTime()
    {
        Instant now = Instant.now();
        long startSeconds = now.minusSeconds(5 * 60).getEpochSecond();
        long endSeconds = now.getEpochSecond();
        long randomSeconds = random.nextLong(startSeconds, endSeconds + 1);
        long randomNanos = random.nextInt(0, 1_000_000_000);
        return Instant.ofEpochSecond(randomSeconds, randomNanos);
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
