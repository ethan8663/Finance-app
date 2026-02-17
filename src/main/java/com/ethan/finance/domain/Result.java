package com.ethan.finance.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Result<T> {
    private final T value;
    private final List<String> errors;

    private Result(final T value, final List<String> errors)
    {
        this.value = value;
        this.errors = errors;
    }

    public static <T> Result<T> ok(final T value)
    {
        Objects.requireNonNull(value);
        return new Result<>(value, List.of());
    }

    public static <T> Result<T> err(final String error)
    {
        Objects.requireNonNull(error);
        if(error.isBlank())
        {
            throw new IllegalArgumentException("error can not be blank");
        }
        return new Result<>(null, List.of(error));
    }

    public boolean isOk()
    {
        return this.errors.isEmpty();
    }

    public List<String> getErrors()
    {
        return this.errors;
    }

    public static List<String> mergeErrors(final List<Result<?>> results)
    {
        Objects.requireNonNull(results);

        final List<String> errors;

        errors = new ArrayList<>();

        for(final Result<?> result : results)
        {
            Objects.requireNonNull(result);

            if(!result.isOk())
            {
                errors.addAll(result.getErrors());
            }
        }

        return List.copyOf(errors);
    }
}
