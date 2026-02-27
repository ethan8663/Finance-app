package com.ethan.finance.app;

public record TransactionDraft(
        String recordAt,
        String money,
        String type,
        String category,
        String payer,
        String payee,
        String note
) {
}
