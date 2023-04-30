package com.nukang.app.province;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProvinceService {
    @Autowired
    ProvinceRepository repo;
    public List<Province> getAll(Predicate predicate) {
        return Optional.of(repo.findAll(predicate)).orElse(null);
    }

    public Province create(Province province) {
        return repo.save(province);
    }
}
