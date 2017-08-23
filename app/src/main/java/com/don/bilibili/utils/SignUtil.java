package com.don.bilibili.utils;


import com.bilibili.nativelibrary.LibBili;
import com.bilibili.nativelibrary.SignedQuery;

import java.util.HashMap;

public class SignUtil {

    public static SignedQuery getSign(HashMap<String, String> map) {
        SignedQuery query = LibBili.a(map);
        return query;
    }
}
