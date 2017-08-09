package cn.sxf.xls.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import cn.sxf.xls.R;
import cn.sxf.xls.global.Constant;
import cn.sxf.xls.test.Test;
import cn.sxf.xls.view.custom.ViewFactory;

/**
 * 动态添加RadioButton
 *
 * @author flh
 *         setContentView(R.layout.radio_container);
 */
public class NavigatorAty extends AppCompatActivity {

    private RadioGroup radioGroup;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radio_container);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        ViewFactory.getInstanse().createNavigatorTab(this, radioGroup, Constant.tabs);
    }
}
