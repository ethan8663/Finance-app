package com.ethan.finance.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents result. It can be either ok(value) or err(list of errors).
 * If ok(value), errors must be empty list.
 * If err(list of errors), value must be null.
 *
 * @param <T> type of value
 */
public final class Result<T>
{
    private static final String ERR_MSG_VAL_ERR_PRESENT = "Value and errors can not be both present.";
    private static final String ERR_MSG_VAL_ERR_ABSENT = "Value and errors can not be both absent.";
    private static final String ERR_MSG_ERR_EMPTY = "Errors can not be empty.";
    private static final String ERR_MSG_ERR_NOT_EMPTY = "Errors must be empty to get the value.";
    private static final String ERR_MSG_ERR_BLANK = "Error must not be blank";

    private static final String NULL_MSG_ERRORS = "errors";
    private static final String NULL_MSG_ERROR = "error";
    private static final String NULL_MSG_VALUE = "value";
    private static final String NULL_MSG_RESULTS = "results";
    private static final String NULL_MSG_RESULT = "result";

    private final T value;
    private final List<String> errors;

    /// Enforces rules before constructing an object.
    /// 1. errors can not be null.
    /// 2. if value is not null, errors must be empty.
    /// 3. if value is null, errors can not be empty.
    private Result(final T value, final List<String> errors)
    {
        Objects.requireNonNull(errors, NULL_MSG_ERRORS);
        final List<String> copied;

        copied = List.copyOf(errors);

        if(value != null && !copied.isEmpty())
        {
            throw new IllegalArgumentException(ERR_MSG_VAL_ERR_PRESENT);
        }

        if(value == null && copied.isEmpty())
        {
            throw new IllegalArgumentException(ERR_MSG_VAL_ERR_ABSENT);
        }

        this.value = value;
        this.errors = copied;
    }

    /**
     * Represents a factory method to construct ok(value).
     * Value can not be null.
     *
     * @param value the value
     * @return Result<T>
     * @param <T> type of value
     */
    public static <T> Result<T> ok(final T value)
    {
        Objects.requireNonNull(value, NULL_MSG_VALUE);
        return new Result<>(value, List.of());
    }

    /**
     * Represents a factory method to construct err(list of errors).
     * The list can not be null nor empty.
     * Each element in the list can not be null nor empty.
     *
     * @param errors the list
     * @return Result<T>
     * @param <T> type of the value
     */
    public static <T> Result<T> err(final List<String> errors)
    {
        Objects.requireNonNull(errors, NULL_MSG_ERRORS);
        if(errors.isEmpty())
        {
            throw new IllegalArgumentException(ERR_MSG_ERR_EMPTY);
        }

        for(final String error : errors)
        {
            Objects.requireNonNull(error, NULL_MSG_ERROR);
            if(error.isBlank())
            {
                throw new IllegalArgumentException(ERR_MSG_ERR_BLANK);
            }
        }
        return new Result<>(null, errors);
    }

    /**
     * Checks if errors is an empty list.
     *
     * @return true if empty. Otherwise, false.
     */
    public boolean isOk()
    {
        return this.errors.isEmpty();
    }

    /**
     * Gets the list of errors.
     *
     * @return the list of String
     */
    public List<String> getErrors()
    {
        return this.errors;
    }

    /**
     * Gets the value.
     * The list of errors must be empty.
     *
     * @return the value
     */
    public T getValue()
    {
        if(!this.errors.isEmpty())
        {
            throw new IllegalStateException(ERR_MSG_ERR_NOT_EMPTY);
        }

        return this.value;
    }

    /**
     * Merges list of errors.
     * The list must not be null.
     * The element in the list must not be null.
     *
     * @param results the results
     * @return list of errors
     */
    public static List<String> mergeErrors(final List<Result<?>> results)
    {
        Objects.requireNonNull(results, NULL_MSG_RESULTS);

        final List<String> errorsAcc;

        errorsAcc = new ArrayList<>();

        for(final Result<?> result : results)
        {
            Objects.requireNonNull(result, NULL_MSG_RESULT);

            if(!result.isOk())
            {
                errorsAcc.addAll(result.getErrors());
            }
        }

        return List.copyOf(errorsAcc);
    }
}
