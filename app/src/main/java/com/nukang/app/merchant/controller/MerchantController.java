package com.nukang.app.merchant.controller;

import com.nukang.app.merchant.model.Merchant;
import com.nukang.app.merchant.repository.MerchantRepository;
import com.nukang.app.merchant.service.MerchantService;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchants")
public class MerchantController{

    private final MerchantService service;

    @GetMapping("")
    ResponseEntity list(Principal principal, @QuerydslPredicate(root = Merchant.class, bindings = MerchantRepository.class)
                                Predicate predicate, Pageable pageable, @RequestParam(required = false) String findCategory){
//        System.out.println("PRINCIPAL : " + principal.getName());
        Page<Merchant> result = service.getMerchant(predicate, pageable, findCategory);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/{userId}")
    ResponseEntity list(@PathVariable("userId") String userId){

        Merchant result = service.findById(userId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/create")
    ResponseEntity createMerchant(@RequestBody Merchant merchant){
        try {
            System.out.println(merchant.getMerchantCategory() + " CATEGORY");
            Merchant newMerchant = service.createMerchant(merchant);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(e);
        }
        return ResponseEntity.ok(merchant);
    }
}
