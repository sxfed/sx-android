package cn.sxf.xls.view.custom;

import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.sxf.xls.R;
import cn.sxf.xls.adapter.CycleViewPagerAdapter;
import cn.sxf.xls.global.Constant;
import cn.sxf.xls.tool.CycleViewPagerHandler;

/**
 * Created by fenglonghui on 2017/7/5.
 */

public class CycleViewPager implements ViewPager.OnPageChangeListener{
    public Context con;
    // 指示器
    private ImageView[] indicators;
    private LinearLayout indicatorLayout;
    private BaseViewPager viewPager;
    private CycleViewPagerHandler handler;
    private List<ImageView> imageViews = new ArrayList<ImageView>();
    // 转动标志
    private int WHEEL = 100;
    // 滚动框是否滚动着
    private boolean isScrolling = false;
    // 轮播当前位置
    private int currentPosition = 0;
    // 手指松开、页面不滚动时间，防止手机松开后短时间进行切换
    private long releaseTime = 0;
    // 默认轮播时间 4s
    private int time = Integer.parseInt(Constant.DEFAULT_CYCLE_TIME);
    // 等待标志
    protected int WHEEL_WAIT;
    // 是否轮播
    private boolean isWheel = false;
    // 是否循环
    private boolean isCycle = false;
    private CycleViewPagerAdapter cycleAdapter;
    private List<ImageView> temViews;

    {
        WHEEL_WAIT = 101;
    }

    public CycleViewPager(Context con) {
        this.con = con;
    }



    public void initCycleViewPage(BaseViewPager viewPager, LinearLayout indicatorLayout,List<ImageView> temViews){
        this.viewPager = viewPager;
        this.indicatorLayout = indicatorLayout;
        this.temViews = temViews;
        startViewPager();
    }

    /**
     * 初始化主页广告轮番图
     */
    private void startViewPager() {
        handler = new CycleViewPagerHandler(con) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == WHEEL && imageViews.size() != 0) {
                    if (!isScrolling) {
                        int max = imageViews.size() + 1;
                        int position = (currentPosition + 1) % imageViews.size();
                        viewPager.setCurrentItem(position, true);
                        if (position == max) {
                            // 循环到结尾后
                            viewPager.setCurrentItem(1, false);
                        }
                    }
                    releaseTime = System.currentTimeMillis();
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable, time);
                    return;
                }
                if (msg.what == WHEEL_WAIT && imageViews.size() != 0) {
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable, time);
                }
            }
        };
        setCycle(true);
        setWheel(true);
        // 循环间隔时间
        setTime(this.time);
        //set guide icon show position defalut right
        setIndicatorCenter();
        setData(0);
    }

    /**
     * init viewpager
     * @param showPosition 默认显示位置
     */
    public void setData(int showPosition) {
        this.imageViews.clear();
        if (temViews.size() == 0) {
            return;
        }

        for (ImageView item : temViews) {
            this.imageViews.add(item);
        }
        int ivSize = temViews.size();
        // 设置指示器
        indicators = new ImageView[ivSize];
        if (isCycle)
            indicators = new ImageView[ivSize - 2];
        indicatorLayout.removeAllViews();
        for (int i = 0; i < indicators.length; i++) {
            View view = LayoutInflater.from(con).inflate(
                    R.layout.view_cycle_viewpager_indicator, null);
            indicators[i] = (ImageView) view.findViewById(R.id.image_indicator);
            indicatorLayout.addView(view);
        }

        cycleAdapter = new CycleViewPagerAdapter();
        cycleAdapter.setImageViews(imageViews);
        // 指示器默认指向第一项，
        setIndicator(0);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(cycleAdapter);
        if (showPosition < 0 || showPosition >= temViews.size())
            showPosition = 0;
        if (isCycle) {
            showPosition = showPosition + 1;
        }
        viewPager.setCurrentItem(showPosition);

    }

    /**
     * set the guider
     * @param selectedPosition 默认指示器位置
     */
    private void setIndicator(int selectedPosition) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i]
                    .setBackgroundResource(R.drawable.dot_sel);
        }
        if (indicators.length > selectedPosition)
            indicators[selectedPosition]
                    .setBackgroundResource(R.drawable.dot);
    }

    /**
     * 设置指示器居中，默认在右方
     */
    public void setIndicatorCenter() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        indicatorLayout.setLayoutParams(params);
    }

    /**
     * 轮播默认时间间隔4000ms
     * @param time 毫秒为单位
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * 设置是否轮播，默认不轮播,轮播一定是循环的
     * @param isWheel
     */
    public void setWheel(boolean isWheel) {
        this.isWheel = isWheel;
        isCycle = true;
        if (isWheel) {
            handler.postDelayed(runnable, time);
        }
    }

    /**
     * 是否循环，默认不开启，开启前，请将views的最前面与最后面各加入一个视图，用于循环
     * @param isCycle 是否循环
     */
    public void setCycle(boolean isCycle) {
        this.isCycle = isCycle;
    }

    final Runnable runnable = new Runnable() {

        @Override
        public void run() {
            Activity act = (Activity)con;
            if (act != null && !act.isFinishing()
                    && isWheel) {
                long now = System.currentTimeMillis();
                // 检测上一次滑动时间与本次之间是否有触击(手滑动)操作，有的话等待下次轮播
                if (now - releaseTime > time - 500) {
                    handler.sendEmptyMessage(WHEEL);
                } else {
                    handler.sendEmptyMessage(WHEEL_WAIT);
                }
            }
        }
    };

    @Override
    public void onPageScrollStateChanged(int arg0) {
        if (arg0 == 1) { // viewPager在滚动
            isScrolling = true;
            return;
        } else if (arg0 == 0) { // viewPager滚动结束
            releaseTime = System.currentTimeMillis();
            viewPager.setCurrentItem(currentPosition, false);
        }
        isScrolling = false;
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int arg0) {
        int max = imageViews.size() - 1;
        int position = arg0;
        currentPosition = arg0;
        if (isCycle) {
            if (arg0 == 0) {
                currentPosition = max - 1;
            } else if (arg0 == max) {
                currentPosition = 1;
            }
            position = currentPosition - 1;
        }
        setIndicator(position);
    }

}
