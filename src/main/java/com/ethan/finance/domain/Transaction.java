package com.ethan.finance.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Transaction {
    private final LocalDate recordAt;
    private final Money money;
    private final Type type;
    private final Long categoryId;
    private final String payee;
    private final String payer;
    private final String note;

    public Transaction(final LocalDate recordAt,
                        final Money money,
                        final Type type,
                        final Long categoryId,
                        final String payee,
                        final String payer,
                        final String note)
    {
        Objects.requireNonNull(recordAt, "recordAt must not be null");
        Objects.requireNonNull(money, "money must not be null");
        Objects.requireNonNull(type, "type must not be null");
        Objects.requireNonNull(payee, "payee must not be null");
        Objects.requireNonNull(payer, "payer must not be null");Objects.requireNonNull(note, "note must not be null");

        this.recordAt = recordAt;
        this.money = money;
        this.type = type;
        this.categoryId = categoryId;
        this.payee = payee;
        this.payer = payer;
        this.note = note;
    }

    public static Result<String> validatePartyName(final String partyName)
    {
        if(partyName == null ||
        partyName.isBlank() ||
        partyName.strip().length() > 20)
        {
            return Result.err("Party name should be valid name less than 20 characters.");
        }

        return Result.ok(partyName.strip());
    }

    public static Result<String> validateNote(final String note)
    {
        if(note == null)
        {
            return Result.ok("");
        }

        if(note.strip().length() > 100)
        {
            return Result.err("Note can not exceed 100 characters.");
        }

        return Result.ok(note);
    }


}
