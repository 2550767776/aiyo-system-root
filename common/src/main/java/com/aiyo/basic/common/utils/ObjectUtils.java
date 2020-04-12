package com.aiyo.basic.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjectUtils {
    private static final transient Logger logger = LoggerFactory.getLogger(ObjectUtils.class);

    public static void main(String[] p) {
        List<String> list = new ArrayList<String>();
        Map map = new HashMap();
        map = new TreeMap();
        Set set = new HashSet();
    }

    public static boolean isEquals(Object object1, Object object2) {
        boolean ret = false;
        try {
            if (object1 == null && object2 == null) {
                ret = true;
                return ret;
            }
            ret = object1.equals(object2);
        } catch (NullPointerException e) {
            ret = false;
        }
        return ret;

    }

    /**
     * 比较两字符串是否相等，忽略大小写
     *
     * @param s1
     * @param s2
     * @return
     */
    public static boolean equalsIgnorecase(String s1, String s2) {
        if (s1 == null && s2 == null)
            return true;
        if (s1 != null && s2 != null) {
            if (s1.toLowerCase().equals(s2.toLowerCase()))
                return true;
        }
        return false;
    }

    /**
     * 判断对象是否为空
     *
     * @param obj -参数对象
     * @return boolean -true:表示对象为空;false:表示对象为非空 集合：
     * Collection.isEmpty()
     * 数组：判断数组每个元素，所有元素都为空，即判定数组为空
     * 字符串：判断字符串等于"null"，或去除两端""字窜后返回String.isEmpty()的结果 其它类型返回 false
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null)
            return true;
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }
        if (obj instanceof HashMap) {
            return ((HashMap<?, ?>) obj).isEmpty();
        }
        if (obj instanceof TreeMap) {
            return ((TreeMap<?, ?>) obj).isEmpty();
        }
        if (obj instanceof String) {
            return ((String) obj).equalsIgnoreCase("null") | ((String) obj).trim().isEmpty();
        }

        if (obj instanceof StringBuffer) {
            return ((StringBuffer) obj).length() == 0;
        }

        if (obj.getClass().isArray()) {
            try {
                Object[] a = (Object[]) obj;

                boolean b = true;
                for (Object o : a) {
                    b = b & ObjectUtils.isEmpty(o);

                    if (!b)
                        break;
                }

                return b;
            } catch (ClassCastException e) {
            }
        }

        return false;
    }

    /**
     * @param obj
     * @return
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 校验邮箱是否合规
     *
     * @param email 邮箱
     * @return true 合规 false 不合规
     */
    public static boolean isEmailFormat(String email) {

        Pattern pattern = Pattern
                .compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isEmailAdressFormat(String email) {
        boolean isExist = false;
        if (isEmpty(email)) {
            return isExist;
        }
        Pattern p = Pattern.compile("\\w+@(\\w+\\.)+[a-z]{2,3}");
        Matcher m = p.matcher(email);
        boolean b = m.matches();
        if (b) {
            isExist = true;
        }
        return isExist;
    }

    //判断是否相等
    public static boolean compare(Long id, Long id1) {
        if (ObjectUtils.isNotEmpty(id) && ObjectUtils.isNotEmpty(id1)) {
            if (id.intValue() != id1.intValue()) {
                return false;
            }
        } else if (ObjectUtils.isEmpty(id) && ObjectUtils.isEmpty(id1)) {
            return true;
        } else {
            return false;
        }
        return true;
    }


    /**
     * 泛型方法设置java对象
     *
     * @param entiy
     * @param keys
     * @param keyValue
     * @param <T>
     */
    public static <T> boolean setJavaObj(T entiy, String keys, String keyValue) {
        try {
            Class<T> clazz = (Class<T>) entiy.getClass();
            Field field = clazz.getDeclaredField(keys);
            //设置更新时间
            if (field != null) {
                //开启对私有字段进行设置值
                field.setAccessible(true);
                field.set(entiy, keyValue);
            }
            return true;

        } catch (Exception e) {
            //e.printStackTrace();
            logger.info("字段【:{}】不存在", keys);
            return false;
        }
    }

    /**
     * 将对象转为Map
     * @param obj
     * @return
     * @throws Exception
     */
    public static HashMap<String, Object> convertToMap(Object obj)
            throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            boolean accessFlag = fields[i].isAccessible();
            fields[i].setAccessible(true);

            Object o = fields[i].get(obj);
            if (o != null)
                map.put(varName, o.toString());

            fields[i].setAccessible(accessFlag);
        }
        return map;
    }
    //map转对象
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            Method setter = property.getWriteMethod();
            if (setter != null) {
                setter.invoke(obj, map.get(property.getName()));
            }
        }

        return obj;
    }

}
