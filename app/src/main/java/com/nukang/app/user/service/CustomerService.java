package com.nukang.app.user.service;

import com.nukang.app.user.model.Customer;
import com.nukang.app.user.repository.CustomerRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CustomerService {
    private final Logger log = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    public Page<Customer> getCustomer(Predicate predicate, Pageable pageable){
        return customerRepository.findAll(predicate,pageable);
    }

    public Customer createCustomer(Customer customer) throws Exception {
        validateUser(customer);
        String uuid = customer.getCustomerId().trim();
        while (customer.getCustomerId().trim().equals("") || customerRepository.findByCustomerId(uuid).isPresent()){
            uuid = UUID.randomUUID().toString().replace("-","");
        }
        customer.setCustomerId(uuid);
        customer.setCreatedBy("System");
        customer.setCreatedOn(LocalDateTime.now());
        customer.setStatus("created");
        try {
            customerRepository.save(customer);
        }catch (Exception e){
            log.error("error creating Customer {}",customer.getName() + customer.getNumber());
            log.error(e.getMessage());
        }
        log.info("Customer created - {}", customer.getCustomerId());

        return customer;
    }

    public Customer updateCustomer(Customer customer) throws Exception {
        validateUser(customer);
        String uuid = customer.getCustomerId().trim();
        log.info("[update-customer] " + uuid +" start . . .");
        if(customerRepository.findByCustomerId(uuid).isEmpty()){
            log.info("customer Id : " + uuid);
            throw new Exception("Customer ID Invalid");
        }
        customer.setStatus("updated");
        customer.setEditedOn(LocalDateTime.now());
        customer.setEditedBy("System");
        try {
            customerRepository.save(customer);
        }catch (Exception e){
            log.error("error creating Customer {}",customer.getName() + customer.getNumber());
            log.error(e.getMessage());
        }
        log.info("Customer created - {}", customer.getCustomerId());

        return customer;
    }

    private void validateUser(Customer customer) throws Exception {
        if(isEmpty(customer.getName())) throw new Exception("user name is empty");

    }

    private boolean isEmpty(String s){
        return s == null || s.trim().equals("");
    }

    public Customer findCustomer(String username) {
        return customerRepository.findByEmail(username);
    }

    public Customer findCustomerById(String userId) {
        log.info(userId + " USER ID");
        return  customerRepository.findByCustomerId(userId).orElse(null);
    }


}
