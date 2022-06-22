package com.fiap.registration.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire}")
    private int expire;

    public String generateToken(String userName){
        Map<String, Object> claims = new HashMap<>();

        claims.put(CLAIM_KEY_USERNAME, userName);

        final Date createdDate = new Date();
        claims.put(CLAIM_KEY_CREATED, createdDate);

        return doGenerateToken(claims);
    }

    private String doGenerateToken(Map<String, Object> claims) {
        final Date createdDate = (Date) claims.get(CLAIM_KEY_CREATED);
        final Date expirationDate = new Date(createdDate.getTime() + expire * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsernameFromToken(String token){
        String userName;
        try{
            final Claims claims = getClaimsFromtoken(token);
            userName = claims.getSubject();
        } catch (Exception e) {
            userName = null;
        }
        return userName;
    }

    private Claims getClaimsFromtoken(String token){
        Claims claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e){
            claims = null;
        }
        return claims;
    }

}
