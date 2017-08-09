package cn.sxf.xls.react.view;

import android.os.Bundle;
import android.widget.LinearLayout;

import cn.sxf.xls.R;
import cn.sxf.xls.react.modules.CommPackage;

/**
 * 原生和RN混合显示
 * @author flh
 */
public class MyReactAty extends BaseModuleAty {

    private LinearLayout mReactLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_react);
        mReactLayout = (LinearLayout) findViewById(R.id.layout);
        mReactLayout.addView(mReactRootView);
    }

    @Override
    public void configReactRootViewParams() {
        // String bundleAssetName, String bundleJs, ReactPackage reactPackage, String RegistryComponent
        // configReactRootView("index.android.bundle","index.android",new AnExampleReactPackage(),"FirstView");
        configReactRootView("index.android.bundle","index.android",new CommPackage(),"FirstView");
    }


}