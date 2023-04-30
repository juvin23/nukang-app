package com.nukang.app.transactionHistory.service;

import com.nukang.app.transaction.Transaction;
import com.nukang.app.transactionHistory.model.TransactionHistory;
import com.nukang.app.transactionHistory.repository.TransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionHistoryService {

    private final TransactionHistoryRepository repository;

    public void save(Transaction transDB) {
        if(transDB == null) return;
        TransactionHistory history = new TransactionHistory(transDB);
        repository.save(history);
    }
}
