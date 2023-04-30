package com.nukang.app.user.repository;


import com.nukang.app.user.model.Customer;
import com.nukang.app.user.model.QCustomer;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, QuerydslPredicateExecutor<Customer>, QuerydslBinderCustomizer<QCustomer> {
    Optional<Customer> findByCustomerId(String userId);

    Page<Customer> findAll(Predicate predicate, Pageable pageable);
    Customer findByEmail(String email);

    @Override
    default void customize(QuerydslBindings bindings, QCustomer user) {
        bindings.bind(user.name).first(StringExpression::containsIgnoreCase);

        bindings.bind(user.createdOn).all((path, value) -> {
            List<? extends LocalDateTime> dates = new ArrayList(value);
            if (dates.size() == 1) {
                return Optional.of(path.eq(dates.get(0)));
            } else {
                LocalDateTime from = dates.get(0);
                LocalDateTime to = dates.get(1);
                return Optional.of(path.between(from, to));
            }
        });
        bindings.bind(user.lastLogin).all((path, value) -> {
            List<? extends LocalDateTime> dates = new ArrayList(value);
            if (dates.size() == 1) {
                return Optional.of(path.eq(dates.get(0)));
            } else {
                LocalDateTime from = dates.get(0);
                LocalDateTime to = dates.get(1);
                return Optional.of(path.between(from, to));
            }
        });
    }
}
