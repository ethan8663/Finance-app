package com.ethan.finance.infra.persistence;

import com.ethan.finance.domain.Transaction;
import com.ethan.finance.domain.TransactionRepository;
import org.springframework.stereotype.Repository;

@Repository
class TransactionRepositoryImpl implements TransactionRepository
{
    private final TransactionJpaRepository transactionJpaRepository;

    public TransactionRepositoryImpl(final TransactionJpaRepository transactionJpaRepository)
    {
        this.transactionJpaRepository = transactionJpaRepository;
    }

    @Override
    public long saveTransaction(final Transaction transaction)
    {
        final TransactionEntity entity;

        entity = this.toEntity(transaction);

        return transactionJpaRepository.save(entity).getId();
    }

    private TransactionEntity toEntity(final Transaction transaction)
    {
        return new TransactionEntity(
                transaction.getDate(),
                transaction.getMoney().getAmount(),
                transaction.getType(),
                transaction.getCategoryId(),
                transaction.getPayer(),
                transaction.getPayee(),
                transaction.getNote()
        );
    }
}
