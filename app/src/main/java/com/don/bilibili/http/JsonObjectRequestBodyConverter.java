package com.don.bilibili.http;


import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

public class JsonObjectRequestBodyConverter<T> implements Converter<T, RequestBody> {
    static final JsonObjectRequestBodyConverter<Object> INSTANCE = new JsonObjectRequestBodyConverter<>();
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain; charset=UTF-8");

    private JsonObjectRequestBodyConverter() {
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        return RequestBody.create(MEDIA_TYPE, String.valueOf(value));
    }
}
