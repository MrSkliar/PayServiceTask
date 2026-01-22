package com.task.payconsumerservice.services;

import com.task.payconsumerservice.models.Payment;
import com.task.payconsumerservice.reps.IPaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PayConsumerService
{
    private final WebClient webClient;
    private final IPaymentRepository paymentRepository;
    private final MetricService metricService;
    public PayConsumerService(
            @Value("${dealer.url}") String dealerUrl,
            WebClient.Builder webClientBuilder,
            IPaymentRepository paymentRepository,
            MetricService metricService
    )
    {
        this.webClient = webClientBuilder.baseUrl(dealerUrl).build();
        this.paymentRepository = paymentRepository;
        this.metricService = metricService;
    }
    public Mono<Payment> fetchData() {
        var timer = metricService.startPaymentConsumeTimer();
        return webClient.get()
                .uri("/api/payment")
                .retrieve()
                .bodyToMono(Payment.class)
                .flatMap(payment -> {
                    return paymentRepository.save(payment);
                })
                .doOnSuccess(payment -> {
                    metricService.paymentConsumed();
                    metricService.stopPaymentConsumeTimer(timer);
                })
                .doOnError(__ -> {
                    metricService.paymentConsumedWithError();
                    metricService.stopPaymentConsumeTimer(timer);
                });
    }
}
