package com.ethan.finance.app;

import com.ethan.finance.domain.*;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public final class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final Clock clock;

    public TransactionService(final TransactionRepository transactionRepository,
                              final CategoryRepository categoryRepository,
                              final Clock clock)
    {
        Objects.requireNonNull(transactionRepository, "Transaction repository can not be null.");
        Objects.requireNonNull(categoryRepository, "Category repository can not be null.");
        Objects.requireNonNull(clock, "Clock can not be null.");

        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
        this.clock = clock;
    }

    public Result<Long> create(final TransactionDraft transactionDraft)
    {
        Objects.requireNonNull(transactionDraft, "Transaction draft can not be null.");

        final List<Result<?>> resultList;
        final List<String> errorList;

        final Result<LocalDate> recordAtResult;
        final Result<Money> moneyResult;
        final Result<Type> typeResult;
        final Result<Long> categoryIdResult;
        final Result<String> payerResult;
        final Result<String> payeeResult;
        final Result<String> noteResult;

        final Transaction transaction;
        final long transactionId;

        recordAtResult = TransactionParser.parseRecordAt(transactionDraft.recordAt()).flatMap(this::validateRecordAt);
        moneyResult = Money.parse(transactionDraft.money()).flatMap(Money::create);
        typeResult = TransactionParser.parseType((transactionDraft.type()));
        categoryIdResult = createCategoryId((transactionDraft.category()));
        payerResult = Transaction.validatePartyName(transactionDraft.payer());
        payeeResult = Transaction.validatePartyName(transactionDraft.payee());
        noteResult = Transaction.validateNote(transactionDraft.note());

        resultList = List.of(recordAtResult, moneyResult, typeResult, categoryIdResult,
                payerResult, payeeResult, noteResult);

        errorList = Result.mergeErrors(resultList);

        if(!errorList.isEmpty())
        {
            return Result.err(errorList);
        }

        transaction = new Transaction(
                recordAtResult.getValue(),
                moneyResult.getValue(),
                typeResult.getValue(),
                categoryIdResult.getValue(),
                payerResult.getValue(),
                payeeResult.getValue(),
                noteResult.getValue()
                );

        transactionId = this.transactionRepository.saveTransaction(transaction);

        return Result.ok(transactionId);
    }

    private Result<LocalDate> validateRecordAt(final LocalDate recordAt)
    {
        Objects.requireNonNull(recordAt, "Record at should be not null.");

        final LocalDate today;

        today = LocalDate.now(clock);
        if(recordAt.isAfter(today))
        {
            return Result.err("Record date can not be in the future.");
        }

        return Result.ok(recordAt);
    }

    private Result<Long> createCategoryId(final String category)
    {
        if(category == null ||
        category.strip().isBlank())
        {
            return Result.err("Category should be valid category name.");
        }

        final Result<Long> categoryId;

        categoryId = this.categoryRepository.findCategoryIdByName(category);

        return categoryId;
    }
}
