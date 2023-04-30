package com.nukang.app.city;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/mst/cities")
public class CityController {

    private  final  CityService service;

    @RequestMapping
    public List<City> getAll(@QuerydslPredicate(root = City.class) Predicate predicate){
        return service.getAll(predicate);
    }

    @PostMapping("")
    public ResponseEntity createCity(@RequestBody City city){
        return ResponseEntity.ok(service.create(city));
    }
}
