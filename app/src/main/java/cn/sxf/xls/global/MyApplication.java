package cn.sxf.xls.global;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.facebook.react.BuildConfig;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.sxf.xls.entity.AdvertisInfo;
import cn.sxf.xls.react.modules.CommPackage;


public class MyApplication extends Application implements ReactApplication {
    private static MyApplication instance;
    // 应用上下文
    public static Context appContext;

    /**
     * 轮播图结合
     **/
    public static ArrayList<AdvertisInfo> infos = new ArrayList<AdvertisInfo>();

    public ArrayList<AdvertisInfo> getImages() {
        return infos;
    }

    public void setImages(ArrayList<AdvertisInfo> infos) {
        this.infos = infos;
    }

    /**
     * 轮播时间间隔
     **/
    private String cycleTime;


    public void setCycleTime(String cycleTime) {
        if (TextUtils.isEmpty(cycleTime)) {
            this.cycleTime = Constant.DEFAULT_CYCLE_TIME;
        } else {
            this.cycleTime = cycleTime;
        }
    }

    public String getCycleTime() {
        return this.cycleTime;
    }

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(), new CommPackage());
        }
    };


    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appContext = getApplicationContext();
        /* native exopackage */
        SoLoader.init(this,false);
    }

    /**
     * 获取应用包名
     */
    public String getAppPackageName() {
        return this.getPackageName();
    }

    /**
     * 获取Application实例
     * @return
     */
    public static MyApplication getInstance() {
        return instance;
    }

}
