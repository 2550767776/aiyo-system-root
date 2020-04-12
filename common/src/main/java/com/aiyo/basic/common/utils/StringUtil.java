package com.aiyo.basic.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具类
 *
 * @author boylin
 * @create 2018-04-05 12:10
 */
@Slf4j
public class StringUtil {

    // 定义下划线
    private static final char UNDERLINE = '_';

    /**
     * String为空判断
     *
     * @param str 需判断字符串
     * @return true:为空 false:不为空
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * String不为空判断
     *
     * @param str 需判断字符串
     * @return true:不为空 false:为空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 驼峰转下划线工具
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    public static String camelToUnderline(String param) {
        if (isNotEmpty(param)) {
            int len = param.length();
            StringBuilder sb = new StringBuilder(len);

            for (int i = 0; i < len; ++i) {
                char c = param.charAt(i);
                if (Character.isUpperCase(c)) {
                    sb.append(UNDERLINE);
                    sb.append(Character.toLowerCase(c));
                } else {
                    sb.append(c);
                }
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * 下划线转驼峰工具
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    public static String underlineToCamel(String param) {
        if (isNotEmpty(param)) {
            int len = param.length();
            StringBuilder sb = new StringBuilder(len);
            for (int i = 0; i < len; ++i) {
                char c = param.charAt(i);
                if (c == 95) {
                    ++i;
                    if (i < len) {
                        sb.append(Character.toUpperCase(param.charAt(i)));
                    }
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return "";
        }
    }


    /**
     * 2018031423513915210426990971
     * 生成28位的数字编码 生成订单单号
     *
     * @return
     */
    public static String creadNumber() {
        Date date = new Date();
        int max = 99;
        int min = 10;
        Random random = new Random();
        String currentTimeMillis = System.currentTimeMillis() + "";
        return DatetimeUtil.formatDateTimes(date) + currentTimeMillis.substring(0, currentTimeMillis.length() - 1) + String.valueOf(random.nextInt(max) % (max - min + 1) + min);
    }


    /**
     * 更换img标签src属性值
     *
     * @param htmlText
     * @param imgUrls
     * @return
     */
    public static String setImagesDomain(String htmlText, List<String> imgUrls) {
        Document doc = Jsoup.parse(htmlText);
        Elements srcs = doc.select("img[src]");
        if (srcs.size() < 1) {
            return htmlText;
        }
        if (imgUrls.size() < 1) {
            return htmlText;
        }
        int i = 0;
        for (Element element : srcs) {
            if (i < imgUrls.size()) {
                element.attr("src", imgUrls.get(i));
                i++;
            }
        }
        return doc.html();
    }

    /**
     * 获取富文本中的图片src
     *
     * @param htmlText
     * @return
     */
    public static List<String> getHtmlTextImgSrc(String showUrl, String htmlText) {
        List<String> stringList = new ArrayList<>();
        if (isNotEmpty(htmlText)) {
            Document doc = Jsoup.parse(htmlText);
            Elements elements = doc.select("img");
            for (Element el : elements) {
                String urlStr = el.attr("src");
                System.out.println("urlStr=" + urlStr);
                if (urlStr.indexOf(showUrl) != -1) {
                    stringList.add(urlStr);
                } else {
                    System.out.println("不是平台上传的图片");
                }
            }
        }
        return stringList;
    }


    /**
     * 判断字符串中是否包含中文
     *
     * @param str 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 解码
     *
     * @param str
     * @return
     */
    public static String StrDencode(String str) {
        try {
            if (StringUtil.isNotEmpty(str)) {
                str = URLDecoder.decode(str, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            System.err.println("解码异常：" + e.getMessage());
        }
        return str;
    }

    /**
     * 编码
     *
     * @param str
     * @return
     */
    public static String StrEncode(String str) {
        try {
            if (StringUtil.isNotEmpty(str)) {
                str = URLEncoder.encode(str, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            System.err.println("编码异常：" + e.getMessage());
        }
        return str;
    }

}
