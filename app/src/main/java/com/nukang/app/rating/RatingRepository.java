package com.nukang.app.rating;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating,String>, QuerydslPredicateExecutor<Rating>, QuerydslBinderCustomizer<QRating> {
    @Query(value = "select count(*) as count,avg(rating) as rating from rating where merchant_id = ?1", nativeQuery = true)
    RatingCount getRateCount(String userId);

    @Override
    default void customize(QuerydslBindings bindings, QRating root){

        bindings.bind(root.merchantId).first(StringExpression::equalsIgnoreCase);
        bindings.bind(root.transactionId).first(StringExpression::equalsIgnoreCase);
    };

}
