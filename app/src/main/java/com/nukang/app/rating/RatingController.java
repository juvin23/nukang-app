package com.nukang.app.rating;

import com.nukang.app.user.AppUser;
import com.nukang.app.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rating")
public class RatingController {
    private final RatingService ratingService;
    private final AppUserRepository appUserRepository;

    @PostMapping("")
    public ResponseEntity postRating(@RequestBody Rating rating, Principal principal) {
        Rating posted = null;
        AppUser appUser = appUserRepository.findByUsername(principal.getName()).orElse(null);
        try {
            posted = ratingService.post(rating,appUser);
            return ResponseEntity.ok(posted);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
