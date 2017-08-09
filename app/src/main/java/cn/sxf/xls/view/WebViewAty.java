package cn.sxf.xls.view;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;

import cn.sxf.xls.R;
import cn.sxf.xls.global.Constant;
import cn.sxf.xls.inter.WebViewCallback;
import cn.sxf.xls.tool.DialogueTools;
import cn.sxf.xls.tool.WebViewUtil;

/**
 * 加载WebView界面
 * @author flh
 */

public class WebViewAty extends BaseAty implements WebViewCallback {

    public String title="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adver_activity);
        init();
    }

    @Override
    public void init(){
        super.init();
        mWebView = getViewTag(R.id.adver_webView);
        WebViewUtil.loadingWebPage(mWebView, Constant.mWebViewUrl,this);
    }

    @Override
    public void beforePageStarted(WebView view, String url, Bitmap favicon) {
        // 开始加载进度条
        DialogueTools.ToastShortDialogue(this,R.string.loadding_start);
    }

    @Override
    public void afterPageFinished(WebView view, String url, String title) {
        // 加载进度条结束
        DialogueTools.ToastShortDialogue(this,R.string.loadding_end);
        title = view.getTitle();
        if(!TextUtils.isEmpty(title)){
            initTitle(title, true, true);
        }
    }

    @Override
    public void overrideUrlLoading(WebView view, String url) {}

    @Override
    public void doWithReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        // 加载时异常的处理x
    }

    @Override
    public void isVisibilityTitleArea() {
        initTitle(title, true, true);
    }

    @Override
    public void dynamicReqPermission() {}
}
