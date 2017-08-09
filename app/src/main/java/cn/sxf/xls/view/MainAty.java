package cn.sxf.xls.view;


import android.Manifest;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import org.json.JSONException;

import java.util.ArrayList;
import cn.sxf.xls.R;
import cn.sxf.xls.entity.UnitInfo;
import cn.sxf.xls.fragment.FragmentUtil;
import cn.sxf.xls.fragment.HomeFragment;
import cn.sxf.xls.global.Constant;
import cn.sxf.xls.tool.DialogueTools;
import cn.sxf.xls.tool.ResultService;
import cn.sxf.xls.tool.OkManager;
import cn.sxf.xls.tool.ViewTools;
import cn.sxf.xls.view.custom.ViewFactory;


/**
 * 应用程序入口
 * @author flh
 */
public class MainAty extends BaseAty implements OnCheckedChangeListener{
    private RadioGroup container;
    private long mExitTime;
    // 九宫格数据
    public ArrayList<UnitInfo> grids = new ArrayList<UnitInfo>();
    // 轮播图数据
    public ArrayList<String> cycleImages = new ArrayList<String>();
    public FragmentUtil fragUtil;
    private ResultService rServece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initConfigParam();
        initView();
    }

    /**
     * 初始化参数
     */
    public void initConfigParam(){
        rServece = new ResultService();
        // 初始化ImageLoader
        rServece.initImageLoader(this);
        fragUtil = FragmentUtil.getInstance();
    }

    public void initView(){
        container = getViewTag(R.id.main_radiogroup);
        container.setOnCheckedChangeListener(this);
        ViewFactory.getInstanse().createNavigatorTab(this, container, Constant.tabs);
        getCycles(Constant.CYCLES_URL);
        getGrids(Constant.GRIDS_URL);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        fragUtil.setManager(getSupportFragmentManager());
        fragUtil.switchFragmentView(checkedId);
    }

    /**
     * 网络请求，获取九宫格数据
     * @param url
     */
    public void getGrids(String url){
        rServece.reqResultGet(url, new OkManager.OnResultCallback<String>() {
            @Override
            public void onFailure(String error) {
                DialogueTools.ToastDialogue(MainAty.this, error, 1);
            }

            @Override
            public void onResponseSuccess(String result) {
                try {
                    grids = rServece.getJSONArrayList(result, UnitInfo.class);
                    setGrids(grids);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponseFailure(int code, String msg) {
                DialogueTools.ToastDialogue(MainAty.this, msg, 1);
            }
        });
    }

    /**
     * 网络请求，获取轮播图数据
     * @param url
     */
    public void getCycles(String url){
        rServece.reqResultGet(url, new OkManager.OnResultCallback<String>() {
            @Override
            public void onFailure(String error) {
                DialogueTools.ToastDialogue(MainAty.this, error, 1);
            }

            @Override
            public void onResponseSuccess(String result) {
                try {
                    cycleImages = rServece.getJSONArrayList(result);
                    setCycleImage(cycleImages);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponseFailure(int code, String msg) {
                DialogueTools.ToastDialogue(MainAty.this, msg, 1);
            }
        });
    }

    /**
     * 填充轮播图数据
     * @param cycleImageUrls
     */
    public void setCycleImage(ArrayList<String> cycleImageUrls){
        HomeFragment mFrag = fragUtil.getF1();
        if(mFrag != null){
            mFrag.configCycleViewPager(ViewTools.urlToImageView(this,cycleImageUrls));
            mFrag.updateCycleImages();
        }
    }

    /**
     * 填充九宫格数据
     * @param grids
     */
    public void setGrids(ArrayList<UnitInfo> grids){
        HomeFragment mFrag = fragUtil.getF1();
        if(mFrag != null){
            mFrag.configGridsViewPager(grids);
            mFrag.updateGrids();
        }
    }

    /**
     * url转换ImageView，供HomeFragment调用
     */
    public void checkDataIsNull(HomeFragment homeFragment){
        if(homeFragment.getTemViews() == null){
            homeFragment.configCycleViewPager(ViewTools.urlToImageView(this,cycleImages));
        }
        if(homeFragment.getGrids() == null){
            homeFragment.configGridsViewPager(grids);
        }
    }

    @Override
    public void isVisibilityTitleArea() {}

    /**
     * 获取手机拨打电话的权限
     */
    @Override
    public void dynamicReqPermission() {
        reqGranted(Manifest.permission.CALL_PHONE);
    }

    public static void sendEvent(ReactContext reactContext, String eventName, WritableMap paramss){
        reactContext .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, paramss);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                DialogueTools.ToastLongDialogue(this,R.string.exit_message);
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
