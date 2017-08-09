package cn.sxf.xls.tool;


import android.content.Context;
import cn.sxf.xls.R;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import cn.sxf.xls.global.Constant;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 网络请求工具类
 * 参考：http://www.cnblogs.com/whoislcj/p/5526431.html
 * @author flh
 */
public class OkManager {
    //获得类名
    private final String TAG = OkManager.class.getSimpleName();
    private OkHttpClient client;
    private volatile static OkManager manager;
    private Handler handler;

    private OkManager() {
        client = new OkHttpClient().newBuilder()
                .connectTimeout(Constant.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constant.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constant.WRITE_TIMEOUT,TimeUnit.SECONDS)
                .build();
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     *采用单例模式获取对象
     */
    public static OkManager getInstance() {
        if (manager == null) {
            manager = new OkManager();
        }
        return manager;
    }

    /**
     * 请求结果字符串
     * @param url
     * @param callback
     */
    public void asyResult(String url, final OnResultCallback callback){
        onRequestResult(url,callback,Constant.RESULT_STRING_TYPE);
    }

    /**
     * 请求结果字符串(POST)
     * @param url
     * @param callback
     */
    public void asyResultPost(String url, final OnResultCallback callback){
        onRequestResultPost(url,callback,Constant.RESULT_STRING_TYPE);
    }

    /**
     * 请求结果字节数组
     * @param url
     * @param callback
     */
    public void asyResultBytes(String url, final OnResultCallback callback){
        onRequestResult(url,callback, Constant.RESULT_BYTE_ARRAY_TYPE);
    }

    /**
     * 请求结果字节数组(PSOT)
     * @param url
     * @param callback
     */
    public void asyResultPostBytes(String url, final OnResultCallback callback){
        onRequestResultPost(url,callback, Constant.RESULT_BYTE_ARRAY_TYPE);
    }

    /**
     * 请求结果流
     * @param url
     * @param callback
     */
    public void asyResultStream(String url, final OnResultCallback callback){
        onRequestResult(url,callback,Constant.RESULT_STREAM_TYPE);
    }

    /**
     * 请求结果流(POST)
     * @param url
     * @param callback
     */
    public void asyResultPostStream(String url, final OnResultCallback callback){
        onRequestResultPost(url,callback,Constant.RESULT_STREAM_TYPE);
    }

    /**
     * ImageLoader初始化,显示网络图片
     * @param con
     */
    public void configImageLoader(Context con) {
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.mipmap.icon_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.icon_empty)
                .showImageOnFail(R.mipmap.icon_error)
                .cacheInMemory(true) // 是否缓存
                .cacheOnDisc(true) // 是否缓存在SD卡中
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(con).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * 网络请求(GET)
     * @param url
     * @param callback
     * @param type 值为0：字符串，1 字节数组，2流
     */
    private Call onRequestResult(final String url, final OnResultCallback callback, final int type){
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                //TODO 请求失败
                failedCallBack(e.getMessage(), callback);
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                //TODO 请求成功
                successCallBack(response, callback, type);
            }
        });
        return call;
    }

    public static final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
    /**
     * 网络请求(POST)
     * @param url
     * @param callback
     * @param type
     * @return
     */
    private Call onRequestResultPost(String url, final OnResultCallback callback, final int type) {
        RequestBody body = new FormBody.Builder().build();
//            RequestBody body = RequestBody.create(mediaType,"");
        Request request = new Request.Builder().url(url).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //TODO 请求失败
                failedCallBack(e.getMessage(), callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //TODO 请求成功
                successCallBack(response, callback, type);
            }
        });
        return call;
    }

    /**
     * 网络请求成功回调
     * @param response
     * @param callBack
     * @param type 值为0：字符串，1 字节数组，2流
     */
    private void successCallBack(final Response response, final OnResultCallback callBack, final int type) {
        try {
            if (Constant.RESULT_STRING_TYPE == type) {
                mSuccessCallBack(callBack, response.body().string());
            } else if (Constant.RESULT_BYTE_ARRAY_TYPE == type) {
                mSuccessCallBack(callBack, response.body().bytes());
            } else if (Constant.RESULT_STREAM_TYPE == type) {
                mSuccessCallBack(callBack, response.body().byteStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通知主线程更新数据
     * @param callBack
     * @param result
     * @param <T>
     */
    private <T> void mSuccessCallBack(final OnResultCallback<T> callBack,final T result){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onResponseSuccess(result);
            }
        });
    }

    /**
     * 网络请求失败回调
     * @param error
     * @param callBack
     * @param <T>
     */
    private <T> void failedCallBack(final String error, final OnResultCallback<T> callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(callBack != null){
                    callBack.onFailure(error);
                }
            }
        });
    }

    /**
     *  接口回调，网络请求回调请求结果
     */
    public interface  OnResultCallback<T> {
        public void onFailure(String error);
        public void onResponseSuccess(T result);
        public void onResponseFailure(int code ,String msg);
    }
}
