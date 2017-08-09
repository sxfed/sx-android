package cn.sxf.xls.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.kernal.passportreader.sdk.IdCardMainActivity;

/**
 * 卡片识别
 * @author flh
 */

public class OcrCartdAty extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=new Intent(this,IdCardMainActivity.class);
        this.finish();
        startActivity(intent);
    }
}
