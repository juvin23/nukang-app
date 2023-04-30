package com.nukang.app.city;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    @Autowired
    CityRepository repo;
    public List<City> getAll(Predicate predicate) {
        return Optional.of(repo.findAll(predicate)).orElse(null);
    }

    public City create(City city) {
        return repo.save(city);
    }
}
