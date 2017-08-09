package cn.sxf.xls.tool;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import cn.sxf.xls.entity.UnitInfo;


/**
 * 文件工具类
 * @author flh
 */
public class ResultService {
    private OkManager manager;

    public ResultService(){
        manager = OkManager.getInstance();
    }

    /**
     * Get请求下载图片
     * @param url
     * @param callback
     */
    public void reqResultByteArray(String url, OkManager.OnResultCallback<byte[]> callback){
        manager.asyResultBytes(url, callback);
    }

    public void reqResultPostByteArray(String url, OkManager.OnResultCallback<byte[]> callback){
        manager.asyResultPostBytes(url, callback);
    }

    /**
     * Get请求返回字符串
     * @param url
     * @param callback
     */
    public void reqResultGet(String url, OkManager.OnResultCallback<String> callback){
        manager.asyResult(url, callback);
    }

    /**
     * Post请求返回字符串
     * @param url
     * @param callback
     */
    public void reqResultPost(String url, OkManager.OnResultCallback<String> callback){
        manager.asyResultPost(url, callback);
    }

    /**
     * Get请求返回结果流
     * @param url
     * @param callback
     */
    public void reqResultStream(String url, OkManager.OnResultCallback<InputStream> callback){
        manager.asyResultStream(url, callback);
    }

    /**
     * 初始化ImageLoader
     * @param con
     */
    public void initImageLoader(Context con){
        manager.configImageLoader(con);
    }
    /**
     * Post请求返回结果流
     * @param url
     * @param callback
     */
    public void reqResultPostStream(String url, OkManager.OnResultCallback<InputStream> callback){
        manager.asyResultPostStream(url, callback);
    }

    /**
     * 解析九宫格数据
     * @param result
     * @param clazz
     * @param <T>
     * @return
     * @throws JSONException
     */
    public <T> ArrayList<T> getJSONArrayList(String result, Class<T> clazz) throws JSONException{
        return JsonUtils.toListFromJSONArray(JsonUtils.toJSONArray(result),clazz);
    }

    /**
     * 解析轮播图数据
     * @param result
     * @return
     * @throws JSONException
     */
    public ArrayList<String> getJSONArrayList(String result) throws JSONException{
        return JsonUtils.toListFromJSONArray(JsonUtils.toJSONArray(result));
    }

    /**
     * 获取本地JSON文件九宫格数据
     * @param con
     * @param filename
     * @param key menuList
     */
    public static ArrayList<Object> getGridsFromJsonFile(Context con, String filename, String key) {
        ArrayList<Object> grids = new ArrayList<Object>();
        try {
            JSONObject result = XlsApiTools.getJsonByFile(con, filename);
            JSONArray menuListArry = JsonUtils.arrayFromJSONObject(result, key);
            for (int i = 0; i < menuListArry.length(); i++) {
                grids.add(JsonUtils.toGson(menuListArry.getJSONObject(i).toString(), UnitInfo.class));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return grids;
    }

    /**
     * 获取本地JSON文件轮播图数据
     * @param con
     * @param filename
     * @param key cycleList
     */
    public static ArrayList<String> getCyclesFromJsonFile(Context con, String filename, String key) {
        ArrayList<String> cycleImages = new ArrayList<String>();
        try {
            JSONObject result = XlsApiTools.getJsonByFile(con, filename);
            JSONArray cycleListArray = JsonUtils.arrayFromJSONObject(result, key);
            for (int index = 0; index < cycleListArray.length(); index++) {
                cycleImages.add(cycleListArray.getString(index));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return cycleImages;
    }

}
