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
 * 我的
 * @author flh
 */

public class MyInfoFragment extends BaseFragment {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.con = (Context)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myinfo, null);
        init(view);
        return view;
    }

    @Override
    public void init(View view){
        super.init(view);
    }

    @Override
    public void isVisibilityTitleArea() {
        initTitle(XlsApiTools.getResString(getActivity(),R.string.title_myinfo), true, false);
    }
}
