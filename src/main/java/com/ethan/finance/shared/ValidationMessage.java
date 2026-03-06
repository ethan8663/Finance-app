package com.ethan.finance.shared;

/***
 * For validation message.
 */
public final class ValidationMessage {

    // for user-facing messages
    public static String cannotBe(final String field, final String condition)
    {
        return field + " cannot be " + condition + ".";
    }

    public static String cannotHave(final String field, final String condition)
    {
        return field + " cannot have " + condition + ".";
    }

    public static String shouldBe(final String field, final String condition)
    {
        return field + " should be " + condition + ".";
    }

    public static String shouldHave(final String field, final String condition)
    {
        return field + " should have " + condition + ".";
    }

    // for programmer
    public static String mustNotBeNull(final String name)
    {
        return name + " must not be null.";
    }
}
