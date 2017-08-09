package cn.sxf.xls.tool;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * Created by fenglonghui on 2017/6/4.
 * 系统设备工具类
 */

public class DevOs {
    // android 6.0 以上需要授权否则报错
    public static final int BUILD_VERSION_INT_M = 23;
    // 授权请求码
    public static final int  SDK_INT_GRANTED_REQUEST_CODE = 10;

    /**
     * 获取系统版本号
     * @return
     */
    public static Integer getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }


    /**
     * 设置设备授权，前提是 Android os > 23
     * @param act
     */
    public static void setOverlayPermission(Activity act){
        if (Build.VERSION.SDK_INT >= BUILD_VERSION_INT_M) {
            if (!Settings.canDrawOverlays(act)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + act.getPackageName()));
                act.startActivityForResult(intent,SDK_INT_GRANTED_REQUEST_CODE);
            }
        }
    }


    /**
     * 是否授权
     * @return
     */
    public static Boolean isCurrenAppGranted(Activity act){
        Boolean result = false;
        if (Build.VERSION.SDK_INT >= BUILD_VERSION_INT_M) {
            // 必须授权
            if (Settings.canDrawOverlays(act)) {
                // 已授权
                result = true;
            }else{
                // 未授权
                result = false;
            }
        }else{
            //  不需要授权
            result = false;
        }

        return result;
    }
}
