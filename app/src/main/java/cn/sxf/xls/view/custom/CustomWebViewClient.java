package cn.sxf.xls.view.custom;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.sxf.xls.inter.WebViewCallback;

/**
 * 自定义WebViewClient
 * @author flh
 */

public class CustomWebViewClient extends WebViewClient {

    public WebViewCallback mWebViewCallback;

    public void setmWebViewCallback(WebViewCallback mWebViewCallback) {
        this.mWebViewCallback = mWebViewCallback;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        mWebViewCallback.beforePageStarted(view,url,favicon);
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        mWebViewCallback.overrideUrlLoading(view,url);
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        mWebViewCallback.afterPageFinished(view,url,view.getTitle());
        super.onPageFinished(view, url);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        mWebViewCallback.doWithReceivedSslError(view,handler,error);
        handler.proceed();
    }
}
