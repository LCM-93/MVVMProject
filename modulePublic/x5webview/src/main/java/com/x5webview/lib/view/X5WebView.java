package com.x5webview.lib.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.tencent.smtt.sdk.*;
import com.x5webview.lib.BuildConfig;

import java.util.List;


public class X5WebView extends WebView {
    private static final String TAG = X5WebView.class.getSimpleName();

    public X5WebView(Context context) {
        super(context);
        initWebView();
    }

    public X5WebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initWebView();
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initWebView();
    }


    private void initWebView() {
        clearHistory();
        clearCache(true);
        WebSettings webSettings = getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setSupportZoom(false);
        webSettings.setDomStorageEnabled(true);
        String appCachePath = getContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        //内容自适应屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSettings.setMediaPlaybackRequiresUserGesture(false);
        }
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            webSettings.setDatabasePath("/data/data/" + getContext().getPackageName() + "/databases/");
        }

        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
        setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        setScrollbarFadingEnabled(false);
        if (BuildConfig.DEBUG) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setWebContentsDebuggingEnabled(true);
            }
        }

    }


    /**
     * 同步一下cookie
     */
    public void synCookies(Context context, List<CookieValue> cookieList) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if (cookieList != null && cookieList.size() > 0) {
            for (CookieValue cookieValue : cookieList) {
                cookieManager.setCookie(cookieValue.getDomain(), cookieValue.getParam());
            }
        }
        CookieSyncManager.getInstance().sync();
    }


    public void executeJsParams(String function, Object... params) {
        executeJsParamsCallback(function, null, params);
    }

    public void executeJsParamsCallback(String function, ExecuteJsCallback callback, Object... params) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("javascript:").append(function).append("(");
            if (params != null && params.length > 0) {
                for (Object param : params) {
                    if (param instanceof String) {
                        sb.append("'").append(param).append("'").append(",");
                    }
                    if (param instanceof Integer) {
                        sb.append(param).append(",");
                    }
                    if (param instanceof Boolean) {
                        sb.append(param).append(",");
                    }
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
            }
            sb.append(")");
            String js = sb.toString();
            Log.d(TAG, "executeJs: " + js);
            if (Build.VERSION.SDK_INT < 19) {
                loadUrl(js);
            } else {
                evaluateJavascript(js, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        if (callback != null) callback.callback(s);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void executeJsSimple(String function) {
        executeJsSimple(function, null);
    }

    public void executeJsSimple(String function, ExecuteJsCallback callback) {
        try {
            String js = "javascript:" + function + "()";
            Log.d(TAG, "executeJs: " + js);
            if (Build.VERSION.SDK_INT <= 19) {
                loadUrl(js);
            } else {
                evaluateJavascript(js, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        if (callback != null) callback.callback(s);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface ExecuteJsCallback {
        void callback(String str);
    }

}
