package com.nukang.app.city;

import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository< City, String> , QuerydslPredicateExecutor<City> {
    List<City> findAll(Predicate predicate);
}
