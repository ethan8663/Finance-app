package com.ethan.finance.infra.persistence;

import com.ethan.finance.domain.Type;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class TransactionEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Type type;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Column(nullable = false, length = 20)
    private String payer;

    @Column(nullable = false, length = 20)
    private String payee;

    @Column(nullable = false, length = 100)
    private String note;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    protected TransactionEntity() {}

    public TransactionEntity(
            final LocalDate date,
            final BigDecimal amount,
            final Type type,
            final Integer categoryId,
            final String payer,
            final String payee,
            final String note,
            final LocalDateTime recordedAt)
    {
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.categoryId = categoryId;
        this.payer = payer;
        this.payee = payee;
        this.note = note;
        this.recordedAt = recordedAt;
    }

    public Long getId()
    {
        return id;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public Type getType()
    {
        return type;
    }

    public Integer getCategoryId()
    {
        return categoryId;
    }

    public String getPayer()
    {
        return payer;
    }

    public String getPayee()
    {
        return payee;
    }

    public String getNote()
    {
        return note;
    }

    public LocalDateTime getRecordedAt()
    {
        return recordedAt;
    }
}
