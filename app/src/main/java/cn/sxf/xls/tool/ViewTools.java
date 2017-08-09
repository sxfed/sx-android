package cn.sxf.xls.tool;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.sxf.xls.entity.AdvertisInfo;
import cn.sxf.xls.fragment.HomeFragment;
import cn.sxf.xls.view.custom.ViewFactory;

import static cn.sxf.xls.global.MyApplication.infos;

/**
 * 视图工具类
 * Created by fenglonghui on 2017/7/3.
 */

public class ViewTools {

    /**
     * 隐藏视图
     * @param view
     */
    public static void hideView(View view){
        if(view != null && view.getVisibility() == View.VISIBLE){
            view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 视图及其空间消失
     * @param view
     */
    public static void goneView(View view){
        if( view != null && view.getVisibility() == View.VISIBLE){
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 显示视图
     * @param view
     */
    public static void displayView(View view){
        if( view != null && view.getVisibility() == View.INVISIBLE){
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 根据url转换ImageView
     */
    public static List<ImageView> urlToImageView(Context con, ArrayList<String> imgUrls){
        List<ImageView> views = new ArrayList<ImageView>();
        if(views.size() > 0)
            views.clear();
        if(imgUrls.size() != 0){
            views.add(ViewFactory.getImageView(con, imgUrls.get(imgUrls.size() - 1)));
            for (int i = 0; i < imgUrls.size(); i++) {
                views.add(ViewFactory.getImageView(con, imgUrls.get(i)));
            }
            views.add(ViewFactory.getImageView(con, imgUrls.get(0)));
        }
        return views;
    }

}
