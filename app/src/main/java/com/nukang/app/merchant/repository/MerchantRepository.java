package com.nukang.app.merchant.repository;

import com.nukang.app.merchant.model.Merchant;
import com.nukang.app.merchant.model.QMerchant;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringPath;
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
public interface MerchantRepository extends JpaRepository<Merchant, Long>,
                                            QuerydslPredicateExecutor<Merchant>,
                                            QuerydslBinderCustomizer<QMerchant> {

    Page<Merchant> findAll(Predicate predicate, Pageable pageable);

    Optional<Merchant> findByMerchantId(String id);
    Merchant findByEmail(String email);

    @Override
    default void customize(QuerydslBindings bindings, QMerchant merchant) {
        bindings.bind(String.class).first((StringPath path, String value)->{
            return path.containsIgnoreCase(value);
        });

        bindings.bind(merchant.name).all((path,value) -> {
            List<String> name = new ArrayList(value);
            return Optional.of(path.containsIgnoreCase(name.get(0)));
        });

        bindings.bind(merchant.createdOn).all((path, value) -> {
            List<? extends LocalDateTime> dates = new ArrayList(value);
            if (dates.size() == 1) {
                return Optional.of(path.eq(dates.get(0)));
            } else {
                LocalDateTime from = dates.get(0);
                LocalDateTime to = dates.get(1);
                return Optional.of(path.between(from, to));
            }
        });
        bindings.bind(merchant.lastLogin).all((path, value) -> {
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
