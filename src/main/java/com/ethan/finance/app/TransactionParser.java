package com.ethan.finance.app;

import com.ethan.finance.domain.Result;
import com.ethan.finance.domain.Type;
import com.ethan.finance.shared.FieldName;
import com.ethan.finance.shared.ValidationMessage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

final class TransactionParser {
    private static final String ERR_MSG_RECORD_AT_FORMAT = ValidationMessage.shouldBe(FieldName.RECORD_AT, "YYYY-MM-DD");
    private static final String ERR_MSG_TYPE = ValidationMessage.shouldBe(FieldName.TYPE, "INCOME or EXPENSE");

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ISO_LOCAL_DATE; // yyyy-MM-dd

    static Result<LocalDate> parseRecordAt(final String recordAt)
    {
        if(recordAt == null)
        {
            return Result.err(ERR_MSG_RECORD_AT_FORMAT);
        }

        final LocalDate parsedDate;
        final String strippedDate;

        try
        {
            strippedDate = recordAt.strip();
            parsedDate = LocalDate.parse(strippedDate, DATE_FMT);

            return Result.ok(parsedDate);
        }
        catch(final DateTimeParseException e)
        {
            return Result.err(ERR_MSG_RECORD_AT_FORMAT);
        }
    }

    static Result<Type> parseType(final String type)
    {
        if(type == null)
        {
            return Result.err(ERR_MSG_TYPE);
        }

        final String normalized;

        normalized = type.strip().toUpperCase();
        return switch(normalized)
        {
            case "INCOME" -> Result.ok(Type.INCOME);
            case "EXPENSE" -> Result.ok(Type.EXPENSE);
            default -> Result.err(ERR_MSG_TYPE);
        };
    }
}
