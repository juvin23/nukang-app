package com.nukang.app.province;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/mst/province")
@RequiredArgsConstructor
public class ProvinceController {
    @Autowired
    ProvinceService service;

    @GetMapping("")
    public List<Province> list(@QuerydslPredicate(root = Province.class) Predicate predicate){
        return service.getAll(predicate);
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody Province province){
        return ResponseEntity.ok(service.create(province));
    }

}
