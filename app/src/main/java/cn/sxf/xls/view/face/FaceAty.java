package cn.sxf.xls.view.face;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.view.View.OnClickListener;
import android.widget.TextView;
import org.json.JSONObject;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.tencent.bugly.crashreport.CrashReport;
import cn.cloudwalk.FaceInterface;
import cn.cloudwalk.libproject.BestFaceActivity;
import cn.cloudwalk.libproject.Bulider;
import cn.cloudwalk.libproject.LiveStartActivity;
import cn.cloudwalk.libproject.callback.DefineRecognizeCallBack;
import cn.cloudwalk.libproject.callback.ResultCallBack;
import cn.cloudwalk.libproject.net.HttpManager;
import cn.cloudwalk.libproject.util.Base64Util;
import cn.cloudwalk.libproject.util.FileUtil;
import cn.cloudwalk.libproject.util.PreferencesUtils;
import cn.sxf.xls.R;
import cn.sxf.xls.view.BaseAty;

/**
 * 人脸识别
 * @author flh
 */

public class FaceAty extends BaseAty implements OnClickListener {

    TextView mTv_live, mTv_compare, mTv_identify, mTv_ocr, mTv_attribute, mTv_info, mTv_yuncong,
            mTv_set, mTv_copyright;

    public String publicFilePath;
    SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
    // 活体配置 默认值
    public static int liveCount = 3;
    public static float yuz = 0.7f;

    // 云之眼比对服务器配置 默认值
    public static String faceserver = "http://120.76.76.125:8784/ibis", faceappid = "user", faceappser
            = "12345";

    public static int liveLevel = FaceInterface.LevelType.LEVEL_STANDARD;

    public static String licence = "MDMxNzEwbm9kZXZpY2Vjd2F1dGhvcml6Zf/n5Ofj5ufmv+Pn4OXn5uX/5ufn4" +
            "+Dm4JHl5ubr5ebmouvl5ubr5ebE5uvl5ubr5cjm5uvl5ubm7+fm5+bk";

    // TODO 用于测试多次做活体
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.face_main);
        initView();
        initBugLy();
        init();
        // 创建cloudwalk文件夹
        publicFilePath = new StringBuilder(Environment.getExternalStorageDirectory()
                .getAbsolutePath())
                .append(File.separator).append("cloudwalk").append(File.separator).append(sdf
                        .format(new Date()))
                .toString();
        FileUtil.mkDir(publicFilePath);
        super.onCreate(savedInstanceState);
    }

    private void initView() {
//        mTv_copyright = (TextView) findViewById(R.id.tv_copyright);

//        try {
//            String text = getResources().getString(R.string.copyright);
//            String versionName = this.getPackageManager().getPackageInfo(getPackageName(), 0)
//					.versionName;
//            mTv_copyright.setText(text + versionName);
//        } catch (NameNotFoundException e) {
//
//            e.printStackTrace();
//
//        }

        mTv_live = (TextView) findViewById(R.id.tv_live);
        mTv_live.setOnClickListener(this);

        mTv_compare = (TextView) findViewById(R.id.tv_compare);
        mTv_compare.setOnClickListener(this);

        mTv_set = (TextView) findViewById(R.id.tv_set);
        mTv_set.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 获取 活体配置 授权服务器配置 云之眼比对服务器配置
        yuz = PreferencesUtils.getFloat(this, SetAty.PREF_YUZ, yuz);

        faceserver = PreferencesUtils.getString(this, SetAty.PREF_FACESERVER, faceserver);

        Bulider.licence = licence;
    }

    public void startCls(Class cls) {
        startActivity(new Intent(this, cls));
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_live:
                if (liveCount > 0) {
                    startLive();
                } else {
                    startBestFace();
                }

                break;
            case R.id.tv_set:
                startCls(SetAty.class);

                break;
            case R.id.tv_compare:
                startCls(FaceCompareAty.class);

                break;
        }

    }

    private void startBestFace() {

        final Bulider bulider = new Bulider();
        bulider.setLicence(licence).setFaceVerify(new DefineRecognizeCallBack() {// 启用人证比对功能
            @Override
            public void OnDefineFaceVerifyResult(byte[] bestface) {
                if (bestface == null || bestface.length == 0)
                    bulider.setFaceResult(FaceAty.this, Bulider.FACE_VERFY_FAIL, 0f, "", "");
                // 自定义调用人脸比对服务器,进行人脸对比
                String imgBase64 = Base64Util.encode(bestface);
                HttpManager.cwFaceComper(FaceAty.faceserver, FaceAty.faceappid,
                        FaceAty.faceappser,
                        imgBase64, imgBase64, new HttpManager.DataCallBack() {

                            @Override
                            public void requestSucess(JSONObject result) {

                                double score = result.optDouble("score");
                                if (FaceAty.yuz <= score) {
                                    bulider.setFaceResult(FaceAty.this, Bulider
                                            .FACE_VERFY_PASS, score, "", "");
                                } else {
                                    bulider.setFaceResult(FaceAty.this, Bulider
                                            .FACE_VERFY_FAIL, score, "", "");
                                }

                            }

                            @Override
                            public void requestFailure(String errorMsg) {
                                bulider.setFaceResult(FaceAty.this, Bulider
                                        .FACE_VERFY_NETFAIL, 0f, "", "");
                            }

                        });

            }
        }).setBestFaceDelay(3000).setResultCallBack(resultCallBack).isResultPage(true)//
                .startActivity(FaceAty.this, BestFaceActivity.class);
    }

    private void startLive() {

        ArrayList<Integer> liveList = new ArrayList<Integer>();
        liveList.add(FaceInterface.LivessType.LIVESS_MOUTH);
        liveList.add(FaceInterface.LivessType.LIVESS_HEAD_UP);
        liveList.add(FaceInterface.LivessType.LIVESS_HEAD_DOWN);
        liveList.add(FaceInterface.LivessType.LIVESS_HEAD_LEFT);
        liveList.add(FaceInterface.LivessType.LIVESS_HEAD_RIGHT);
        liveList.add(FaceInterface.LivessType.LIVESS_EYE);
        final Bulider bulider = new Bulider();
        bulider.setLicence(licence)
                // .setFaceVerify(new DefineRecognizeCallBack() {// 启用人证比对功能
                // @Override
                // public void OnDefineFaceVerifyResult(byte[] bestface) {
                // // 自定义调用人脸比对服务器,进行人脸对比
                // String imgBase64 = Base64Util.encode(bestface);
                //
                // HttpManager.cwFaceComper(MainActivity.faceserver,
                // MainActivity.faceappid, MainActivity.faceappser,
                // imgBase64, // 证件照图片
                // imgBase64, // 最佳人脸
                // new HttpManager.DataCallBack() {
                //
                // @Override
                // public void requestSucess(JSONObject result) {
                // double score = result.optDouble("score");
                // if (score >= yuz) {
                // bulider.setFaceResult(MainActivity.this,
                // Bulider.FACE_VERFY_PASS, score, "", "");
                // } else {
                // bulider.setFaceResult(MainActivity.this,
                // Bulider.FACE_VERFY_FAIL, score, "", "");
                // }
                //
                // }
                //
                // @Override
                // public void requestFailure(String errorMsg) {
                // bulider.setFaceResult(MainActivity.this,
                // Bulider.FACE_VERFY_NETFAIL, 0f, "", "");
                // }
                //
                // });
                //
                // }
                // })

                .isResultPage(true)//
                .setLives(liveList, liveCount, true, true, liveLevel).setResultCallBack
                (resultCallBack)
                .startActivity(FaceAty.this, LiveStartActivity.class);
    }

    ResultCallBack resultCallBack = new ResultCallBack() {
        @Override
        public void result(boolean isLivePass, boolean isVerfyPass, String faceSessionId, double
                face_score,
                           int resultType, byte[] bestFace, HashMap<Integer, byte[]> liveDatas) {

            count++;
            // 存储最佳人脸图片
            if (bestFace != null && bestFace.length > 0) {
                FileUtil.writeByteArrayToFile(bestFace, publicFilePath + "/" + count + "bestface" +
                        ".jpg");
            }
            // 存储活体证据图片
            if (liveDatas != null && liveDatas.size() > 0) {
                Iterator iter = liveDatas.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<Integer, byte[]> entry = (Map.Entry<Integer, byte[]>) iter.next();
                    int key = entry.getKey();
                    byte[] val = entry.getValue();
                    String saveName = null;
                    switch (key) {
                        case FaceInterface.CW_LivenessCode.CW_FACE_LIVENESS_HEADLEFT:
                            saveName = "/" + count + "headleft.jpg";
                            break;
                        case FaceInterface.CW_LivenessCode.CW_FACE_LIVENESS_HEADRIGHT://
                            saveName = "/" + count + "headright.jpg";
                            break;

                        case FaceInterface.CW_LivenessCode.CW_FACE_LIVENESS_HEADPITCH://
                            saveName = "/" + count + "headup.jpg";
                            break;
                        case FaceInterface.CW_LivenessCode.CW_FACE_LIVENESS_HEADDOWN://
                            saveName = "/" + count + "headdown.jpg";
                            break;
                        case FaceInterface.CW_LivenessCode.CW_FACE_LIVENESS_OPENMOUTH://
                            saveName = "/" + count + "mouth.jpg";
                            break;
                        case FaceInterface.CW_LivenessCode.CW_FACE_LIVENESS_BLINK://
                            saveName = "/" + count + "eye.jpg";
                            break;
                    }
                    FileUtil.writeByteArrayToFile(val, publicFilePath + saveName);

                }
            }

        }
    };

    private String getInfo() {

        String mtype = android.os.Build.MODEL;// MI 4W
        String mtyb = android.os.Build.BRAND;// Xiaomi

        return mtype + mtyb;
    }

    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);

            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "unknown";
        }
    }

    /**
     * initBugLy:腾讯bugly bug监控 <br/>
     *
     * @author:284891377 Date: 2016-5-18 上午11:26:03
     * @since JDK 1.7
     */
    private void initBugLy() {
        Context appContext = this.getApplicationContext();
        String appId = "7448f71775";
        boolean isDebug = true;

        CrashReport.UserStrategy strategy = new
                CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppChannel(getInfo());
        strategy.setAppVersion(getVersion());
        strategy.setAppReportDelay(5000);

        CrashReport.initCrashReport(appContext, appId, isDebug, strategy);

    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void dynamicReqPermission() {}

    @Override
    public void isVisibilityTitleArea() {
        initTitle("人脸识别示例", true, false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
