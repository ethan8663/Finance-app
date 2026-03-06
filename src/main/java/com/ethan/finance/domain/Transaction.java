package com.ethan.finance.domain;

import com.ethan.finance.shared.FieldName;
import com.ethan.finance.shared.ValidationMessage;

import java.time.LocalDate;
import java.util.Objects;

public class Transaction {
    private static final String NULL_MSG_RECORD_AT = ValidationMessage.mustNotBeNull("Record at");
    private static final String NULL_MSG_MONEY = ValidationMessage.mustNotBeNull("Money");
    private static final String NULL_MSG_Type = ValidationMessage.mustNotBeNull("Type");
    private static final String NULL_MSG_PAYEE = ValidationMessage.mustNotBeNull("Payee");
    private static final String NULL_MSG_PAYER = ValidationMessage.mustNotBeNull("Payer");
    private static final String NULL_MSG_NOTE = ValidationMessage.mustNotBeNull("Note");
    private static final String ERR_MSG_NAME_TOO_LONG = ValidationMessage.shouldBe(FieldName.PAYER + " and " + FieldName.PAYEE, "valid name less than 20 characters");
    private static final String ERR_MSG_NOTE_TOO_LONG = ValidationMessage.cannotHave(FieldName.NOTE, "more than 100 characters");

    private static final int NAME_LENGTH_LIMIT = 20;
    private static final int NOTE_LENGTH_LIMIT = 100;

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
        Objects.requireNonNull(recordAt, NULL_MSG_RECORD_AT);
        Objects.requireNonNull(money, NULL_MSG_MONEY);
        Objects.requireNonNull(type, NULL_MSG_Type);
        Objects.requireNonNull(payee, NULL_MSG_PAYEE);
        Objects.requireNonNull(payer, NULL_MSG_PAYER);
        Objects.requireNonNull(note, NULL_MSG_NOTE);

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
        partyName.strip().length() > NAME_LENGTH_LIMIT)
        {
            return Result.err(ERR_MSG_NAME_TOO_LONG);
        }

        return Result.ok(partyName.strip());
    }

    public static Result<String> validateNote(final String note)
    {
        if(note == null)
        {
            return Result.ok("");
        }

        if(note.strip().length() > NOTE_LENGTH_LIMIT)
        {
            return Result.err(ERR_MSG_NOTE_TOO_LONG);
        }

        return Result.ok(note);
    }
}
