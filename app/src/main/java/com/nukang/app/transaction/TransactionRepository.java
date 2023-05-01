package com.nukang.app.transaction;

import com.nukang.app.user.AppUser;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, QuerydslPredicateExecutor<Transaction>,QuerydslBinderCustomizer<QTransaction>{
    Optional<Transaction> findByTransactionId(String transactionId);

    @Modifying
    @Query(value = "update transaction set is_seen =0 where merchant_id = ?1 or customer_id =?1",nativeQuery = true)
    void clearNotif(String appUser);

    @Override
    default void customize(QuerydslBindings bindings, QTransaction txn){
        StringPath[] customerAndMerchantId = new StringPath[] {txn.merchantId, txn.customerId};

        bindings.bind(txn.merchantId).first(StringExpression::containsIgnoreCase);
        bindings.bind(txn.customerId).first(StringExpression::containsIgnoreCase);
    };

}
