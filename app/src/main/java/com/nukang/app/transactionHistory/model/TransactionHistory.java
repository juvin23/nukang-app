package com.nukang.app.transactionHistory.model;

import com.nukang.app.transaction.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "transaction_history")
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @Column(name = "txn_id")
    private String txnId;

    @Column(name = "txn_start_date")
    private LocalDate startDate;

    @Column(name = "txn_end_date")
    private LocalDate endDate;

    @Column(name = "record_status")
    private String recordStatus;

    @Column(name = "last_update")
    private LocalDateTime lastUpdated;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "denied_reason")
    private String deniedReason;
    public TransactionHistory(Transaction transDB) {
        this.createdDate = transDB.getCreatedDate();
        this.lastUpdated = transDB.getLastUpdated();
        this.txnId = transDB.getTransactionId();
        this.recordStatus = transDB.getRecordStatus();
        this.startDate = transDB.getStartDate();
        this.endDate = transDB.getEndDate();
        this.deniedReason = transDB.getDeniedReason();
    }
}
