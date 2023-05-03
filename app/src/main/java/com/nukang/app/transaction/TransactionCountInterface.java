package com.nukang.app.transaction;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class TransactionCountInterface {
    long count;
    BigDecimal amount;

    public TransactionCountInterface(long count, BigDecimal amount){
        this.count = count;
        this.amount = amount;
    }
}
