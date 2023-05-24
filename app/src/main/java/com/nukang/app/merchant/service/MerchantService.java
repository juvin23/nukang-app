package com.nukang.app.merchant.service;


import com.nukang.app.merchant.model.Merchant;
import com.nukang.app.merchant.model.MerchantCategories;
import com.nukang.app.merchant.model.QMerchant;
import com.nukang.app.merchant.repository.MerchantRepository;
import com.nukang.app.user.model.Customer;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class MerchantService {
    private final MerchantRepository merchantRepository;

    public MerchantService(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }
    public Page<Merchant> getMerchant(Predicate predicate, Pageable pageable,String category){
        QMerchant qmerchant = QMerchant.merchant;
        BooleanBuilder filterCategory = new BooleanBuilder(predicate);
        if(category != null) filterCategory.and(qmerchant.category.any().category.categoryId.equalsIgnoreCase(category));;


        return merchantRepository.findAll(filterCategory,pageable);
    }

    public Merchant createMerchant(Merchant merchant) throws Exception {
        validateMerchant(merchant);

        log.info("create merchant");
        String uuid = merchant.getMerchantId().trim();
        while (merchant.getMerchantId().trim().equals("") && merchantRepository.findByMerchantId(uuid) != null){
            uuid = UUID.randomUUID().toString().replace("-","");
        }
        merchant.setMerchantId(uuid);
        log.info("merchant Id : {}",uuid);
        String[] categories = merchant.getMerchantCategory().split(",");
        Set<MerchantCategories> category = new HashSet<>();
        for(String c : categories){
            MerchantCategories newMerchantCategory = new MerchantCategories();
            newMerchantCategory.setMerchant(merchant);
            newMerchantCategory.setCategoryId(c);
            category.add(newMerchantCategory);

            log.info(c + " - " + uuid);
        }
        merchant.setCategory(category);
        for(MerchantCategories cat : merchant.getCategory()){
            cat.setMerchant(merchant);
        }
        merchant.setMerchantId(uuid.toString());
        merchant.setCreatedBy("System");
        merchant.setCreatedOn(LocalDateTime.now());
        merchant.setStatus("created");
        merchant.setRating(0);
        merchant.setRatingCount(0);
        Merchant savedMerchant = null;
        try {
            savedMerchant = merchantRepository.save(merchant);
        }catch (Exception e){
            log.error("error creating merchant {}",merchant.getName() + merchant.getNumber());
            log.error(e.getMessage());
        }
        log.info("user created - {}", merchant.getMerchantId());

        return savedMerchant;
    }



    private void validateMerchant(Merchant merchant) throws Exception {
//        if(isEmpty(merchant.getName())) log.info("user name is empty");

    }

    private boolean isEmpty(String s){
        return s == null || s.trim().equals("");
    }

    public Merchant findById(String userId) {
        return merchantRepository.findByMerchantId(userId).orElse(null);
    }

    public Merchant updateMerchant(Merchant merchant) throws Exception {
        String uuid = merchant.getMerchantId().trim();
        log.info("[update-Merchant] " + uuid +" start . . .");
        Merchant updatedMerchant = merchantRepository.findByMerchantId(uuid).orElse(null);
        if(updatedMerchant == null){
            log.info("Merchant Id : " + uuid);
            throw new Exception("Merchant ID Invalid");
        }
        String[] categories = merchant.getMerchantCategory().split(",");
        Set<MerchantCategories> category = new HashSet<>();
        for(String c : categories){
            MerchantCategories newMerchantCategory = new MerchantCategories();
            newMerchantCategory.setMerchant(merchant);
            newMerchantCategory.setCategoryId(c);
            category.add(newMerchantCategory);
        }
        updatedMerchant.setCategory(category);
        for(MerchantCategories cat : updatedMerchant.getCategory()){
            cat.setMerchant(updatedMerchant);
        }
        updatedMerchant.setAddress(merchant.getAddress());
        updatedMerchant.setCityCode(merchant.getCityCode());
        updatedMerchant.setProvinceCode(merchant.getProvinceCode());
        updatedMerchant.setEmail(merchant.getEmail());
        updatedMerchant.setDescription(merchant.getDescription());
        updatedMerchant.setNumber(merchant.getNumber());
        updatedMerchant.setName(merchant.getName());
        updatedMerchant.setStatus("updated");
        updatedMerchant.setEditedOn(LocalDateTime.now());
        updatedMerchant.setEditedBy("System");
        try {
            updatedMerchant = merchantRepository.save(updatedMerchant);
        }catch (Exception e){
            log.error("error Update Merchant {}",updatedMerchant.getName() + updatedMerchant.getNumber());
            log.error(e.getMessage());
        }
        log.info("Merchant created - {}", updatedMerchant.getMerchantId());

        return updatedMerchant;
    }
}
