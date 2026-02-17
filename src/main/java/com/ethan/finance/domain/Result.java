package com.ethan.finance.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public static <T> Result<T> ok(final T value)
    {
        Objects.requireNonNull(value, NULL_MSG_VALUE);
        return new Result<>(value, List.of());
    }

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

    public boolean isOk()
    {
        return this.errors.isEmpty();
    }

    public List<String> getErrors()
    {
        return this.errors;
    }

    public T getValue()
    {
        if(!this.errors.isEmpty())
        {
            throw new IllegalStateException(ERR_MSG_ERR_NOT_EMPTY);
        }

        return this.value;
    }

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
