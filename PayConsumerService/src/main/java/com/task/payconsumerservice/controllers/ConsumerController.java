package com.task.payconsumerservice.controllers;

import com.task.payconsumerservice.models.Payment;
import com.task.payconsumerservice.reps.IPaymentRepository;
import com.task.payconsumerservice.services.PayConsumerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/consumer")
public class ConsumerController
{
    private final PayConsumerService payConsumerService;
    private final IPaymentRepository paymentRepository;
    public ConsumerController(PayConsumerService payConsumerService, IPaymentRepository paymentRepository)
    {
        this.payConsumerService = payConsumerService;
        this.paymentRepository = paymentRepository;
    }
    @PutMapping
    @Operation(summary = "Получает платеж из PayDealer и сохраняет в БД")
    public Mono<Payment> postPayment() {
        return payConsumerService.fetchData();
    }
    @GetMapping
    @Operation(summary = "Возвращает последние N платежей, отсортированные по убыванию времени платежа")
    public Flux<Payment> getPayments(
            @Parameter(description = "Кол-во платежей к возврату", example = "50")
            @RequestParam(defaultValue = "50")
            int count) {
        return paymentRepository.findLast(count);
    }
}
