package cn.sxf.xls.react.view;

import android.os.Bundle;

import cn.sxf.xls.react.modules.CommPackage;

/**
 * Created by fenglonghui on 2017/6/13.
 * test to diffrent page
 */

public class Main2Aty extends BaseModuleAty {

    public CommPackage commPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mReactRootView);

    }


    @Override
    public void configReactRootViewParams() {
        commPackage = new CommPackage();
        configReactRootView("index.android.bundle","index.android",commPackage,"FirstView");
    }

//    @Override
//    public void passedReactParams(CommModule mModule, ReactContext rctContext) {
//        WritableMap params = Arguments.createMap();
//        params.putString("name","小明他爹");
//        params.putBoolean("sex",true);
//        params.putInt("age",10);
//        Log.i("ReactContext",rctContext + "");
//        rctContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//                .emit("SendEvent", params);
//    }
}
