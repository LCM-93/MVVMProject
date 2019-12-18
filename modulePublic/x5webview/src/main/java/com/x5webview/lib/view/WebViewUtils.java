package com.x5webview.lib.view;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebStorage;

import java.io.File;

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019/12/11 17:15
 * Desc:
 * *****************************************************************
 */
public class WebViewUtils {
    private static final String TAG = WebViewUtils.class.getSimpleName();


    public static void clearCache(Activity context) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                String cachePath = "/data/data/" + context.getPackageName() + "/databases/";
                deleteFile(new File(cachePath));
            }
            WebStorage.getInstance().deleteAllData();
            context.deleteDatabase("webview.db");
            context.deleteDatabase("webviewCache.db");

            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public static void deleteFile(File file) {

        Log.i(TAG, "delete file path=" + file.getAbsolutePath());

        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
        }
    }



}
