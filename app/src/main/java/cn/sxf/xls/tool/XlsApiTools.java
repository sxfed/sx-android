package cn.sxf.xls.tool;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 工具类
 * @author flh
 */
public class XlsApiTools {


    /**
     * 获取本地资源字符串
     * @param act
     * @param id
     * @return
     */
    public static String getResString(Activity act, int id) {
        return act.getResources().getString(id);
    }

    /**
     * 读取本地文件JSON数据
     * @param mContext
     * @param fileName
     * @return
     * @throws JSONException
     */
    public static JSONObject getJsonByFile(Context mContext, String fileName) throws JSONException{
        return JsonUtils.toJson(resultFromFile(mContext,fileName));
    }

    /**
     * 从asset路径下读取对应文件转String输出
     * @param mContext
     * @return
     */
    public static String resultFromFile(Context mContext, String fileName) {
        StringBuilder sb = new StringBuilder();
        AssetManager am = mContext.getAssets();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    am.open(fileName)));
            String next = "";
            while (null != (next = br.readLine())) {
                sb.append(next);
            }
        } catch (IOException e) {
            e.printStackTrace();
            sb.delete(0, sb.length());
        }
        return sb.toString().trim();
    }

}
