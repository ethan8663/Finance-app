package com.ethan.finance.domain;

import com.ethan.finance.shared.FieldName;
import com.ethan.finance.shared.ValidationMessage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents money.
 * Has one factory method to construct an object.
 */
public final class Money {
    private static final int SCALE_LIMIT = 2;

    private static final String ERR_MSG_AMT_ZERO_NEGATIVE = ValidationMessage.cannotBe(FieldName.AMOUNT, "zero or negative");
    private static final String ERR_MSG_AMT_LARGE_SCALE = ValidationMessage.cannotHave(FieldName.AMOUNT, "more than " + SCALE_LIMIT + " decimal places");
    private static final String ERR_MSG_AMT_PARSE = ValidationMessage.shouldBe(FieldName.AMOUNT, "a number");

    private static final String NULL_MSG_AMT = ValidationMessage.mustNotBeNull(FieldName.AMOUNT);

    private final BigDecimal amount;

    ///
    /// Enforces rules before constructing an object.
    /// 1. amount can not be null
    /// 2. amount can not be negative nor zero
    /// 3. amount can not have more than 2 decimals
    /// Failure to conform to these rules, throw an exception.
    private Money(final BigDecimal amount)
    {
        Objects.requireNonNull(amount, NULL_MSG_AMT);

        if(amount.compareTo(BigDecimal.ZERO) <= 0)
        {
            throw new IllegalArgumentException(ERR_MSG_AMT_ZERO_NEGATIVE);
        }

        if(amount.scale() > SCALE_LIMIT)
        {
            throw new IllegalArgumentException(ERR_MSG_AMT_LARGE_SCALE);
        }

        this.amount = amount.setScale(SCALE_LIMIT, RoundingMode.HALF_UP);
    }

    /**
     * Represents a factory method to construct a money object wrapped inside Result.
     * 1. amount can not be null
     * 2. amount can not be negative nor zero
     * 3. amount can not have more than {@code SCALE_LIMIT} decimals
     * Failure to conform to these rules, return error messages wrapped inside Result.
     *
     * @param amount the amount
     * @return error messages wrapped in Result or amount wrapped in Result
     */
    public static Result<BigDecimal> parse(final String amount)
    {
        if(amount == null)
        {
            return Result.err(ERR_MSG_AMT_PARSE);
        }

        final List<String> errors;
        final BigDecimal amountParsed;

        errors = new ArrayList<>();

        try
        {
            amountParsed = new BigDecimal(amount.strip());
        }
        catch(final NumberFormatException e)
        {
            errors.add(ERR_MSG_AMT_PARSE);
            return Result.err(errors);
        }
        return Result.ok(amountParsed);
    }

    public static Result<Money> create(final BigDecimal amount)
    {
        Objects.requireNonNull(amount, NULL_MSG_AMT);

        final List<String> errors;

        errors = new ArrayList<>();

        if(amount.scale() > SCALE_LIMIT)
        {
            errors.add(ERR_MSG_AMT_LARGE_SCALE);
        }

        if(amount.compareTo(BigDecimal.ZERO) <= 0)
        {
            errors.add(ERR_MSG_AMT_ZERO_NEGATIVE);
        }

        if(errors.isEmpty())
        {
            return Result.ok(new Money(amount));
        }
        return Result.err(errors);
    }

    /**
     * Get amount.
     * @return the amount
     */
    public BigDecimal getAmount()
    {
        return this.amount;
    }
}
