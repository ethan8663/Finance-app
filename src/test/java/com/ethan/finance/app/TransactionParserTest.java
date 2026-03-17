package com.ethan.finance.app;
import com.ethan.finance.domain.Result;
import com.ethan.finance.domain.Type;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionParserTest {
    @Test
    void parseDate_validDate_returnResult()
    {
        final String date;
        final LocalDate d;
        final Result<LocalDate> r;

        date = "   2026-02-27  ";
        d = LocalDate.of(2026, 2, 27);
        r = TransactionParser.parseDate(date);

        assertTrue(r.isOk());
        assertEquals(d, r.getValue());
    }

    @Test
    void parseDate_wrongDateFormat_returnResultWithError()
    {
        final String date;
        final Result<LocalDate> r;

        date = "2026-2-2";
        r = TransactionParser.parseDate(date);

        assertFalse(r.isOk());
        assertEquals(List.of("Date should be YYYY-MM-DD."), r.getErrors());
    }

    @Test
    void parseType_validTypeIncome_returnResult()
    {
        final String type;
        final Result<Type> result;

        type = "income";
        result = TransactionParser.parseType(type);

        assertTrue(result.isOk());
        assertEquals(Type.INCOME, result.getValue());
    }

    @Test
    void parseType_validTypeExpense_returnResult()
    {
        final String type;
        final Result<Type> result;

        type = "ExpensE";
        result = TransactionParser.parseType(type);

        assertTrue(result.isOk());
        assertEquals(Type.EXPENSE, result.getValue());
    }

    @Test
    void parseType_invalidType_returnResult()
    {
        final String type;
        final Result<Type> result;

        type = "wrong type";
        result = TransactionParser.parseType(type);

        assertFalse(result.isOk());
        assertEquals(List.of("Type should be INCOME or EXPENSE."), result.getErrors());
    }


}
