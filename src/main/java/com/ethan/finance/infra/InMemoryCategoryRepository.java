package com.ethan.finance.infra;

import com.ethan.finance.domain.CategoryRepository;
import com.ethan.finance.domain.Result;
import com.ethan.finance.domain.Type;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public final class InMemoryCategoryRepository implements CategoryRepository {
    private static final Map<String, Integer> EXPENSE_CATEGORIES = Map.of(
            "FOOD", 1,
            "TRANSPORT", 2,
            "SHOPPING", 3
    );

    private static final Map<String, Integer> INCOME_CATEGORIES = Map.of(
            "SALARY", 101,
            "BONUS", 102,
            "OTHER", 103
    );

    @Override
    public Result<Integer> findCategoryIdByName(final String category, final Type type)
    {
        final Integer id;

        if(type == Type.EXPENSE)
        {
            id = EXPENSE_CATEGORIES.get(category);
        }
        else
        {
            id = INCOME_CATEGORIES.get(category);
        }

        if(id == null)
        {
            return Result.err("Category should be valid name.");
        }

        return Result.ok(id);
    }
}
