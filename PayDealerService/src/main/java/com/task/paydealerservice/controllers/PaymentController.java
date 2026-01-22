package com.task.paydealerservice.controllers;

import com.task.paydealerservice.models.Payment;
import com.task.paydealerservice.services.MetricService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/payment")
public class PaymentController
{
    private final MetricService metricService;
    public PaymentController(
            MetricService metricService
    )
    {
        this.metricService = metricService;
    }
    @GetMapping
    @Operation(summary = "Создает случайный платеж с случайным временем платежа в диапазоне now и now-5min. Задержка выдачи результата 200мс")
    public Mono<Payment> getRandomPayment() {
        return Mono.delay(java.time.Duration.ofMillis(200))
                .thenReturn(new Payment())
                .doOnSuccess(payment -> {
                    metricService.paymentDealed();
                });
    }
}
