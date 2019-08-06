package com.aiyo.common.result;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装返回信息
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", HttpStatus.SC_OK);
        put("success", true);
    }

    /**
     * 带状态码和消息的错误
     *
     * @param code
     * @param msg
     * @return
     */
    public static R error(int code, String msg) {
        R r = new R();
        r.put("success", false);
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    /**
     * 系统错误
     *
     * @return
     */
    public static R error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

    /**
     * 自带消息的错误，如需错误带数据，使用put操作
     *
     * @param msg
     * @return
     */
    public static R error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    /**
     * 返回成功，不带消息
     *
     * @return
     */
    public static R ok() {
        return new R();
    }

    /**
     * 返回成功，带消息
     *
     * @param msg
     * @return
     */
    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    /**
     * 返回成功，带Map数据集合
     *
     * @param map
     * @return
     */
    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    /**
     * 成功或者失败后的链式操作
     *
     * @param key
     * @param value
     * @return
     */
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
