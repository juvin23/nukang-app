package com.nukang.app.rating;

import com.nukang.app.merchant.model.Merchant;
import com.nukang.app.merchant.repository.MerchantRepository;
import com.nukang.app.transaction.Transaction;
import com.nukang.app.transaction.TransactionRepository;
import com.nukang.app.user.AppUser;
import com.nukang.app.user.AppUserRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rating")
public class RatingController {
    private final RatingService ratingService;
    private final MerchantRepository merchantRepository;
    private final AppUserRepository appUserRepository;

    @PostMapping("")
    public ResponseEntity postRating(@RequestBody Rating rating, Principal principal) {
        Rating posted = null;
        AppUser appUser = appUserRepository.findByUsername(principal.getName()).orElse(null);
        Merchant merchant = merchantRepository.findByMerchantId(rating.getMerchantId()).orElse(null);
        try {

            if(merchant!= null && appUser != null){
                posted = ratingService.post(rating,appUser);
                int ratingCount = merchant.getRatingCount();
                merchant.setRating(merchant.getRating()*ratingCount + rating.getRating());
                merchant.setRatingCount(ratingCount+1);
                merchant.setRating(merchant.getRating()/ratingCount);
            }
            return ResponseEntity.ok(posted);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity getRating(@PageableDefault Pageable pageable,
                                    @QuerydslPredicate(root = Rating.class, bindings = RatingRepository.class) Predicate predicate){
        Page ratingList = ratingService.getRating(pageable, predicate);

        return ResponseEntity.ok(ratingList);
    }

    @GetMapping("total-rating/{userId}")
    public ResponseEntity response(@PathVariable("userId") String userId){

        return ResponseEntity.ok(ratingService.getTotalRating(userId));
    }
}
