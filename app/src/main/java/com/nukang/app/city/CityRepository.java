package com.nukang.app.city;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository< City, String>
        , QuerydslPredicateExecutor<City>
        , QuerydslBinderCustomizer<QCity> {
    List<City> findAll(Predicate predicate);

    @Override
    default void customize(QuerydslBindings bindings, QCity root){
        bindings.bind(root.cityCode).first(StringExpression::startsWithIgnoreCase);
    };
}
