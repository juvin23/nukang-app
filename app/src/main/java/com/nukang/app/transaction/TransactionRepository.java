package com.nukang.app.transaction;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, QuerydslPredicateExecutor<Transaction>,QuerydslBinderCustomizer<QTransaction>{
    Optional<Transaction> findByTransactionId(String transactionId);

    @Override
    default void customize(QuerydslBindings bindings, QTransaction txn){
        StringPath[] customerAndMerchantId = new StringPath[] {txn.merchantId, txn.customerId};

        bindings.bind(txn.merchantId).first(StringExpression::containsIgnoreCase);
        bindings.bind(txn.customerId).first(StringExpression::containsIgnoreCase);
    };
}
