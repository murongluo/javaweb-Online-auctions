package com.example.demopmxt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {

        private static final String signKey = "t/qe4T5nE0g3KvN76Nz2G+Z3m+KFvVxGzTvoQx0DGf8eytM9Ln8H6h9lGyy4X3vLZ7/3qPIxH82ZL9L5Jz8eA==";
        private static final Long expire = 43200000L;

        public static String generateJwt(Map<String, Object> claims){
            String jwt = Jwts.builder()
                    .addClaims(claims)
                    .signWith(SignatureAlgorithm.HS256, signKey)
                    .setExpiration(new Date(System.currentTimeMillis() + expire))
                    .compact();
            return jwt;
        }

        public static Claims parseJWT(String jwt){
            Claims claims = Jwts.parser()
                    .setSigningKey(signKey)
                    .parseClaimsJws(jwt)
                    .getBody();
            return claims;
        }
    }


