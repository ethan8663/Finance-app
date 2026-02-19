package com.ethan.finance.domain;

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

    private static final String ERR_MSG_AMT_ZERO_NEGATIVE = "Amount can not be zero or negative.";
    private static final String ERR_MSG_AMT_LARGE_SCALE = "Amount can not have more than " + SCALE_LIMIT +" decimal places.";
    private static final String ERR_MSG_AMT_PARSE = "Amount should be a number.";

    private static final String NULL_MSG_AMT = "Amount can not be null.";

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
    public static Result<Money> of(final String amount)
    {
        Objects.requireNonNull(amount, NULL_MSG_AMT);

        final List<String> errors;
        final BigDecimal amountParsed;

        errors = new ArrayList<>();

        // Parse
        // If parsing failed, it is not worth validating. Return right away.
        try
        {
            amountParsed = new BigDecimal(amount);
        }
        catch(final NumberFormatException e)
        {
            errors.add(ERR_MSG_AMT_PARSE);
            return Result.err(errors);
        }

        // Validation
        if(amountParsed.scale() > SCALE_LIMIT)
        {
            errors.add(ERR_MSG_AMT_LARGE_SCALE);
        }

        if(amountParsed.compareTo(BigDecimal.ZERO) <= 0)
        {
            errors.add(ERR_MSG_AMT_ZERO_NEGATIVE);
        }

        if(errors.isEmpty())
        {
            return Result.ok(new Money(amountParsed));
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
