package cn.sxf.xls.view.custom;


import android.util.SparseArray;
import android.view.View;



/**
 * View工具类（根据id）
 * Created by fenglonghui on 2017/6/6.
 */

public class BaseViewHolder {
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

}
