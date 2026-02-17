package com.ethan.finance.domain;

import java.math.BigDecimal;

public class Money {
    private final BigDecimal amount;

    private Money(final BigDecimal amount)
    {
        this.amount = amount;
    }


}
