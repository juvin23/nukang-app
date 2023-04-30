package com.nukang.app.Auth;

import com.nukang.app.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/auth/")
public class AuthController {
    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity register(@RequestBody AppUser user) throws Exception {
        AuthResponse response = null;
        try{
             authService.register(user);
            response = authService.authenticate(user);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
    @PostMapping("/authenticate")
    public ResponseEntity authenticate(
            @RequestBody AppUser request
    ) throws Exception {
        AuthResponse response = null;
        try {
            response = authService.authenticate(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("email atau kata sandi salah.");
        }
        return ResponseEntity.ok(response);
    }
}
