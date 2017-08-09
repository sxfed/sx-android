package cn.sxf.xls.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.util.ArrayList;
import cn.sxf.xls.R;
import cn.sxf.xls.global.Constant;
import cn.sxf.xls.tool.DialogueTools;
import cn.sxf.xls.tool.OkManager;
import cn.sxf.xls.tool.ResultService;

/**
 * 原生控件实例
 */
public class NativeComponent extends BaseAty {
    private final static String Tag = NativeComponent.class.getSimpleName();
    private Button testButton, getButton, postButton, button3;
    private ImageView testImageView;
    private ResultService rServece;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_demo);
        init();
    }

    @Override
    public void init() {
        super.init();
        testButton = (Button) findViewById(R.id.test);
        getButton = (Button) findViewById(R.id.getjson);
        postButton = (Button) findViewById(R.id.postjson);
        testImageView = (ImageView) findViewById(R.id.testImageView);
        button3 = (Button) findViewById(R.id.button3);
        rServece = new ResultService();
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rServece.reqResultGet(Constant.GRIDS_URL, new OkManager.OnResultCallback<String>() {
                    @Override
                    public void onFailure(String error) {
                        DialogueTools.ToastDialogue(NativeComponent.this, error, 1);
                    }

                    @Override
                    public void onResponseSuccess(String result) {
                        Log.i(Tag, (String) result);
                    }

                    @Override
                    public void onResponseFailure(int code, String msg) {
                        DialogueTools.ToastDialogue(NativeComponent.this, msg, 1);
                    }
                });
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rServece.reqResultPost(Constant.GRIDS_URL, new OkManager.OnResultCallback<String>(){
                    @Override
                    public void onFailure(String error) {
                        DialogueTools.ToastDialogue(NativeComponent.this, error, 1);
                    }

                    @Override
                    public void onResponseSuccess(String result) {
                        Log.i(Tag, (String) result);
                    }

                    @Override
                    public void onResponseFailure(int code, String msg) {
                        DialogueTools.ToastDialogue(NativeComponent.this, msg, 1);
                    }
                });
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Object> infos = new ArrayList<Object>();
                // 读取本地资源文件
                infos = ResultService.getGridsFromJsonFile(NativeComponent.this, Constant.ADSSSERT_FILE_NAME, "menuList");
                DialogueTools.ToastDialogue(NativeComponent.this, infos.size() + "条数据", 1);
            }
        });

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rServece.reqResultByteArray(Constant.IMG_PATH, new OkManager.OnResultCallback<byte[]>() {
                    @Override
                    public void onFailure(String error) {
                        // todo 网络链接失败
                    }

                    @Override
                    public void onResponseSuccess(byte[] result) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
                        testImageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onResponseFailure(int code, String msg) {
                        // TODO 服务器请求失败
                    }
                });
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NativeComponent.this, NavigatorAty.class));
            }
        });
    }

    @Override
    public void isVisibilityTitleArea() {
        initTitle("Native控件", true, false);
    }

    @Override
    public void dynamicReqPermission() {
    }

}
