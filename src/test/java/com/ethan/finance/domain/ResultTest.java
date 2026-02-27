package com.ethan.finance.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ResultTest {
    @Test
    void ok_withNonNullValue_storeValueAndNullErrList()
    {
        final Result<String> r;
        final String s;

        s = "hello";
        r = Result.ok(s);

        assertTrue(r.isOk());
        assertEquals(s, r.getValue());
        assertEquals(List.of(), r.getErrors());
    }

    @Test
    void ok_withNullValue_throw()
    {
        assertThrows(NullPointerException.class, () -> Result.ok(null));
    }

    @Test
    void err_withList_storeNullValueAndStrippedList()
    {
        final Result<String> r;
        final List<String> errors;

        errors = List.of("err1", "  err 2   ", "err3   ");
        r = Result.err(errors);

        assertFalse(r.isOk());
        assertEquals(List.of("err1", "err 2", "err3"), r.getErrors());
        assertThrows(IllegalStateException.class, () -> r.getValue());
    }
    @Test
    void err_withString_storeNullValueAndStrippedStringAsList()
    {
        final Result<String> r;
        final String s;

        s = "  hello  ";
        r = Result.err(s);

        assertFalse(r.isOk());
        assertEquals(List.of("hello"), r.getErrors());
        assertThrows(IllegalStateException.class, () -> r.getValue());
    }
    @Test
    void err_withNull_throw()
    {
        assertThrows(NullPointerException.class, () -> Result.err((String) null));
    }

    @Test
    void err_withListContainNull_throw()
    {
        assertThrows(NullPointerException.class, () -> Result.err(List.of("err1", null, "err 2")));
    }

    @Test
    void err_withListContainBlank_throw()
    {
        assertThrows(IllegalArgumentException.class, () -> Result.err(List.of("err1", "     ", "err 2")));
    }

    @Test
    void mergeErrors_withListOfResults_mergeErrors()
    {
        final Result<String> r1;
        final Result<Integer> r2;
        final Result<Boolean> r3;

        r1 = Result.err(List.of("err1", "err2  "));
        r2 = Result.ok(10);
        r3 = Result.err(List.of("err4    "));

        assertEquals(List.of("err1", "err2", "err4"), Result.mergeErrors(List.of(r1,r2,r3)));
    }

    @Test
    void flatMap_withError_shortCircuit()
    {
        final Result<String> r;
        final Result<String> r2;

        r = Result.err("err");
        r2 = r.flatMap(s -> Result.ok(s + " ."));

        assertEquals(List.of("err"), r2.getErrors());
    }

    @Test
    void flatMap_withValue_noShortCircuit()
    {
        final Result<String> r;
        final Result<String> r2;

        r = Result.ok("hello");
        r2 = r.flatMap(s -> Result.ok(s + "."));

        assertEquals("hello.", r2.getValue());
    }
}
