package com.nukang.app.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/rating")
public class RatingController {
    @Autowired
    RatingService ratingService;

    @PostMapping("")
    public ResponseEntity postRating(@RequestBody Rating rating) {
        Rating posted = null;
        try {
            posted = ratingService.post(rating);
            return ResponseEntity.ok(posted);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
