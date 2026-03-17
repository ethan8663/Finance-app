package com.ethan.finance.app;

public record TransactionDraft(
        String date,
        String money,
        String type,
        String category,
        String payer,
        String payee,
        String note
) {
}
