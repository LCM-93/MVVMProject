package com.x5webview.lib.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019/11/14 14:08
 * Desc:
 * *****************************************************************
 */
public class X5WebChromeClient extends WebChromeClient {

    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;
    private final static int FILECHOOSER_RESULTCODE = 10101;

    public ValueCallback<Uri[]> mUploadMessageForAndroid5;

    private ValueCallback<Uri> mUploadMessage;

    private Activity activity;
    private TitleChangeListener mTitleChangeListener;
    private ProgressChangeListener mProgressChangeListener;

    public void setTitleChangeListener(TitleChangeListener titleChangeListener) {
        this.mTitleChangeListener = titleChangeListener;
    }

    public void setProgressChangeListener(ProgressChangeListener progressChangeListener) {
        this.mProgressChangeListener = progressChangeListener;
    }

    public X5WebChromeClient(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onProgressChanged(WebView webView, int i) {
        if(mProgressChangeListener != null){
            mProgressChangeListener.onProgressChange(i);
        }
        super.onProgressChanged(webView, i);
    }

    @Override
    public void onReceivedTitle(WebView webView, String s) {
        if(mTitleChangeListener != null){
            mTitleChangeListener.onTitleChange(s);
        }
        super.onReceivedTitle(webView, s);
    }

    @Override
    public void onCloseWindow(WebView webView) {
        super.onCloseWindow(webView);
        if(activity != null){
            activity.finish();
        }
    }

    // For Android 3.0+
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        if (mUploadMessage != null) return;

        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        if (activity != null) {
            activity.startActivityForResult(Intent.createChooser(i, "文件选择"), FILECHOOSER_RESULTCODE);
        }
    }

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooser(uploadMsg, "");
    }

    // For Android  > 4.1.1
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        openFileChooser(uploadMsg, acceptType);
    }


    @Override
    public boolean onShowFileChooser(WebView webView,
                                     ValueCallback<Uri[]> filePathCallback,
                                     FileChooserParams fileChooserParams) {
        if (mUploadMessageForAndroid5 != null)
            return false;
        mUploadMessageForAndroid5 = filePathCallback;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        if (activity != null) {
            activity.startActivityForResult(Intent.createChooser(i, "文件选择"),
                    FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
        }
        return true;
    }


    public void onActivityResult(int requestCode, int resultCode,
                                 Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != Activity.RESULT_OK ? null
                    : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;

        } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            if (null == mUploadMessageForAndroid5)
                return;
            Uri result = (intent == null || resultCode != Activity.RESULT_OK) ? null
                    : intent.getData();
            if (result != null) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
            }
            mUploadMessageForAndroid5 = null;
        }
    }

    public interface TitleChangeListener {
        void onTitleChange(String title);
    }

    public interface ProgressChangeListener {
        void onProgressChange(int progress);
    }
}
