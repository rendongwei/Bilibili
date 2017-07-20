package com.don.bilibili.http;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class JsonObjectResponseBodyConverter  implements Converter<ResponseBody, JSONObject> {

    static final JsonObjectResponseBodyConverter INSTANCE = new JsonObjectResponseBodyConverter();

    @Override
    public JSONObject convert(ResponseBody value) throws IOException {
        try {
            return new JSONObject(value.string());
        } catch (JSONException e) {
        }
        return null;
    }
}
