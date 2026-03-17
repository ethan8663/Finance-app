package com.ethan.finance.app;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.ethan.finance.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

public class TransactionServiceTest
{
    private TransactionRepository transactionRepository;
    private CategoryRepository categoryRepository;
    private Clock clock;
    private TransactionService transactionService;

    @BeforeEach
    void setUp()
    {
        transactionRepository = mock(TransactionRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        clock = Clock.fixed(Instant.parse("2026-02-02T00:00:00Z"), ZoneId.of("UTC"));
        transactionService = new TransactionService(transactionRepository, categoryRepository, clock);

        when(transactionRepository.saveTransaction(any(Transaction.class))).thenReturn(1L);
        when(categoryRepository.findCategoryIdByName("FOOD", Type.EXPENSE)).thenReturn(Result.ok(1));
    }

    @Test
    void create_validDraft_storeTransactionInDBAndReturnTransactionID()
    {
        final TransactionDraft draft;
        final Result<Long> result;

        draft = new TransactionDraft(
                "2026-02-01",
                "10.00",
                "EXPENSE",
                "FOOD",
                "Ethan",
                "Costco",
                "This is note"
        );

        result = transactionService.create(draft);

        assertTrue(result.isOk());
        assertEquals(1L, result.getValue());
        verify(transactionRepository).saveTransaction(any(Transaction.class));
    }

    @Test
    void create_invalidFutureRecordAt_returnError()
    {
        final TransactionDraft draft;
        final Result<Long> result;

        draft = new TransactionDraft(
                "2027-02-01",
                "10.00",
                "EXPENSE",
                "FOOD",
                "Ethan",
                "Costco",
                "This is note"
        );

        result = transactionService.create(draft);

        assertFalse(result.isOk());
        assertEquals(List.of("Date cannot be in the future."), result.getErrors());
    }

    @Test
    void create_invalidCategory_returnError()
    {
        final TransactionDraft draft;
        final Result<Long> result;

        draft = new TransactionDraft(
                "2026-02-01",
                "10.00",
                "EXPENSE",
                "INVALID",
                "Ethan",
                "Costco",
                "This is note"
        );

        when(categoryRepository.findCategoryIdByName("INVALID", Type.EXPENSE)).thenReturn(Result.err("Category should be valid name."));

        result = transactionService.create(draft);

        assertFalse(result.isOk());
        assertEquals(List.of("Category should be valid name."), result.getErrors());
    }

    @Test
    void create_multipleInvalidFields_returnAccumulatedError()
    {
        final TransactionDraft draft;
        final Result<Long> result;

        draft = new TransactionDraft(
                "2027-02-01", // invalid; future date
                "10.000", // invalid; more than 2 decimal places
                "", // invalid; no type
                "", // invalid; no category
                "Ethan",
                "Costcoasdfsadfasdfdsdaf", // invalid; more than 10 chars
                "This is note"
        );

        when(categoryRepository.findCategoryIdByName("INVALID", Type.EXPENSE)).thenReturn(Result.err("Category should be valid name."));

        result = transactionService.create(draft);

        assertFalse(result.isOk());
        assertEquals(List.of(
                "Date cannot be in the future.",
                "Amount cannot have more than 2 decimal places.",
                "Type should be INCOME or EXPENSE.",
                "Payer and Payee should be valid name less than 20 characters."), result.getErrors());
    }
}
