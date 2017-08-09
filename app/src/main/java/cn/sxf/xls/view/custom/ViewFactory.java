package cn.sxf.xls.view.custom;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.sxf.xls.R;

/**
 * 视图创建工具
 * @author flh
 */
public class ViewFactory {

    private static ViewFactory inStanse;

    private ViewFactory() {
    }

    public static ViewFactory getInstanse(){
        if(inStanse == null){
            inStanse = new ViewFactory();
        }
        return inStanse;
    }

    /**
     * 获取ImageView视图的同时加载显示url
     * @param context
     * @param url
     * @return
     */
    public static ImageView getImageView(Context context, String url) {
        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(
                R.layout.adver_unit, null);
        ImageLoader.getInstance().displayImage(url, imageView);
        return imageView;
    }

    /**
     * 根据图标id创建tab标签
     * @param con
     * @param container
     * @param args
     */
    public void createNavigatorTab(Context con, RadioGroup container, int... args){
        int tabs = args.length;
        int marginSize = mesureRadioButton(tabs);
        for(int index = 0; index < tabs; index++){
            RadioButton rb = new RadioButton(con);
            rb.setId(index);
            rb.setButtonDrawable(null);
            rb.setGravity(Gravity.CENTER);
            rb.setBackgroundResource(args[index]);
            container.addView(rb);
            if(index == 0){
                rb.setChecked(true);
            }
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) rb.getLayoutParams();
            lp.weight = 1.0f;
            lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            lp.width = 0;
            lp.setMargins(marginSize, 0, marginSize, 0);
            rb.setLayoutParams(lp);
        }

    }

    /**
     * 根据文字描述创建标签
     * @param con
     * @param container
     * @param args
     */
    public void createNavigatorTab(Context con, RadioGroup container, String... args){
        int tabs = args.length;
        int marginSize = mesureRadioButton(tabs);
        for(int index = 0; index < tabs; index++){
            RadioButton rb = new RadioButton(con);
            rb.setId(index);
            rb.setButtonDrawable(null);
            rb.setText(args[index]);
//            rb.setCompoundDrawablesWithIntrinsicBounds(null, con.getResources().getDrawable(R.drawable.rb_home_selector), null, null);
            rb.setGravity(Gravity.CENTER);
//            rb.setBackgroundResource(args[index]);
            container.addView(rb);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) rb.getLayoutParams();
            lp.weight = 1.0f;
            lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            lp.width = 0;
            lp.setMargins(marginSize, 0, marginSize, 0);
            rb.setLayoutParams(lp);
        }

    }

    /**
     * 计算标签的间隔
     * @param len
     * @return
     */
    private int mesureRadioButton(int len){
        int margin = 0;
        margin =   ((480 / len) - 120 ) / 2;
        return margin;
    }

}
