package com.ethan.finance.domain;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TransactionTest {
    @Test
    void validatePartyName_validName_returnResult()
    {
        final String partyName;
        final Result<String> r;

        partyName = " ethan   ";
        r = Transaction.validatePartyName(partyName);

        assertTrue(r.isOk());
        assertEquals("ethan", r.getValue());
    }

    @Test
    void validatePartyName_blankName_returnResultWithError()
    {
        final String partyName;
        final Result<String> r;

        partyName = "         ";
        r = Transaction.validatePartyName(partyName);

        assertFalse(r.isOk());
        assertEquals(List.of("Payer and Payee should be valid name less than 20 characters."), r.getErrors());
    }

    @Test
    void validatePartyName_tooLongName_returnResultWithError()
    {
        final String partyName;
        final Result<String> r;

        partyName = "fasdfdsafdsaffaaaaaaa"; // 21 chars
        r = Transaction.validatePartyName(partyName);

        assertFalse(r.isOk());
        assertEquals(List.of("Payer and Payee should be valid name less than 20 characters."), r.getErrors());
    }

    @Test
    void validateNote_nullNote_returnResult()
    {
        final String note;
        final Result<String> r;

        note = null;
        r = Transaction.validateNote(note);

        assertTrue(r.isOk());
        assertEquals("", r.getValue());

    }

    @Test
    void validateNote_someNote_returnResult()
    {
        final String note;
        final Result<String> r;

        note = "this is some note";
        r = Transaction.validateNote(note);

        assertTrue(r.isOk());
        assertEquals("this is some note", r.getValue());
    }

    @Test
    void validateNote_tooLongNote_returnResultWithError()
    {
        final String note;
        final Result<String> r;

        note = "asdfsadfdsafsdafsadfsadfsdafasdfsadfasfsadfsadfasdfdsafdsafasdfsdfsadfasdfsadfasdfsdafsdafasdasdfsadf"; // 101 chars
        r = Transaction.validateNote(note);

        assertFalse(r.isOk());
        assertEquals(List.of("Note cannot have more than 100 characters."), r.getErrors());
    }

}
