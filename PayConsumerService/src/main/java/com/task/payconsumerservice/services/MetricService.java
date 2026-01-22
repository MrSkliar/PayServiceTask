package com.task.payconsumerservice.services;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

@Service
public class MetricService
{
    private final Counter paymentConsumeCounter;
    private final Counter paymentFailedCounter;
    private final Timer paymentConsumeTimer;
    public MetricService(MeterRegistry registry) {
        this.paymentConsumeCounter = Counter.builder("PayConsumerService.Consumed.TotalConsumed")
                .description("Кол-во полученных записей о платежах")
                .register(registry);

        this.paymentFailedCounter = Counter.builder("PayConsumerService.Consumed.TotalFailed")
                .description("Кол-во неполученных из-за ошибок записей о платежах")
                .register(registry);

        this.paymentConsumeTimer = Timer.builder("PayConsumerService.Consumed.Duration")
                .description("Время на получение платежа")
                .register(registry);
    }

    public void paymentConsumed() {
        paymentConsumeCounter.increment();
    }

    public void paymentConsumedWithError() {
        paymentFailedCounter.increment();
    }

    public Timer.Sample startPaymentConsumeTimer() {
        return Timer.start();
    }

    public void stopPaymentConsumeTimer(Timer.Sample sample) {
        sample.stop(paymentConsumeTimer);
    }
}
