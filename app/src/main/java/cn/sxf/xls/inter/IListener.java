package cn.sxf.xls.inter;



/**
 * 网络请求回调接口
 */
public interface IListener {
    // 网络请求完成时回调
    public void onComplete(int reqCode, String result);
    // 网络请求异常
    public void  onException(int reqCode, String result);
}
