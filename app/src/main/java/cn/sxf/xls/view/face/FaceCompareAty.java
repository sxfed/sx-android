/**
 * Project Name:cwFaceForDev3
 * File Name:FaceCompareActivity.java
 * Package Name:cn.cloudwalk.dev.mobilebank
 * Date:2016-5-11 9:18:25
 * Copyright @ 2010-2016 Cloudwalk Information Technology Co.Ltd All Rights Reserved.
 */

package cn.sxf.xls.view.face;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cn.cloudwalk.libproject.Contants;
import cn.cloudwalk.libproject.TemplatedActivity;
import cn.cloudwalk.libproject.net.HttpManager;
import cn.cloudwalk.libproject.util.Base64Util;
import cn.cloudwalk.libproject.util.ImgUtil;
import cn.cloudwalk.libproject.util.ToasterUtil;
import cn.sxf.xls.R;
import cn.sxf.xls.global.Constant;

/**
 * ClassName: FaceCompareActivity <br/>
 * Description: 人脸比对 <br/>
 * date: 2016-5-11 9:18:25 <br/>
 *
 * @author 284891377
 * @version
 * @since JDK 1.7
 */
public class FaceCompareAty extends TemplatedActivity implements OnClickListener {
    Button mBt_compare;
    ImageView mIv_tianj1, mIv_tianj2;
    TextView mTv_info;

    Bitmap face1Bitmap, face2Bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        setTitle("人脸比对");
        initView();
    }

    private void initView() {

        mIv_tianj1 = (ImageView) findViewById(R.id.iv_tianj1);
        mIv_tianj1.setOnClickListener(this);

        mIv_tianj2 = (ImageView) findViewById(R.id.iv_tianj2);
        mIv_tianj2.setOnClickListener(this);

        mBt_compare = (Button) findViewById(R.id.bt_compare);
        mBt_compare.setOnClickListener(this);

        mTv_info = (TextView) findViewById(R.id.tv_info);
        mTv_info.setText("?\n是否是同一个人");
        if(!openTakePhoto() || !openPhotoLibrary()){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},Constant.CameraRequestCode);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},Constant.WriteExternalStorageRequestCode);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_compare:
                if (face1Bitmap == null || face2Bitmap == null) {
                    ToasterUtil.showToast(this, null, "请选择图片");
                    return;
                }
                String imgBase64, imgAase64;
                byte[] imgBData = ImgUtil.bitmapToByte(face2Bitmap, CompressFormat.JPEG, Contants
                        .JPG_QUALITY);
                imgBase64 = Base64Util.encode(imgBData);
                imgBData = null;
                byte[] imgAData = ImgUtil.bitmapToByte(face1Bitmap, CompressFormat.JPEG, Contants
						.JPG_QUALITY);
                imgAase64 = Base64Util.encode(imgAData);
                imgAData = null;


                processDialog.setLabel(getString(R.string.cloudwalk_faceverifying)).show();

                HttpManager.cwFaceComper(FaceAty.faceserver, FaceAty.faceappid,
                        FaceAty.faceappser,
                        imgAase64, imgBase64, new HttpManager.DataCallBack() {

                            @Override
                            public void requestSucess(JSONObject result) {
                                if (processDialog != null && processDialog.isShowing())
                                    processDialog.dismiss();
                                try {
                                    double score = result.getJSONObject("data").optDouble("sim");
                                    if (FaceAty.yuz <= score) {
                                        mTv_info.setText(score + "\n  是同一个人");
                                    } else {
                                        mTv_info.setText(score + "\n  不是同一个人");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }

                            @Override
                            public void requestFailure(String errorMsg) {
                                if (processDialog != null && processDialog.isShowing())
                                    processDialog.dismiss();
                                makeToast(errorMsg);
                            }

                        });

                break;
            case R.id.iv_tianj1:
                checkTakePhotoPermission(REQ_PHOTO1, REQ_CAMERA1);
                break;
            case R.id.iv_tianj2:
                checkTakePhotoPermission(REQ_PHOTO2, REQ_CAMERA2);
                break;
        }

    }

    AlertDialog alertDialog;

    File cametaImgFile;

    private final static int REQ_PHOTO1 = 1, REQ_CAMERA1 = 2, REQ_PHOTO2 = 3, REQ_CAMERA2 = 4;

    public void startAlertDialog(final int req_photo, final int req_camera) {
        alertDialog = new AlertDialog.Builder(this).setTitle("获取图片").setMessage("从相机或者相册获取图片")
                .setPositiveButton("相册", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        IntentPhoto(req_photo);
                    }
                }).setNegativeButton("相机", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cametaImgFile = intentCamera(req_camera, FaceCompareAty.this);
                    }
                }).create();
        alertDialog.show();
    }

    /**
     * 是否打开(拍照/图库)的权限
     */
    public void checkTakePhotoPermission(int req_photo, int req_camera){
        if(openTakePhoto() && openPhotoLibrary()){
            startAlertDialog(req_photo, req_camera);
        }else{
            if(!openTakePhoto()){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},Constant.CameraRequestCode);
            }

            if(!openPhotoLibrary()){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},Constant.WriteExternalStorageRequestCode);
            }
        }
    }

    /**
     * 判断拍照权限
     * @return
     */
    public boolean openTakePhoto(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 权限未打开
            return false;
        }else{
            // 权限已打开
            return true;
        }
    }

    public boolean openPhotoLibrary(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 权限未打开
            return false;
        }else{
            // 权限已打开
            return true;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            mTv_info.setText("?\n是否是同一个人");

            if (requestCode == REQ_PHOTO1 || requestCode == REQ_PHOTO2) {// 相册
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {

                    String photoImgFilePath = ImgUtil.getPath(this, selectedImageUri);
                    setImage(requestCode, photoImgFilePath);
                }
            }
            if (requestCode == REQ_CAMERA1 || requestCode == REQ_CAMERA2) {// 相机

                setImage(requestCode, cametaImgFile.getAbsolutePath());
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setImage(int resultCode, String photoImgFilePath2) {

        try {
            switch (resultCode) {
                case REQ_PHOTO1:
                case REQ_CAMERA1:
                    int degree = ImgUtil.readPictureDegree(photoImgFilePath2);
                    // 图片压缩
                    if (degree > 0) {
                        face1Bitmap = ImgUtil.rotaingImageView(degree,
                                ImgUtil.zoomPic(photoImgFilePath2, 1600, 1600, Bitmap.Config.RGB_565));
                    } else {
                        face1Bitmap = ImgUtil.zoomPic(photoImgFilePath2, 1600, 1600, Bitmap.Config.RGB_565);
                    }
                    if (face1Bitmap == null) return;
                    mIv_tianj1.setImageBitmap(face1Bitmap);


                    break;
                case REQ_PHOTO2:
                case REQ_CAMERA2:

                    degree = ImgUtil.readPictureDegree(photoImgFilePath2);
                    // 三星拍照角度
                    if (degree > 0) {
                        face2Bitmap = ImgUtil.rotaingImageView(degree,
                                ImgUtil.zoomPic(photoImgFilePath2, 1600, 1600, Bitmap.Config.RGB_565));
                    } else {
                        face2Bitmap = ImgUtil.zoomPic(photoImgFilePath2, 1600, 1600, Bitmap.Config.RGB_565);
                    }
                    if (face2Bitmap == null) return;
                    mIv_tianj2.setImageBitmap(face2Bitmap);

                    break;

            }
        } catch (OutOfMemoryError e) {

            e.printStackTrace();
        }

    }

    @Override
    public void onLeftClick(View v) {
        onBackPressed();

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, FaceAty.class));

    }

    @Override
    protected void onDestroy() {
        if (face1Bitmap != null) {
            face1Bitmap.recycle();
            face1Bitmap = null;
        }
        if (face2Bitmap != null) {
            face2Bitmap.recycle();
            face2Bitmap = null;
        }
        System.gc();
        Runtime.getRuntime().runFinalization();
        System.gc();

        super.onDestroy();
    }
}
