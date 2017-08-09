package cn.sxf.xls.tool;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.sxf.xls.inter.WebViewCallback;
import cn.sxf.xls.view.custom.CustomWebView;
import cn.sxf.xls.view.custom.CustomWebViewClient;


/**
 * 加载WebView
 * @author flh
 */
public class WebViewUtil {

    public static void loadingWebPage(WebView mWebView, String url, WebViewCallback callback) throws NullPointerException{
        CustomWebView customWebPage = new CustomWebView(mWebView,url);
        CustomWebViewClient mWebClient = new CustomWebViewClient();

        mWebClient.setmWebViewCallback(callback);
        customWebPage.setmCustomWebViewClient(mWebClient);
        customWebPage.loadWebView();
    }


}
