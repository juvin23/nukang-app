package com.nukang.app.transaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nukang.app.user.AppUser;
import com.nukang.app.user.AppUserRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transaction")
public class TransactionController {
    private static Logger log = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService service;
    private final AppUserRepository appUserRepository;

    @GetMapping("")
    private ResponseEntity list(@PageableDefault  Pageable pageable,
                                @QuerydslPredicate(root = Transaction.class, bindings = TransactionRepository.class) Predicate predicate) {
        Page<Transaction> transactions = null;
        try {
            transactions = service.list(predicate, pageable);
        }catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/count/{mId}")
    private ResponseEntity transactionCount(@PathVariable(name = "mId") String mId){
        TransactionCount count = service.count(mId);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/create")
    private ResponseEntity create(@RequestBody Transaction paramTrx) throws JsonProcessingException {
        Transaction created = null;
        log.info("create transaction");
        try {
            created = service.create(paramTrx);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok(created);
    }

    @PutMapping("/request-price/{trxId}")
    private ResponseEntity requestPrice(@PathVariable(name = "trxId") String trxId,
                                        @RequestParam("amount") long amount,
                                        Principal principal){
        AppUser appUser = appUserRepository.findByUsername(principal.getName()).orElse(null);
        if(appUser == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        Transaction updated = null;
        try {
            updated = service.requestPrice(trxId, amount,appUser);
        }catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }

        return ResponseEntity.ok(updated);
    }

    @PutMapping("/approve/{trxId}")
    private ResponseEntity approve(@PathVariable(name = "trxId") String trxId, Principal principal){
        Transaction updated = null;
        AppUser appUser = appUserRepository.findByUsername(principal.getName()).orElse(null);
        if(appUser == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        try {
            updated = service.approve(trxId,appUser);
        }catch (Exception e){
            // nanti dibuat custom error
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(updated);
    }

    @PutMapping("/clear-notif")
    private ResponseEntity approve(Principal principal){
        AppUser appUser = appUserRepository.findByUsername(principal.getName()).orElse(null);
        if(appUser == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        try {
            service.clearNotif(appUser);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(appUser);
    }

    @PutMapping("/reject/{tId}")
    public ResponseEntity reject(@PathVariable("tId") String transactionId, @RequestParam("reason") String reason, Principal principal){
        AppUser appUser = appUserRepository.findByUsername(principal.getName()).orElse(null);
        if(appUser == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        try{
            service.reject(transactionId,reason, appUser);
        }catch (Exception e){
            return ResponseEntity.ok(e);
        }
        return ResponseEntity.ok("");
    }
}