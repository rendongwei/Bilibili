package com.bilibili.nativelibrary;


public final class SignedQuery {

    private String url;
    private String sign;

    public SignedQuery(String url, String sign) {
        this.url = url;
        this.sign = sign;
    }

    public String getUrl() {
        return url;
    }

    public String getSign() {
        return sign;
    }

    @Override
    public String toString() {
        if (this.url == null) return "";
        if (this.sign == null) return this.url;
        return this.url + "&sign=" + this.sign;
    }
}
