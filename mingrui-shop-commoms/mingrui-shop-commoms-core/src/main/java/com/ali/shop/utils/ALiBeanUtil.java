package com.ali.shop.utils;

import org.springframework.beans.BeanUtils;

/**
 * @ClassName ALiBeanUtil
 * @Description: TODO
 * @Author wangyue
 * @Date 2020/12/25
 * @Version V1.0
 **/
public class ALiBeanUtil {
    public static <T> T copyProperties(Object source,Class<T> clazz){

        try {
            T t = clazz.newInstance();//创建当前类型的实例
            BeanUtils.copyProperties(source,t);
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
