package cn.sxf.xls.inter;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;


/**
 *  加载WebView时接口回调
 *  @author flh
 */
public interface WebViewCallback {
    // 加载网页前调用
    public void beforePageStarted(WebView view, String url, Bitmap favicon);
    // 点击网页中的链接是否在WebView里跳转
    public void overrideUrlLoading(WebView view, String url);
    // 加载完网页后调用
    public void afterPageFinished(WebView view, String url, String title);
    // 加载网页时处理错误
    public void doWithReceivedSslError(WebView view, SslErrorHandler handler, SslError error);
}
