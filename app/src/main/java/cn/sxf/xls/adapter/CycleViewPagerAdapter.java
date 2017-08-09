package cn.sxf.xls.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * 轮播图适配器
 * @author flh
 */
public class CycleViewPagerAdapter extends PagerAdapter {

    // 存放轮播图片容器
    private List<ImageView> imageViews = new ArrayList<ImageView>();
    // 是否循环
    private boolean isCycle = false;

    public boolean isCycle() {
        return isCycle;
    }

    public void setCycle(boolean isCycle) {
        this.isCycle = isCycle;
    }

    public List<ImageView> getImageViews() {
        return imageViews;
    }

    public void setImageViews(List<ImageView> imageViews) {
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        ImageView v = imageViews.get(position);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCycle) {
                    //jump2AdverUI();
                }
            }
        });
        container.addView(v);
        return v;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
