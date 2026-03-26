package com.ethan.finance.infra.persistence;

import com.ethan.finance.domain.Type;
import jakarta.persistence.*;

@Entity
@Table(name = "categories")
class CategoryEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column
    private Type type;

    protected CategoryEntity()
    {

    }

    // ctor is not needed here. I won't create entity.

    public Integer getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public Type getType()
    {
        return type;
    }
}
