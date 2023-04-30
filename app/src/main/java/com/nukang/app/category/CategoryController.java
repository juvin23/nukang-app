package com.nukang.app.category;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/mst/category")
public class CategoryController {
    @Autowired
    CategoryService service;

    @GetMapping("")
    public List<Category> getCategories(){
        return service.getAll();
    }


    @PostMapping("")
    public ResponseEntity create(@RequestBody Category category){
        return ResponseEntity.ok(service.create(category));
    }
}
