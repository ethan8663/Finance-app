package com.ethan.finance.infra;

import com.ethan.finance.domain.Transaction;
import com.ethan.finance.domain.TransactionRepository;
import org.springframework.stereotype.Repository;

import java.util.concurrent.atomic.AtomicLong;

@Repository
public final class InMemoryTransactionRepository implements TransactionRepository {
    private final AtomicLong idSequence = new AtomicLong(1);

    @Override
    public long saveTransaction(final Transaction transaction)
    {
        return idSequence.getAndIncrement();
    }
}
