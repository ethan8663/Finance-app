package com.ethan.finance.domain;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class MoneyTest {
    @Test
    void parse_withStringAmount_strippedAndParsed()
    {
        final String amount;
        final Result<BigDecimal> r;

        amount = "  20  ";
        r = Money.parse(amount);

        assertEquals(0, r.getValue().compareTo(new BigDecimal("20")));
    }

    @Test
    void parse_withInvalidAmount_returnResult()
    {
        final String amount;
        final Result<BigDecimal> r;

        amount = "hello";
        r = Money.parse(amount);

        assertFalse(r.isOk());
        assertEquals(List.of("Amount should be a number."), r.getErrors());
    }

    @Test
    void create_LargeScale_returnResultWithError()
    {
        final BigDecimal amount;
        final Result<Money> r;

        amount = new BigDecimal("20.000");
        r = Money.create(amount);

        assertFalse(r.isOk());
        assertEquals(List.of("Amount can not have more than 2 decimal places."), r.getErrors());
    }

    @Test
    void create_negative_returnResultWithError()
    {
        final BigDecimal amount;
        final Result<Money> r;

        amount = new BigDecimal("-20");
        r = Money.create(amount);

        assertFalse(r.isOk());
        assertEquals(List.of("Amount can not be zero or negative."), r.getErrors());
    }

    @Test
    void create_validNumber_returnResult()
    {
        final BigDecimal amount;
        final Result<Money> r;

        amount = new BigDecimal("20.00");
        r = Money.create(amount);

        assertTrue(r.isOk());
        assertEquals(0, r.getValue().getAmount().compareTo(new BigDecimal("20.00")));
    }
}
