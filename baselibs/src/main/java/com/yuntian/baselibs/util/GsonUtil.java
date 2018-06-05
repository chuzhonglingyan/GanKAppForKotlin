package com.yuntian.baselibs.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description  GsonUtil.
 * Created by ChuYingYan on 2018/5/3.
 */
public class GsonUtil {


    private static Gson gson;
    private static JsonParser sJsonParser = new JsonParser();

    static {
        gson = new GsonBuilder().create();
    }

    private GsonUtil() {}

    /**
     * 将object对象转成json字符串
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        String gsonString = "";
        try {
            gsonString = gson.toJson(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gsonString;
    }


    /**
     * 将gsonString转成泛型bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T fromJson(String gsonString, Class<T> cls) {
        T t = null;
        try {
            t = gson.fromJson(gsonString, cls);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return t;
    }


    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> toList(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 转成list
     * 解决泛型问题
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            JsonArray array = sJsonParser.parse(json).getAsJsonArray();
            for (final JsonElement elem : array) {
                list.add(gson.fromJson(elem, cls));
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> toListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> toMap(String gsonString) {
        Map<String, T> map = null;
        try {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return map;
    }

}
