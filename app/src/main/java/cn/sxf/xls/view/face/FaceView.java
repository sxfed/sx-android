package cn.sxf.xls.view.face;

import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import cn.cloudwalk.jni.FaceInfo;
import cn.cloudwalk.libproject.util.DisplayUtil;
import cn.cloudwalk.libproject.util.LogUtils;

public class FaceView extends View {
	private static final String TAG = LogUtils.makeLogTag("FaceView");

	Context mContext;

	Paint mLinePaint, mTextPaint, mPointPaint;
	FaceInfo[] faceInfos;
	private int faceNum;
	int textSize = 22;
	private int surfaceW, surfaceH;
	double scale;
	int frameWidth = 480;
	int frameHight = 640;
	DecimalFormat df = new DecimalFormat("0.00");

	public void setSurfaceWH(int surfaceW, int surfaceH,int frameWidth,int frameHight) {
		this.surfaceW = surfaceW;
		this.surfaceH = surfaceH;
		this.frameWidth = frameWidth;
		this.frameHight = frameHight;
		scale = surfaceW * 1d / frameWidth;

	}

	/**
	 * setFaces:设置人脸框和人脸关键点. <br/>
	 * 
	 * @author:284891377 Date: 2016-4-29 上午9:45:19
	 * @param faceInfos
	 * @param faceNum
	 * @since JDK 1.7
	 */
	public void setFaces(FaceInfo[] faceInfos, int faceNum) {
		this.faceInfos = faceInfos;
		this.faceNum = faceNum;

		invalidate();
	}

	public FaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		initPaint();

	}

	private void initPaint() {
		textSize = DisplayUtil.dip2px(mContext, 16);
		mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		// int color = Color.rgb(0, 150, 255);
		int color = Color.rgb(98, 212, 68);
		mLinePaint.setColor(color);
		mLinePaint.setStyle(Style.STROKE);
		mLinePaint.setStrokeWidth(5f);
		mLinePaint.setAlpha(180);

		mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPointPaint.setColor(color);
		mPointPaint.setStrokeWidth(10f);
		mPointPaint.setAlpha(180);

		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setColor(color);
		mTextPaint.setTextSize(textSize);
		mTextPaint.setAlpha(180);

	}

	@Override
	protected void onDraw(Canvas canvas) {

		if (faceInfos != null) {

			for (int i = 0; i < faceNum; i++) {

				FaceInfo faceInfo = faceInfos[i];
				// 人脸坐标转换

				faceInfo.recty = (int) (faceInfo.y * scale);
				faceInfo.rectheight = (int) (faceInfo.height * scale);

				faceInfo.rectwidth = (int) (faceInfo.width * scale);
				faceInfo.rectx = (int) (faceInfo.x * scale);

				// 关键点转换
				if (faceInfo.pointX != null && faceInfo.pointY != null) {
					int pointXLen = faceInfo.pointX.length;
					if (faceInfo.drawpointX == null)
						faceInfo.drawpointX = new float[pointXLen];
					if (faceInfo.drawpointY == null)
						faceInfo.drawpointY = new float[pointXLen];
					for (int j = 0; j < pointXLen; j++) {
						faceInfo.drawpointX[j] = (int) (faceInfo.pointX[j] * scale);
						faceInfo.drawpointY[j] = (int) (faceInfo.pointY[j] * scale);
						canvas.drawPoint(faceInfo.drawpointX[j], faceInfo.drawpointY[j], mPointPaint);
					}

				}
				// 原点转换
				// if (imageangle == ImageAngle.CW_IMG_ANGLE_270) {
				//
				// } else if (imageangle == ImageAngle.CW_IMG_ANGLE_0) {
				// int temp = faceInfo.width;
				// faceInfo.width = faceInfo.height;
				// faceInfo.height = temp;
				//
				// temp = faceInfo.y;
				// faceInfo.y = faceInfo.x;
				// faceInfo.x = surfaceW - temp - faceInfo.width;
				//
				// }
				// 质量信息
				StringBuilder sb = new StringBuilder();
				// detected
				if (faceInfo.faceId == -1) {
					sb.append("_;");// 跟踪过程切换
				} else {
					sb.append(faceInfo.faceId + ";");
				}

				// 抬点动作
				if (faceInfo.headPitch == 1)
					sb.append("抬;");
				else if (faceInfo.headPitch == -1)
					sb.append("点;");
				else
					sb.append("_;");
				// 左右动作
				if (faceInfo.headYaw == 1)
					sb.append("左;");
				else if (faceInfo.headYaw == -1)
					sb.append("右;");
				else
					sb.append("_;");

				// 嘴部动作
				if (faceInfo.mouthAct != 0)
					sb.append("张;");
				else
					sb.append("_;");
				// 眼睛动作
				if (faceInfo.eyeAct != 0)
					sb.append("睁;");
				else
					sb.append("_;");
				// 分数

				sb.append(df.format(faceInfo.keyptScore) + "");

				canvas.drawText(sb.toString(), faceInfo.rectx, faceInfo.recty - textSize, mTextPaint);
				canvas.drawRect(new Rect(faceInfo.rectx, faceInfo.recty, faceInfo.rectx + faceInfo.rectwidth,
						faceInfo.recty + faceInfo.rectheight), mLinePaint);

			}

		}
	}

}
