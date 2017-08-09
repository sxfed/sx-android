package cn.sxf.xls.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import cn.sxf.xls.R;


/**
 * 加载中Dialog
 * 
 */
public class LoadingDialog extends AlertDialog {
	
	private static LoadingDialog instanse;
    private TextView tips_loading_msg;
    private String message = null;

    private LoadingDialog(Context context, String message) {
        super(context);
        this.message = message;
        this.setCancelable(false);
    }

    public static LoadingDialog getInstanse(Context context,String msg){
    	if(instanse == null){
    		instanse = new LoadingDialog(context, msg);
    	}
    	return instanse;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.view_tips_loading);
        tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
        tips_loading_msg.setText(this.message);
    }

    public void setText(String message) {
        this.message = message;
        tips_loading_msg.setText(this.message);
    }

    public void setText(int resId) {
        setText(getContext().getResources().getString(resId));
    }

}
