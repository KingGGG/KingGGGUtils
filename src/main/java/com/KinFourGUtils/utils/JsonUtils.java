package com.kingggg.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 作者：KingGGG on 16/7/13 09:48
 * 描述：
 */
public class JsonUtils {
    /**
     * 功能描述：把JSON数据转换成指定的java对象
     *
     * @param jsonData JSON数据
     * @param clazz    指定的java对象
     * @return
     * @throws Exception
     * @author myclover
     */
    public static <T> T getBeanSingle(String jsonData, Class<T> clazz) {
        return JSON.parseObject(jsonData, clazz);
    }

    /**
     * 功能描述：把JSON数据转换成指定的java对象列表
     *
     * @param jsonData JSON数据
     * @param clazz    指定的java对象
     * @return
     * @throws Exception
     * @author myclover
     */
    public static <T> List<T> getBeanList(String jsonData, Class<T> clazz) {
        return JSON.parseArray(jsonData, clazz);
    }

}
