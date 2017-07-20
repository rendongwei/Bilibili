package com.don.bilibili.http;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class JsonObjectConverterFactory extends Converter.Factory {

    public static JsonObjectConverterFactory create (){
        return  new JsonObjectConverterFactory();
    }
    private JsonObjectConverterFactory(){

    }
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if (type == String.class
                || type == boolean.class
                || type == Boolean.class
                || type == byte.class
                || type == Byte.class
                || type == char.class
                || type == Character.class
                || type == double.class
                || type == Double.class
                || type == float.class
                || type == Float.class
                || type == int.class
                || type == Integer.class
                || type == long.class
                || type == Long.class
                || type == short.class
                || type == Short.class) {
            return JsonObjectRequestBodyConverter.INSTANCE;
        }
        return null;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return JsonObjectResponseBodyConverter.INSTANCE;
    }
}
