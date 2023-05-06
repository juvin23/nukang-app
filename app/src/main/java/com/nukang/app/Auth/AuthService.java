package com.nukang.app.Auth;

import com.nukang.app.Auth.token.Token;
import com.nukang.app.Auth.token.TokenRepository;
import com.nukang.app.Auth.token.TokenType;
import com.nukang.app.merchant.repository.MerchantRepository;
import com.nukang.app.security.config.JwtService;
import com.nukang.app.user.AppUser;
import com.nukang.app.user.AppUserRepository;
import com.nukang.app.user.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appuserRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final MerchantRepository merchantRepository;
    private final CustomerRepository customerRepository;

    @Transactional(rollbackFor = {Exception.class})
    public AuthResponse register(AppUser user) throws Exception {
        AppUser dbUser = appuserRepository.findByUsername(user.getUsername()).orElse(null);
        if(dbUser != null){
            if(merchantRepository.findByMerchantId(dbUser.getUserId()).isPresent()
                    || customerRepository.findByCustomerId(dbUser.getUserId()).isPresent())
            throw new Exception("Username sudah digunakan");
        }

        String uuid = UUID.randomUUID()
                            .toString()
                            .replace("-","");

        AppUser newUser = new AppUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        newUser.setUserId(uuid);
        log.info(newUser.getUserId() + " " + user.getUsername() + " " + user.getPassword());
        var savedUser = appuserRepository.save(newUser);
        var jwtToken = jwtService.generateToken(newUser);
        var refreshToken = jwtService.generateRefreshToken(newUser);
//        saveUserToken(newUser, jwtToken);
        log.info(refreshToken + " " + jwtToken);
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .uid(newUser.getUserId())
                .role(newUser.getRole())
                .build();
    }

    public AuthResponse authenticate(AppUser request) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = appuserRepository.findByUsername(request.getUsername()).orElse(null);
        if(user == null) throw new Exception("User Not Found");
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .uid(user.getUserId())
                .role(user.getRole())
                .build();
    }

    private void saveUserToken(AppUser user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(AppUser user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
