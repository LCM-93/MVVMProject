package com.x5webview.lib.view;

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019/12/18 14:36
 * Desc:
 * *****************************************************************
 */
public class CookieValue {
    private String domain;
    private String param;

    public CookieValue() {
    }

    public CookieValue(String domain) {
        this.domain = domain;
    }

    public CookieValue(String domain, String param) {
        this.domain = domain;
        this.param = param;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "CookieValue{" +
                "domain='" + domain + '\'' +
                ", param='" + param + '\'' +
                '}';
    }
}
