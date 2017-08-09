package cn.sxf.xls.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import cn.sxf.xls.R;
import cn.sxf.xls.entity.AdvertisInfo;
import cn.sxf.xls.entity.GrideInfo;
import cn.sxf.xls.entity.UnitInfo;
import cn.sxf.xls.tool.OkManager;


/**
 * 测试类
 */
public class Test {

    // "00" native，"01" rn, "02" webview
    public static GrideInfo gi1 = new GrideInfo("原生控件", "00", "url", "iconlink", "market", "android");
    public static GrideInfo gi2 = new GrideInfo("WebView控件", "01", "url", "iconlink", "market", "android");
    public static GrideInfo gi3 = new GrideInfo("RN控件", "02", "url", "iconlink", "market", "android");
    public static GrideInfo[] gis = {gi1,gi2,gi3};
    public static List<HashMap<UnitInfo, Integer>> list;

    //图片下载的请求地址
    public static final String img_path = "https://www.baidu.com/img/bd_logo1.png";
    public static final String json_path = "http://home.baidu.com/";
    public static final String login_path = "http://172.16.136.165:8080/";




    // 测试九宫格数据，https://raw.github.com/square/okhttp/master/README.md
    public static List<HashMap<UnitInfo, Integer>> testGrideData(ArrayList<UnitInfo> grids){
        if (list != null) {
            if (list.size() > 0)
                list.clear();
        } else {
            list = new ArrayList<HashMap<UnitInfo, Integer>>();
        }
        Object[] gis = grids.toArray();
        for (int i = 0; i < gis.length; i++) {
            HashMap<UnitInfo, Integer> map = new HashMap<UnitInfo, Integer>();
            int pos = i;
            map.put((UnitInfo) gis[pos], R.mipmap.app_phonecharge);
            list.add(map);
        }
        return list;
    }


    /**
     * 测试轮播图数据
     */
    public static ArrayList<String> testBannerData(){
        ArrayList<String> infos = new ArrayList<String>();
        infos.add("https://www.baidu.com/img/bd_logo1.png");
        infos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496755714479&di=c9f112ccdfc75348405e4202cb1db7b1&imgtype=0&src=http%3A%2F%2Fwww.icosky.com%2Ficon%2Fpng%2FAvatar%2FElite%2520Captains%2FElite%2520Captain%2520Blue.png");
        infos.add("http://img.sj33.cn/uploads/allimg/201412/7_122G02R5Ab.png");
        infos.add("http://img.sj33.cn/uploads/allimg/201412/7_121ZT6493956.png");
        return infos;
    }
}
