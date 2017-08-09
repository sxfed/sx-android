package cn.sxf.xls.adapter;

import android.widget.BaseAdapter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.sxf.xls.R;
import cn.sxf.xls.entity.UnitInfo;
import cn.sxf.xls.inter.ActiveAdapter;
import cn.sxf.xls.view.custom.BaseViewHolder;


/**
 * 九宫格适配器
 * Created by fenglonghui on 2017/6/6.
 */
public class DragBaseAdapter extends BaseAdapter implements ActiveAdapter {

    private List<HashMap<UnitInfo, Integer>> list;
    private LayoutInflater mInflater;
    private int mHidePosition = -1;
    private Context mContext;




    public DragBaseAdapter(Context mContext) {
        super();
        this.mContext = mContext;

    }

    public DragBaseAdapter(Context context, List<HashMap<UnitInfo, Integer>> list){
        this(context);
        this.list = list;
        mInflater = LayoutInflater.from(mContext);

    }

    public void reset(List<HashMap<UnitInfo, Integer>> list){
        this.list = list;
    }

    public List<HashMap<UnitInfo, Integer>> get(){
        return this.list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public HashMap<UnitInfo, Integer> getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 由于复用convertView导致某些item消失了，所以这里不复用item，
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(
                R.layout.grid_item, parent, false);

        TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
        ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);

        Map.Entry entry = loopItem(list,position);
        if(entry != null){
            iv.setImageResource((Integer)entry.getValue());
            UnitInfo ui = (UnitInfo)entry.getKey();
            tv.setText(ui.getTitle());
//            if(entry.getKey() instanceof UnitInfo){
//
//            }
        }else{
            iv.setImageResource(R.mipmap.suixingpay);
        }

        //iv.setBackgroundResource(imgs[position]);
        //tv.setText(img_text[position]);

        if(position == mHidePosition){
            convertView.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }


    public Map.Entry loopItem(List<HashMap<UnitInfo, Integer>> list,int position){
        Map.Entry entry = null;
        HashMap<UnitInfo, Integer> loomap = list.get(position);
        Iterator iter = loomap.entrySet().iterator();
        if(iter.hasNext()) {
            entry = (Map.Entry) iter.next();
        }
        return entry;
    }


    @Override
    public void reorderItems(int oldPosition, int newPosition) {
        HashMap<UnitInfo, Integer> temp = list.get(oldPosition);
        if(oldPosition < newPosition){
            for(int i=oldPosition; i<newPosition; i++){
                Collections.swap(list, i, i+1);
            }
        }else if(oldPosition > newPosition){
            for(int i=oldPosition; i>newPosition; i--){
                Collections.swap(list, i, i-1);
            }
        }

        list.set(newPosition, temp);
    }

    @Override
    public void setHideItem(int hidePosition) {
        this.mHidePosition = hidePosition;
        notifyDataSetChanged();
    }

}
