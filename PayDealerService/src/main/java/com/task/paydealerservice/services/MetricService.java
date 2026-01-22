package com.task.paydealerservice.services;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MetricService
{
    private final Counter paymentDealCounter;
    public MetricService(MeterRegistry registry) {
        this.paymentDealCounter = Counter.builder("PayDealerService.Dealed.TotalDealed")
                .description("Кол-во полученных запросов о платежах")
                .register(registry);

    }

    public void paymentDealed() {
        paymentDealCounter.increment();
    }
}
