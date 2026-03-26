package com.ethan.finance.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface TransactionJpaRepository extends JpaRepository<TransactionEntity, Long>
{
}
