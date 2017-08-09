package cn.sxf.xls.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.sxf.xls.entity.UnitInfo;
import cn.sxf.xls.react.view.Main2Aty;
import cn.sxf.xls.test.Test;
import cn.sxf.xls.view.NativeComponent;
import cn.sxf.xls.view.MainAty;
import cn.sxf.xls.R;
import cn.sxf.xls.view.OcrCartdAty;
import cn.sxf.xls.view.WebViewAty;
import cn.sxf.xls.adapter.DragBaseAdapter;
import cn.sxf.xls.tool.XlsApiTools;
import cn.sxf.xls.view.custom.ActiveGrideView;
import cn.sxf.xls.view.custom.BaseViewPager;
import cn.sxf.xls.view.custom.CycleViewPager;
import cn.sxf.xls.view.face.FaceAty;


/**
 * 实现导航、轮播图、九宫格功能
 * @author flh
 */
public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    // 轮播图指示器
    private LinearLayout indicatorLayout;
    // 轮播图适配器
    private BaseViewPager viewPager;
    // 九宫格布局
    private ActiveGrideView aGridview;
    // 轮播图数据
    private List<ImageView> temViews;
    // 九宫格数据
    public ArrayList<UnitInfo> grids;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainAty)con).checkDataIsNull(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, null);
        init(view);
        return view;
    }

    @Override
    public void init(View view){
        super.init(view);
        viewPager = (BaseViewPager) view.findViewById(R.id.viewPager);
        indicatorLayout = (LinearLayout) view.findViewById(R.id.layout_viewpager_indicator);
        aGridview = getViewTag(view, R.id.gridview);
        aGridview.setOnItemClickListener(this);
        aGridview.setAdapter(new DragBaseAdapter(con, Test.testGrideData(grids)));
        loadBaner(viewPager,indicatorLayout,temViews);
    }

    /**
     * 更新九宫格界面
     */
    public void updateGrids(){
        aGridview.setAdapter(new DragBaseAdapter(con, Test.testGrideData(grids)));
    }

    /**
     * 更新轮播图界面
     */
    public void updateCycleImages(){
        loadBaner(viewPager,indicatorLayout,temViews);
    }

    /**
     * 加载轮番图
     * @param viewPager
     * @param indicatorLayout
     * @param temViews
     */
    private void loadBaner(BaseViewPager viewPager,LinearLayout indicatorLayout,List<ImageView> temViews) {
        CycleViewPager cyclePager = new CycleViewPager(getActivity());
        cyclePager.initCycleViewPage(viewPager,indicatorLayout,temViews);
    }

    /**
     * 获取轮播图数据
     * @param temViews
     */
    public void configCycleViewPager(List<ImageView> temViews){
        this.temViews=temViews;

    }

    /**
     * 获取九宫格数据
     * @param grids
     */
    public void configGridsViewPager(ArrayList<UnitInfo> grids){
        this.grids=grids;
    }

    public ArrayList<UnitInfo> getGrids(){
        return grids;
    }

    public List<ImageView> getTemViews() {
        return temViews;
    }

    /**
     * 设置标题栏
     */
    @Override
    public void isVisibilityTitleArea() {
        initTitle(XlsApiTools.getResString(getActivity(),R.string.title_home), false, false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0){
            // 原生控件，待补充 ？？？
            startActivity(new Intent(HomeFragment.this.con,NativeComponent.class));
        }if(position == 1){
            // RN控件
            startActivity(new Intent(HomeFragment.this.con,Main2Aty.class));
        }else if(position == 2){
            // WebView控件
            startActivity(new Intent(con,WebViewAty.class));
        }else if(position == 3){
            // 人脸识别
            startActivity(new Intent(con,FaceAty.class));
        }else if(position == 4){
            startActivity(new Intent(con,OcrCartdAty.class));
        }
    }
}
