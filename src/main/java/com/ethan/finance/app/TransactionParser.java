package com.ethan.finance.app;

import com.ethan.finance.domain.Result;
import com.ethan.finance.domain.Type;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

final class TransactionParser {
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ISO_LOCAL_DATE; // yyyy-MM-dd

    static Result<LocalDate> parseRecordAt(final String date)
    {
        final LocalDate parsedDate;
        final String strippedDate;

        try
        {
            strippedDate = date.strip();
            parsedDate = LocalDate.parse(strippedDate, DATE_FMT);

            return Result.ok(parsedDate);
        }
        catch(final DateTimeParseException e)
        {
            return Result.err("Date should conform to yyyy-MM-dd.");
        }
    }

    static Result<Type> parseType(final String type)
    {
        final String normalized;

        normalized = type.strip().toUpperCase();
        return switch(normalized)
        {
            case "INCOME" -> Result.ok(Type.INCOME);
            case "EXPENSE" -> Result.ok(Type.EXPENSE);
            default -> Result.err("Type must be INCOME or EXPENSE.");
        };
    }
}
