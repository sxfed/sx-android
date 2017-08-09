/**
 * Project Name:cwFaceForDev3
 * File Name:SetActivity.java
 * Package Name:cn.cloudwalk.dev.mobilebank
 * Date:2016-5-11 9:18:25
 * Copyright @ 2010-2016 Cloudwalk Information Technology Co.Ltd All Rights Reserved.
 */
package cn.sxf.xls.view.face;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import cn.cloudwalk.CloudwalkSDK;
import cn.cloudwalk.libproject.TemplatedActivity;
import cn.cloudwalk.libproject.util.NullUtils;
import cn.cloudwalk.libproject.util.PreferencesUtils;
import cn.cloudwalk.libproject.util.ToasterUtil;
import cn.sxf.xls.R;

/**
 * ClassName: SetActivity <br/>
 * Description: 设置 <br/>
 * date: 2016-5-11 9:18:25 <br/>
 *
 * @author 284891377
 * @since JDK 1.7
 */
public class SetAty extends TemplatedActivity implements OnClickListener {
    //
    public static final String PREF_YUZ = "Pref_yuz";

    public static final String PREF_FACESERVER = "pref_faceserver";

    EditText  et_yuz, et_faceserver, et_version;

    Button bt_save, btn_easy, btn_normal, btn_hard;

    float yuz;

    String faceserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_set);
        setTitle("设置");

        et_version = (EditText) findViewById(R.id.et_version);
        et_yuz = (EditText) findViewById(R.id.et_yuz);

        et_faceserver = (EditText) findViewById(R.id.et_faceserver);

        bt_save = (Button) findViewById(R.id.bt_save);

        bt_save.setOnClickListener(this);

        initData();
    }

    private void initData() {
        //
        yuz = PreferencesUtils.getFloat(SetAty.this, PREF_YUZ, FaceAty.yuz);

        faceserver = PreferencesUtils.getString(SetAty.this, PREF_FACESERVER, FaceAty
				.faceserver);

        et_yuz.setText(yuz + "");

        et_faceserver.setText(faceserver);
        String version = CloudwalkSDK.getInstance(this).cwGetVersionInfo() + "";
        et_version.setText(version);
    }

    private boolean checkEmpty(EditText et) {
        boolean isEmpty = false;
        String str = et.getText().toString().trim();
        if (NullUtils.isEmpty(str)) {
            ToasterUtil.showToast(this, null, "输入不能为空");
            et.requestFocus();
            isEmpty = true;
        }
        return isEmpty;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bt_save:

                if (checkEmpty(et_yuz))
                    return;
                if (checkEmpty(et_faceserver))
                    return;

                yuz = Float.parseFloat(et_yuz.getText().toString().trim());

                faceserver = et_faceserver.getText().toString();

                if (yuz > 1 || yuz < 0) {
                    ToasterUtil.showToast(this, null, "人脸阈值范围为0 - 1");
                    return;
                }

                // 保持值
                PreferencesUtils.putFloat(SetAty.this, PREF_YUZ, yuz);

                PreferencesUtils.putString(SetAty.this, PREF_FACESERVER, faceserver);

                ToasterUtil.showToast(SetAty.this, null, "设置保存成功!");

                break;

            default:
                break;
        }

    }

    @Override
    public void onLeftClick(View v) {
        onBackPressed();

    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
