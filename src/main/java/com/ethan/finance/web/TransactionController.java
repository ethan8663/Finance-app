package com.ethan.finance.web;

import com.ethan.finance.app.TransactionDraft;
import com.ethan.finance.app.TransactionService;
import com.ethan.finance.domain.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public final class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(final TransactionService transactionService)
    {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody final TransactionDraft transactionDraft)
    {
        final Result<Long> result;

        result = transactionService.create(transactionDraft);

        if(!result.isOk())
        {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiErrorResponse(result.getErrors()));
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccessResponse(result.getValue()));
    }
}
