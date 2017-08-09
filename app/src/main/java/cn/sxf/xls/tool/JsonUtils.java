package cn.sxf.xls.tool;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class JsonUtils {
	
	/** 默认的 {@code JSON} 日期/时间字段的格式化模式。 */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";
	
	/**
	 * 字符串转化成json
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject toJson(String data) throws JSONException{
		return new JSONObject(data);
	}
	
	public static JSONObject newJSONOBJECT(){
		return new JSONObject();
	}
	
	
	/**
	 * JSON转化成字符串
	 * @param json
	 * @return
	 */
	public static String toStr(JSONObject json){
		return json.toString();
	}
	
	/**
	 * str转化成json数组
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	public static JSONArray toJSONArray(String data) throws JSONException{
		return new JSONArray(data);
	}
	
	
	
	public static JSONArray toJSONArrayObject(JSONObject data) throws JSONException{
		return new JSONArray(data);
	}
	
	
	
	/**
	 * 来自JSONObject中的json数组
	 * @param data
	 * @param keyword
	 * @return
	 * @throws JSONException
	 */
	public static JSONArray arrayFromJSONObject(JSONObject data,String keyword) throws JSONException{
		return data.getJSONArray(keyword);
	}
	
	/**
	 * 来自JSONObject中json对象
	 * @param data
	 * @param keyword
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject jsonFromJSONObject(JSONObject data,String keyword) throws JSONException{
		return data.getJSONObject(keyword);
	}

	/**
	 * 根据JSON数组得到List列表
	 * @param jsonArry
	 * @param clazz
	 * @return
	 */
	public static <T> ArrayList<T> toListFromJSONArray(JSONArray jsonArry, Class<T> clazz) {
		ArrayList<T> list = new ArrayList<T>();
		try {
			for (int i = 0; i < jsonArry.length(); i++) {
				list.add(toGson(jsonArry.getJSONObject(i).toString(), clazz));
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	/**
	 * 根据JSON数组得到List<String>列表
	 * @param jsonArry
	 * @return
	 */
	public static ArrayList<String> toListFromJSONArray(JSONArray jsonArry) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			for (int i = 0; i < jsonArry.length(); i++) {
				list.add(jsonArry.getString(i));
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	/**
	 * 根据JSON对象及key值得到List列表
	 * @param json
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static <T> ArrayList<T> toListFromJSON(JSONObject json, String key, Class<T> clazz) {
		ArrayList<T> list = new ArrayList<T>();
		try {
			JSONArray menuListArry = arrayFromJSONObject(json, key);
			list = toListFromJSONArray(menuListArry, clazz);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	/**
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
     * 对象。</strong>
     *
     * @param <T>
     *            要转换的目标类型。
     * @param json
     *            给定的 {@code JSON} 字符串。
     * @param clazz
     *            要转换的目标类。
     * @param datePattern
     *            日期格式模式。
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
     */
    private static <T> T fromJson(String json, Class<T> clazz, String datePattern) {
        if (isEmpty(json)) {
            return null;
        }
        GsonBuilder builder = new GsonBuilder();
        if (isEmpty(datePattern)) {
            datePattern = DEFAULT_DATE_PATTERN;
        }
        Gson gson = builder.create();
        try {
            return gson.fromJson(json, clazz);
        } catch (Exception ex) {
            return null;
        }
    }
    
    
    
    public static boolean isEmpty(String inStr) {
        boolean reTag = false;
        if (inStr == null || "".equals(inStr)) {
            reTag = true;
        }
        return reTag;
    }
	
	
	/**
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
     * 对象。</strong>
     *
     * @param <T>
     *            要转换的目标类型。
     * @param json
     *            给定的 {@code JSON} 字符串。
     * @param clazz
     *            要转换的目标类。
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
     */
    public static <T> T toGson(String json, Class<T> clazz) {
        return fromJson(json, clazz, null);
    }
 
    
	
	
}
