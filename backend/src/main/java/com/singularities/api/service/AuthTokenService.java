package com.singularities.api.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.singularities.api.data.entity.AuthTokenModel;
import com.singularities.api.data.entity.RoleModel;
import com.singularities.api.data.entity.UserModel;
import com.singularities.api.data.repository.AuthTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthTokenService {

    private final UserService userService;
    private final AuthTokenRepository authTokenRepository;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${singularities.app.jwtSecret}")
    private String jwtSecret;

    @Value("${singularities.app.jwtExpirationMs}")
    private int jwtExpirationMs;


    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }


    public static String generateSecureRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }


    public AuthTokenModel create(UserModel user) {
        deleteAllByUser(user);

        AuthTokenModel authToken = new AuthTokenModel();
        authToken.setUser(user);
        authToken.setExpiryDate(LocalDateTime.now().plusMinutes(10));
        authToken.setSecurityCode(generateSecureRandomString(10));

        String jwt = Jwts.builder()
                .setSubject((user.getEmail()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .claim("roles", user.getRoles().stream().map(RoleModel::getName).toList())
                .compact();

        authToken.setJwt(jwt);

       return authTokenRepository.save(authToken);
    }


    public List<String> getRolesFromJwt(String jwt) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(jwt).getBody();
        Object rawRoles = claims.get("roles");
        return objectMapper.convertValue(rawRoles, new TypeReference<>() {});
    }


    private void deleteAllByUser(UserModel user) {
        authTokenRepository.deleteAllByUser(user);
        authTokenRepository.flush();
    }


    public Optional<AuthTokenModel> getTokenByEmailAndSecurityCode(String email, String securityCode) {
       return authTokenRepository.findByUser_EmailAndSecurityCode(email, securityCode);
    }


    public UserModel getUserFromJwt(String jwt) {
        String email = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();

        return userService.findByEmail(email);
    }


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
