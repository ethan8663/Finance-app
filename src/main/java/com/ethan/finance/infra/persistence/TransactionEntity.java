package com.ethan.finance.infra.persistence;

import com.ethan.finance.domain.Type;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
class TransactionEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate date;

    @Column
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column
    private Type type;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column
    private String payer;

    @Column
    private String payee;

    @Column
    private String note;

    @CreationTimestamp
    @Column(name = "recorded_at")
    private LocalDateTime recordedAt;

    protected TransactionEntity() {}

    TransactionEntity(
            final LocalDate date,
            final BigDecimal amount,
            final Type type,
            final Integer categoryId,
            final String payer,
            final String payee,
            final String note)
    {
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.categoryId = categoryId;
        this.payer = payer;
        this.payee = payee;
        this.note = note;
    }

    Long getId()
    {
        return id;
    }

    LocalDate getDate()
    {
        return date;
    }

    BigDecimal getAmount()
    {
        return amount;
    }

    Type getType()
    {
        return type;
    }

    Integer getCategoryId()
    {
        return categoryId;
    }

    String getPayer()
    {
        return payer;
    }

    String getPayee()
    {
        return payee;
    }

    String getNote()
    {
        return note;
    }

    LocalDateTime getRecordedAt()
    {
        return recordedAt;
    }
}
