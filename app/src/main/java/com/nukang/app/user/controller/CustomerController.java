package com.nukang.app.user.controller;

import com.nukang.app.user.model.Customer;
import com.nukang.app.user.repository.CustomerRepository;
import com.nukang.app.user.service.CustomerService;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("")
    ResponseEntity list(@QuerydslPredicate(root = Customer.class, bindings = CustomerRepository.class)
                                                Predicate predicate, Pageable pageable){

        Page<Customer> result = service.getCustomer(predicate, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{userId}")
    ResponseEntity list(@PathVariable("userId") String userId){
        Customer result = service.findCustomerById(userId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/create")
    ResponseEntity createCustomer(@RequestBody Customer customer) throws Exception {
        try {
            Customer newCustomer = service.createCustomer(customer);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return ResponseEntity.ok(customer);
    }
    @PostMapping("/update")
    ResponseEntity updateCustomer(@RequestBody Customer customer) throws Exception {
        try {
            Customer updatedCustomer = service.updateCustomer(customer);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return ResponseEntity.ok(customer);
    }
}
