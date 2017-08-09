package cn.sxf.xls.view.custom;

import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.sxf.xls.tool.CycleViewPagerHandler;

/**
 * @author flh
 */

public class CycleImageView {

    // 存放轮播图片容器
    private List<ImageView> imageViews = new ArrayList<ImageView>();
    public Context con;
    private BaseViewPager viewPager;
    // 默认轮播时间 4s
    private int time = Integer.parseInt("4000");
    // 轮播当前位置
    private int currentPosition = 0;
    // 滚动框是否滚动着
    private boolean isScrolling = false;
    // 是否循环
    private boolean isCycle = false;
    // 是否轮播
    private boolean isWheel = false;
    // 手指松开、页面不滚动时间，防止手机松开后短时间进行切换
    private long releaseTime = 0;
    // 转动标志
    private int WHEEL = 100;
    // 等待标志
    protected int WHEEL_WAIT;
    private CycleViewPagerHandler handler;
    /** 指示器 **/
    private LinearLayout indicatorLayout;

    {
        WHEEL_WAIT = 101;
    }

    public void setCon(Context con) {
        this.con = con;
    }

    public boolean isCycle() {
        return isCycle;
    }

    public void setCycle(boolean isCycle) {
        this.isCycle = isCycle;
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
     * 是否处于轮播状态
     * @return
     */
    public boolean isWheel() {
        return isWheel;
    }

    /**
     * set looper duration time. 默认4000ms
     * @param time 毫秒为单位
     */
    public void setTime(int time) {
        this.time = time;
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

    public void initCycleViewPager(){

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
                            // looper end to first adver
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

}
