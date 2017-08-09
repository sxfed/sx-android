package cn.sxf.xls.react.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;

import cn.sxf.xls.BuildConfig;

/**
 * Created by fenglonghui on 2017/6/12.
 */

public abstract class BaseModuleAty extends Activity implements DefaultHardwareBackBtnHandler {
    public ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configReactRootViewParams();
    }

    public abstract void configReactRootViewParams();

    /**
     * create ReactrootView
     * @param bundleAssetName       bundle名称
     * @param bundleJs              对应js文件名
     * @param reactPackage          注册模块管理类
     * @param RegistryComponent     对应js中注册的组件名
     */
    protected final void configReactRootView(String bundleAssetName, String bundleJs, ReactPackage reactPackage, String RegistryComponent){
        createReactRootView(bundleAssetName, bundleJs, reactPackage, RegistryComponent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }


    private void createReactRootView(String bundleAssetName, String bundleJs, ReactPackage reactPackage, String RegistryComponent){
        mReactRootView = new ReactRootView(this);
        iniRecInsManaParam(bundleAssetName, bundleJs, reactPackage);
        mReactRootView.startReactApplication(mReactInstanceManager, RegistryComponent, null);
    }



    private void iniRecInsManaParam(String bundleAssetName, String bundleJs, ReactPackage reactPackage){
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setBundleAssetName(bundleAssetName)
                .setJSMainModuleName(bundleJs)
                .addPackage(new MainReactPackage())
                .addPackage(reactPackage)
                .setUseDeveloperSupport(BuildConfig.DEBUG?BuildConfig.DEBUG:true)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();
    }



    @Override
    protected void onPause() {
        super.onPause();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostPause(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostResume(this, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostDestroy(this);
        }
    }

    @Override
    public void onBackPressed() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        //当我们点击菜单的时候打开发者菜单，一个弹窗（此处需要悬浮窗权限才能显示）
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
