package com.ethan.finance.app;

import com.ethan.finance.domain.*;
import com.ethan.finance.shared.FieldName;
import com.ethan.finance.shared.ValidationMessage;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/***
 * Creates a transaction and validates user input that needs dependencies.
 */
public final class TransactionService {
    private static final String NULL_MSG_T_REPO = ValidationMessage.mustNotBeNull("Transaction repository");
    private static final String NULL_MSG_C_REPO = ValidationMessage.mustNotBeNull("Category repository");
    private static final String NULL_MSG_CLOCK = ValidationMessage.mustNotBeNull("Clock");
    private static final String NULL_MSG_DRAFT = ValidationMessage.mustNotBeNull("Transaction draft");

    private static final String ERR_MSG_RECORD_AT_NULL = ValidationMessage.cannotBe(FieldName.RECORD_AT, "empty");
    private static final String ERR_MSG_DATE_FUTURE = ValidationMessage.cannotBe(FieldName.RECORD_AT, "in the future");
    private static final String ERR_MSG_CATEGORY_INVALID = ValidationMessage.shouldBe(FieldName.CATEGORY, "valid");

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final Clock clock;

    public TransactionService(final TransactionRepository transactionRepository,
                              final CategoryRepository categoryRepository,
                              final Clock clock)
    {
        Objects.requireNonNull(transactionRepository, NULL_MSG_T_REPO);
        Objects.requireNonNull(categoryRepository, NULL_MSG_C_REPO);
        Objects.requireNonNull(clock, NULL_MSG_CLOCK);

        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
        this.clock = clock;
    }

    /***
     * Creates a transaction.
     * Does not throw exceptions for user input mistake.
     *
     * @param transactionDraft the draft
     * @return transaction id
     */
    public Result<Long> create(final TransactionDraft transactionDraft)
    {
        Objects.requireNonNull(transactionDraft, NULL_MSG_DRAFT);

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

        // monadic approach
        // parse and validate. If parse fails, short circuit.
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
        if(recordAt == null)
        {
            return Result.err(ERR_MSG_RECORD_AT_NULL);
        }

        final LocalDate today;

        today = LocalDate.now(clock);
        if(recordAt.isAfter(today))
        {
            return Result.err(ERR_MSG_DATE_FUTURE);
        }

        return Result.ok(recordAt);
    }

    private Result<Long> createCategoryId(final String category)
    {
        if(category == null ||
        category.strip().isBlank())
        {
            return Result.err(ERR_MSG_CATEGORY_INVALID);
        }

        final Result<Long> categoryId;

        categoryId = this.categoryRepository.findCategoryIdByName(category);

        return categoryId;
    }
}
