package com.hlbd.electric.util;

import com.google.gson.Gson;
import com.hlbd.electric.feature.login.UserInfo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class JsonUtil {

    private static final String TAG = "JsonUtil";
    private static Gson sJson = new Gson();

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || clazz == null) {
            return null;
        }
        return sJson.fromJson(json, clazz);
    }

    public static <T> T fromJson(String json, Type typeOfT) {

        if (json == null || typeOfT == null) {
            return null;
        }
        return sJson.fromJson(json, typeOfT);
    }

    public static <T> T fromJsonResult(String json, Class<T> clazz) {
        return sJson.fromJson(json, new ParameterizedTypeImpl(UserInfo.class, new Class[]{clazz}));
    }

    public static <T> String toJson(Object obj) {
        try {
            if (obj == null) {
                return null;
            }
            return sJson.toJson(obj);
        }catch (Exception e) {
            LogUtil.e(TAG, "toJson() error", e);
            return null;
        }
    }

    private static class ParameterizedTypeImpl implements ParameterizedType {
        private final Class raw;
        private final Type[] args;

        private ParameterizedTypeImpl(Class raw, Type[] args) {
            this.raw = raw;
            this.args = args != null ? args : new Type[0];
        }

        @Override
        public Type[] getActualTypeArguments() {
            return args;
        }

        @Override
        public Type getRawType() {
            return raw;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }

}
