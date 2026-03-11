package com.ethan.finance.domain;

public interface CategoryRepository {
    Result<Integer> findCategoryIdByName(String category, Type type);
}
