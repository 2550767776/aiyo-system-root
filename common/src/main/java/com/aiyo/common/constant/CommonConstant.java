package com.aiyo.common.constant;

/**
 * 公共用常量配置
 **/
public interface CommonConstant {

    /**
     * token请求头名称
     */
    String TOKEN_HEADER = "Authorization";
    /**
     * jwt签名
     */
    String JWT_SIGN_KEY = "AIYO";

    /**
     * jwt 用户信息key
     */
    String JWT_KEY = "userJsonStr";
    /**
     * jwt过期时间 Redis保存的key
     */
    String JWT_EXPIRE_TIME_KEY = "_JWT_EXPIRE_TIME";
    //long JWT_EXPIRE_TIME = 30 * 60 * 1000;//默认 token过期时间30分钟 创建token的时候设置，从rids中获取

    /**
     * 不需要验证token请求信息Redis保存的key
     */
    String NOT_VERIFY_TOKEN = "_NOT_VERIFY_TOKEN";

}
