package com.nukang.app.transactionHistory.repository;

import com.nukang.app.transactionHistory.model.TransactionDetailHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailHistoryRepository extends JpaRepository<TransactionDetailHistory, Long> {
}
