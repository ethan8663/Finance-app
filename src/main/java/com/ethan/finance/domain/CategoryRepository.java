package com.ethan.finance.domain;

public interface CategoryRepository {
    Result<Long> findCategoryIdByName(String category);
}
