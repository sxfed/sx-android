package cn.sxf.xls.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.sxf.xls.R;

/**
 * Created by fenglonghui on 2017/6/5.
 */

public abstract class BaseFragment extends Fragment {

    public Context con;
    public TextView title;
    public LinearLayout backup;
    public LinearLayout scan;
    public ImageView titleRightImage;
    public TextView titleRightTv;



    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        con = (Context)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    /**
     * 初始化标题栏
     */
    public void init(View view){
        title = getViewTag(view, R.id.disp);
        backup = getViewTag(view,R.id.backup);
        scan = getViewTag(view,R.id.scan);
        titleRightImage = getViewTag(view,R.id.titleRightImage);
        titleRightTv = getViewTag(view,R.id.titleRightTv);
        isVisibilityTitleArea();
    }


    protected void initTitle(String con,boolean up,boolean next){
        title.setText(con);
        if(up){
            if(backup.getVisibility() == View.INVISIBLE){
                backup.setVisibility(View.VISIBLE);
            }
        }else{
            if(backup.getVisibility() == View.VISIBLE){
                backup.setVisibility(View.INVISIBLE);
            }
        }

        if(next){
            if(scan.getVisibility() == View.INVISIBLE){
                scan.setVisibility(View.VISIBLE);
            }
        }else{
            if(scan.getVisibility() == View.VISIBLE){
                scan.setVisibility(View.INVISIBLE);
            }
        }
    }

    public abstract void isVisibilityTitleArea();

    @SuppressWarnings("unchecked")
    public <T extends View> T getViewTag(View v,int id) {
        return (T) v.findViewById(id);
    }



}
