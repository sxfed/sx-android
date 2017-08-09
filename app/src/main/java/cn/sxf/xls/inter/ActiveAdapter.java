package cn.sxf.xls.inter;

/**
 * Created by fenglonghui on 2017/6/6.
 */

public interface ActiveAdapter {
    /**
     * reset sort data
     * @param oldPosition
     * @param newPosition
     */
    public void reorderItems(int oldPosition, int newPosition);


    /**
     * hide item
     * @param hidePosition
     */
    public void setHideItem(int hidePosition);

}
