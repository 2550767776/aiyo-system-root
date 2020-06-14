package com.aiyo.basic.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class JwtTokenUtils {

    public static String header = "token";
    private static String Secret = "aiyo";
    private static String ISS = "aiyo";

    // 过期时间 30分钟
    private static long EXPIRATION = 30 * 60;

    //半个月免登陆
    private static long EXPIRATION_REMEMBER = 15 * 3600 * 24;

    private static boolean isRemember = false;


    public static String token(Map<String, String> paramList) {
        long expiration = isRemember ? EXPIRATION_REMEMBER : EXPIRATION;
        JwtBuilder builder = Jwts.builder().signWith(SignatureAlgorithm.HS512, Secret.getBytes())
                .setIssuer(ISS)
                .setSubject(paramList.get("id"))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000));
        // 加入参数列表
        paramList.forEach(builder::claim);
        return builder.compact();
    }


    public static Claims claims(String token) {
        return Jwts.parser().setSigningKey(Secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

}
