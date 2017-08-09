package cn.sxf.xls.react.modules;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by fenglonghui on 2017/6/13.
 * 注册模块
 */

public class CommPackage implements ReactPackage {

//    public CommModule mModule;

    private String moduleName = null;

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public CommPackage() {
        super();
    }

    public CommPackage(String moduleName) {
        this();
        setModuleName(moduleName);
    }


    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        CommModule mModule = new CommModule(reactContext);
        mModule.setModuleName(moduleName);
        modules.add(mModule);
        return modules;
    }

    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

}
