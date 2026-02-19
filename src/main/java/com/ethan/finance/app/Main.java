package com.ethan.finance.app;

import java.math.BigDecimal;

public final class Main
{
    public static void main(final String[] args)
    {
        BigDecimal b = new BigDecimal(0);
        System.out.println(b.compareTo(BigDecimal.ZERO));
    }
}
