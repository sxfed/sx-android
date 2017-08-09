package cn.sxf.xls.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.sxf.xls.R;
import cn.sxf.xls.tool.XlsApiTools;

/**
 * Created by fenglonghui on 2017/6/5.
 * 商户
 */

public class ClassifyFragment extends BaseFragment implements View.OnClickListener{

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.classify, null);
        init(view);
        return view;
    }

    @Override
    public void init(View view){
        super.init(view);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void isVisibilityTitleArea() {
        initTitle(XlsApiTools.getResString(getActivity(),R.string.title_leader), true, false);
    }
}
