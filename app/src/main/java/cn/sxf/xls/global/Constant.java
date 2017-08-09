package cn.sxf.xls.global;

import cn.sxf.xls.R;

/**
 * 全局的常量数据
 * Created by fenglonghui on 2017/7/3.
 */

public class Constant {

    // 应用权限请求码
    public static final int PERMISSION_REQUEST = 0x11;
    // 打开拍照权限请求码
    public static final int CameraRequestCode = 0x12;
    // 打开拍照存储权限请求码
    public static final int WriteExternalStorageRequestCode = 0x13;
    // 默认轮播时间间隔，单位：毫秒
    public static final String DEFAULT_CYCLE_TIME = "4000";
    // 连接时间 单位秒
    public final static int CONNECT_TIMEOUT =10;
    // 读数据时间 单位秒
    public final static int READ_TIMEOUT=30;
    // 写数据 单位秒
    public final static int WRITE_TIMEOUT=10;
    // 字符串类型
    public static final int RESULT_STRING_TYPE = 0;
    // 字节数组类型
    public static final int RESULT_BYTE_ARRAY_TYPE = 1;
    // 流类型
    public static final int RESULT_STREAM_TYPE = 2;
    // 本地资源文件名
    public static final String ADSSSERT_FILE_NAME = "data.txt";
    // WebView所需的链接地址
    public static final String mWebViewUrl = "http://172.16.40.34:8010/merch/merchType?id=54e1ed3fe520449391f6cfc5dba4ce71&orgNo=0958416327&origin=tss&roleIdentity=ROLE_IDENTITY_ADMIN&userCode=110665";
    // 获取九宫格数据
    public static final String GRIDS_URL = "http://172.16.41.171:3000/menuList";
    // 获取轮播图数据
    public static final String CYCLES_URL = "http://172.16.41.171:3000/cycleList";
    public static final String IMG_PATH = "https://www.baidu.com/img/bd_logo1.png";
    // tab动态标签测试
    public static int[] tabs = {R.drawable.rb_home_selector,R.drawable.rb_rank_selector,R.drawable.rb_classify_selector,R.drawable.rb_myinfo_selector};

}
