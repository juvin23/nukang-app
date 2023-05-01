package com.nukang.app.rating;

import com.nukang.app.user.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RatingService {
    @Autowired
    RatingRepository ratingRepository;
    public Rating post(Rating rating, AppUser appUser) throws Exception {
        String ratingId = UUID.randomUUID().toString();
        validateRating(rating);
        rating.setId(ratingId.replace("-",""));
        rating.setCreateDate(LocalDateTime.now());
        rating.setCreatedBy(appUser.getUserId());
        ratingRepository.save(rating);
        return rating;
    }

    void validateRating(Rating rating) throws  Exception{
        if(rating.getRating() > 5 && rating.getRating() <= 0)
            throw new Exception("rating out of range");
    }
}
