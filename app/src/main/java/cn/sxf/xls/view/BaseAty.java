package cn.sxf.xls.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sxf.xls.R;
import cn.sxf.xls.global.Constant;
import cn.sxf.xls.tool.DevOs;
import cn.sxf.xls.tool.DialogueTools;
import cn.sxf.xls.tool.ViewTools;

/**
 * 基类
 * Created by fenglonghui on 2017/6/4.
 *
 */

public abstract class BaseAty extends AppCompatActivity {
    public WebView mWebView;
    public TextView title;
    public LinearLayout backup;
    public LinearLayout scan;
    public ImageView titleRightImage;
    public TextView titleRightTv;
    public Context con;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.con = this;
        DevOs.setOverlayPermission(this);
        dynamicReqPermission();
    }

    /**
     * 获得应用在设备中的权限
     */
    public abstract  void  dynamicReqPermission();


    /**
     * 动态请求权限
     */
    public void reqGranted(String permission){

        if(ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{permission}, Constant.PERMISSION_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constant.PERMISSION_REQUEST) {

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("Call Phone", "权限被允许");
            } else {
                Log.i("Call Phone", "权限被拒绝");
            }
        }
    }

    /**
     * 初始化标题栏
     */
    public void init(){
        title = getViewTag(R.id.disp);
        backup = getViewTag(R.id.backup);
        scan = getViewTag(R.id.scan);
        titleRightImage = getViewTag(R.id.titleRightImage);
        titleRightTv = getViewTag(R.id.titleRightTv);
        isVisibilityTitleArea();
    }

    public abstract void isVisibilityTitleArea();

    /**
     * 标题栏'回退'键
     * @param view
     */
    public void isUp(View view){
        if(mWebView !=null){
            boolean canGoBack = mWebView.canGoBack();
            if(canGoBack){
                mWebView.goBack();
            }else{
                finish();
            }

        }else{
            finish();
        }
    }

    /**
     * 标题栏右侧'关闭'键
     * @param view
     */
    public void isScan(View view){
        finish();
    }


    /**
     * 标题栏左右按键是否显示
     * @param con
     * @param left
     * @param right
     */
    protected void initTitle(String con,boolean left,boolean right){
        title.setText(con);
        if(left){
            ViewTools.displayView(backup);
        }else{
            ViewTools.hideView(backup);
        }
        if(right){
            ViewTools.displayView(scan);
        }else{
            ViewTools.hideView(scan);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getViewTag(int id) {
        return (T) super.findViewById(id);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DevOs.SDK_INT_GRANTED_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= DevOs.BUILD_VERSION_INT_M) {
                if (!Settings.canDrawOverlays(this)) {
                    DialogueTools.ToastShortDialogue(this,R.string.permission_granted_warning);
                    DevOs.setOverlayPermission(this);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
