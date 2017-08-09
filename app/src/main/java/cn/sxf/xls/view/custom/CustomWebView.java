package cn.sxf.xls.view.custom;

import android.webkit.WebSettings;
import android.webkit.WebView;



/**
 * WebView工具类
 * @author flh
 */
public class CustomWebView {

    private WebView mWebView;
    private CustomWebViewClient mCustomWebViewClient;
    // WebView加载的url
    private String mWebViewUrl;
    // 是否支支持长点击
    private boolean longClickable = false;
    // 是否支持网页调试
    private boolean webContentsDebuggingEnabled = true;
    // 是否支持javascript
    private boolean javaScriptEnabled = true;
    // 可触摸放大缩小
    private boolean builtInZoomControls = true;
    // 隐藏webview缩放按钮
    private boolean displayZoomControls = false;
    // 支持缩放
    private boolean supportZoom = true;
    // 页面适应手机屏幕的分辨率，完整的显示在屏幕上，可以放大缩小
    private boolean loadWithOverviewMode = true;
    //设置支持DomStorage
    private boolean domStorageEnabled = true;
    // 可任意比例缩放
    private boolean useWideViewPort = true;
    private String encoding = "UTF-8";
    private WebSettings.LayoutAlgorithm layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL;
    private int cacheMode = WebSettings.LOAD_NO_CACHE;
    private boolean databaseEnable = true;


    public CustomWebView(WebView mWebView, String mWebViewUrl) {
        this.mWebView = mWebView;
        this.mWebViewUrl = mWebViewUrl;
    }

    /**
     * 加载WebView
     */
    public void loadWebView () throws NullPointerException{
        if(mWebView != null){
            configWebView();
            configWebViewSetting();
            mWebView.loadUrl(mWebViewUrl);
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * 加载WebView
     */
    private void configWebView(){
        mWebView.setLongClickable(longClickable);
        mWebView.setWebContentsDebuggingEnabled(webContentsDebuggingEnabled);
        mWebView.setWebViewClient(mCustomWebViewClient);
    }

    /**
     * WebView setting设置
     */
    private void configWebViewSetting(){
        WebSettings setting = mWebView.getSettings();
        setting.setLayoutAlgorithm(layoutAlgorithm);
        setting.setDefaultTextEncodingName(encoding);
        setting.setJavaScriptEnabled(javaScriptEnabled);
        setting.setBuiltInZoomControls(builtInZoomControls);
        setting.setDisplayZoomControls(displayZoomControls);
        setting.setSupportZoom(supportZoom);
        setting.setDomStorageEnabled(domStorageEnabled);
        setting.setDatabaseEnabled(databaseEnable);
        setting.setLoadWithOverviewMode(loadWithOverviewMode);
        setting.setUseWideViewPort(useWideViewPort);
        setting.setCacheMode(cacheMode);
    }

    public void setmCustomWebViewClient(CustomWebViewClient mCustomWebViewClient) {
        this.mCustomWebViewClient = mCustomWebViewClient;
    }

    public String getmWebViewUrl() {
        return mWebViewUrl;
    }

    public void setmWebViewUrl(String mWebViewUrl) {
        this.mWebViewUrl = mWebViewUrl;
    }

    public WebSettings.LayoutAlgorithm getLayoutAlgorithm() {
        return layoutAlgorithm;
    }

    public void setLayoutAlgorithm(WebSettings.LayoutAlgorithm layoutAlgorithm) {
        this.layoutAlgorithm = layoutAlgorithm;
    }

    public boolean isLongClickable() {
        return longClickable;
    }

    public void setLongClickable(boolean longClickable) {
        this.longClickable = longClickable;
    }

    public boolean isWebContentsDebuggingEnabled() {
        return webContentsDebuggingEnabled;
    }

    public void setWebContentsDebuggingEnabled(boolean webContentsDebuggingEnabled) {
        this.webContentsDebuggingEnabled = webContentsDebuggingEnabled;
    }

    public boolean isJavaScriptEnabled() {
        return javaScriptEnabled;
    }

    public void setJavaScriptEnabled(boolean javaScriptEnabled) {
        this.javaScriptEnabled = javaScriptEnabled;
    }

    public boolean isBuiltInZoomControls() {
        return builtInZoomControls;
    }

    public void setBuiltInZoomControls(boolean builtInZoomControls) {
        this.builtInZoomControls = builtInZoomControls;
    }

    public boolean isDisplayZoomControls() {
        return displayZoomControls;
    }

    public void setDisplayZoomControls(boolean displayZoomControls) {
        this.displayZoomControls = displayZoomControls;
    }

    public boolean isSupportZoom() {
        return supportZoom;
    }

    public void setSupportZoom(boolean supportZoom) {
        this.supportZoom = supportZoom;
    }

    public boolean isLoadWithOverviewMode() {
        return loadWithOverviewMode;
    }

    public void setLoadWithOverviewMode(boolean loadWithOverviewMode) {
        this.loadWithOverviewMode = loadWithOverviewMode;
    }

    public boolean isDomStorageEnabled() {
        return domStorageEnabled;
    }

    public void setDomStorageEnabled(boolean domStorageEnabled) {
        this.domStorageEnabled = domStorageEnabled;
    }

    public boolean isUseWideViewPort() {
        return useWideViewPort;
    }

    public void setUseWideViewPort(boolean useWideViewPort) {
        this.useWideViewPort = useWideViewPort;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public int getCacheMode() {
        return cacheMode;
    }

    public void setCacheMode(int cacheMode) {
        this.cacheMode = cacheMode;
    }

    public boolean isDatabaseEnable() {
        return databaseEnable;
    }

    public void setDatabaseEnable(boolean databaseEnable) {
        this.databaseEnable = databaseEnable;
    }

}
