package com.task.payconsumerservice.reps;

import com.task.payconsumerservice.models.Payment;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface IPaymentRepository extends ReactiveCrudRepository<Payment, UUID>
{
    @Query("SELECT * FROM payment ORDER BY transaction_time DESC LIMIT $1")
    Flux<Payment> findLast(int count);
}
