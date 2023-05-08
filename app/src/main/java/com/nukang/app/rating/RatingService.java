package com.nukang.app.rating;

import com.nukang.app.merchant.model.Merchant;
import com.nukang.app.merchant.repository.MerchantRepository;
import com.nukang.app.user.AppUser;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final MerchantRepository merchantRepository;
    private final RatingRepository ratingRepository;
    private Logger log = LoggerFactory.getLogger(RatingService.class);

    public Rating post(Rating rating, AppUser appUser) throws Exception {
        String ratingId = UUID.randomUUID().toString();
        validateRating(rating);
        rating.setId(ratingId.replace("-",""));
        rating.setCreateDate(LocalDateTime.now());
        rating.setCreatedBy(appUser.getUserId());
        Merchant merchant = merchantRepository.findByMerchantId(appUser.getUserId()).orElse(null);
        if(merchant == null){
            log.error("Merchant tidak ditemukan  "  + appUser.getUserId());
            return rating;
        }
        int ratingCount = merchant.getRatingCount();
        merchant.setRating(merchant.getRating()*ratingCount + rating.getRating());
        ratingCount++;
        merchant.setRatingCount(ratingCount);
        merchant.setRating(merchant.getRating()/ratingCount);
        merchantRepository.save(merchant);
        ratingRepository.save(rating);
        return rating;
    }

    void validateRating(Rating rating) throws  Exception{
        if(rating.getRating() > 5 && rating.getRating() <= 0)
            throw new Exception("rating out of range");
    }

    public Page getRating(Pageable pageable, Predicate predicate) {
        Page ratings = ratingRepository.findAll(predicate,pageable);
        return ratings;
    }

    public RatingCount getTotalRating(String userId) {
        log.info("[get-total] " + userId);
        RatingCount total = ratingRepository.getRateCount(userId);

        return total;
    }
}
