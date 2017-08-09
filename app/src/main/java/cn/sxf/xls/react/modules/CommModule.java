package cn.sxf.xls.react.modules;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.HashMap;
import java.util.Map;


/**
 * 提供js调用的native方法
 * Created by fenglonghui on 2017/6/13.
 *
 */

public class CommModule extends ReactContextBaseJavaModule {

    public static final String MODULE_NAME = "commModule";
    public static final String EVENT_NAME = "EventName";
    public static final String DURATION_SHORT_KEY = "SHORT";
    public static final String DURATION_LONG_KEY = "LONG";

    private ReactApplicationContext mContext = null;
    private String moduleName;


    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }


    public CommModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }


    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(DURATION_SHORT_KEY, Toast.LENGTH_SHORT);
        constants.put(DURATION_LONG_KEY, Toast.LENGTH_LONG);
        return constants;
    }

    @Override
    public String getName() {
        if(moduleName == null){
            return MODULE_NAME;
        }else{
            return moduleName;
        }

    }

    /**
     * Android 弹出提示信息
     * @param message
     * @param duration
     */
    @ReactMethod
    public void showToast(String message, int duration) {
        Toast.makeText(mContext, message, duration).show();
    }

    /**
     * 关闭当前的Activity
     */
    @ReactMethod
    public void closeActivity() {
        getCurrentActivity().finish();
    }


    /**
     * Callback通信
     * @param successBack
     * @param erroBack
     */
    @ReactMethod
    public void jsActivity(Callback successBack, Callback erroBack) {

        try {
            Activity currentActivity = getCurrentActivity();
            int result = currentActivity.getIntent().getIntExtra("data", 0);
            successBack.invoke(result);
        } catch (Exception e) {
            erroBack.invoke(e.getMessage());
        }

    }


    /**
     * RN调用Native的方法，调用拨打电话界面
     * @param phone
     */
    @ReactMethod
    public void rnCallNative(String phone){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }


    @ReactMethod
    public void nativeCallMethod(){
        WritableMap params = Arguments.createMap();
        params.putString("name","小明");
        params.putBoolean("sex",true);
        params.putInt("age",10);
        mContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(EVENT_NAME, params);
    }


}
