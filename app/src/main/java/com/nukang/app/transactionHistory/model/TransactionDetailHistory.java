package com.nukang.app.transactionHistory.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transaction_detail_history")
public class TransactionDetailHistory {
    @Id
    Long id;

}
