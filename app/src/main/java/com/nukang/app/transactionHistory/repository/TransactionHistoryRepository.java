package com.nukang.app.transactionHistory.repository;

import com.nukang.app.transactionHistory.model.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long>{
}
