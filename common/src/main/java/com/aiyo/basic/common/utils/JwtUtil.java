package com.aiyo.basic.common.utils;

import com.aiyo.basic.common.constant.CommonConstant;
import com.aiyo.basic.vo.UserVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.Date;

/**
 * JwtUtil 校验工具
 **/
@Slf4j
public class JwtUtil {

    public static long JWT_EXPIRE_TIME = 30 * 60 * 1000;//token过期时间30分钟 创建token的时候设置，从rids中获取

    /**
     * 校验token是否正确
     *
     * @param userJsonStr 用户表json字符串
     * @param token       密钥
     * @param secret      系统标识+ip地址
     * @param tokenSalt   md5加密过后的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String userJsonStr, String secret, String tokenSalt) {
        try {
            //根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(new SimpleHash("MD5", secret, tokenSalt).toString());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim(CommonConstant.JWT_KEY, userJsonStr)
                    .build();
            //效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getString(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(CommonConstant.JWT_KEY).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获取token 超时时间
     *
     * @param token
     * @return
     */
    public static Date getWithExpiresAt(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getExpiresAt();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static JSONObject getJSONObject(String token) {
        return JSON.parseObject(JwtUtil.getString(token));
    }

    /**
     * 获得token中的用户loginId
     */
    public static UserVo getUserVo(String token) {
        return JSONObject.parseObject(JwtUtil.getString(token), UserVo.class);
    }

    /**
     * 生成签名,5min后过期
     *
     * @param jsonStr   用户信息
     * @param tokenSalt md5加密过后的密码
     * @return 加密的token
     */
    public static String createToken(String jsonStr, String tokenSalt) {
        Date date = new Date(System.currentTimeMillis() + JWT_EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(new SimpleHash("MD5", tokenSalt).toString());
        // 附带username信息
        return JWT.create()
                .withClaim(CommonConstant.JWT_KEY, jsonStr)
                .withExpiresAt(date)
                .sign(algorithm);

    }

}
