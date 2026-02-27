package com.ethan.finance.domain;

public interface TransactionRepository {
    long saveTransaction(Transaction transaction);
}
