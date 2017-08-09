package cn.sxf.xls.view.face;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import cn.cloudwalk.libproject.util.DisplayUtil;
import cn.cloudwalk.libproject.util.LogUtils;
import cn.sxf.xls.R;

public class OcrMaskView extends View {
	private static final int FOCUSING = 1;
	private static final int FOCUSED = 2;
	private static final int NOFOCUS = 0;

	private static final String TAG = LogUtils.makeLogTag("OcrMaskView");

	Context mContext;

	private Paint mLinePaint, mRectPoint;
	int width;
	int height;
	float focusX, focusY;
	int focusType;

	int ocrRectH, ocrRectW, ocrRectLeft, ocrRectTop;

	public OcrMaskView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		initPaint();

	}

	/**
	 * 
	 * setFocus:设置正在获取焦点 <br/>
	 * 
	 * @author:284891377 Date: 2016年6月30日 下午5:17:06
	 * @param x
	 * @param y
	 * @since JDK 1.7
	 */
	public void setFocus(float x, float y) {
		focusX = x;
		focusY = y;
		focusType = FOCUSING;
		invalidate();
	}

	/**
	 * 
	 * setFocused:设置已经获取焦点 <br/>
	 * 
	 * @author:284891377 Date: 2016年6月30日 下午5:17:06
	 * 
	 * @since JDK 1.7
	 */
	public void setFocused() {

		focusType = FOCUSED;
		invalidate();
	}

	public void setOcr(int width, int height, int ocrRectH, int ocrRectW) {
		this.width = width;
		this.height = height;
		this.ocrRectH = ocrRectH;
		this.ocrRectW = ocrRectW;
		ocrRectLeft = (width - ocrRectW) / 2;
		ocrRectTop = (height - ocrRectH) / 2;
	}

	/**
	 * 
	 * clearFocus:清除焦点 <br/>
	 * 
	 * @author:284891377 Date: 2016年6月30日 下午5:17:06
	 * @since JDK 1.7
	 */
	public void clearFocus() {
		focusType = NOFOCUS;
		invalidate();
	}

	private void initPaint() {
		final float scale = getResources().getDisplayMetrics().density;

		mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

		mRectPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mRectPoint.setColor(0xAA525252);

	}

	@Override
	protected void onDraw(Canvas canvas) {

		// 画四周mask层
		drawMask(canvas);
		// 画焦点
		if (focusType == FOCUSING) {
			Bitmap focusBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.focus);
			canvas.drawBitmap(focusBitmap, focusX - focusBitmap.getWidth() / 2, focusY - focusBitmap.getHeight() / 2,
					null);
		} else if (focusType == FOCUSED) {
			Bitmap focusBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.focused);
			canvas.drawBitmap(focusBitmap, focusX - focusBitmap.getWidth() / 2, focusY - focusBitmap.getHeight() / 2,
					null);
		}
		// 画文字
		mLinePaint.setTextSize(DisplayUtil.dip2px(mContext, 18));
		mLinePaint.setColor(Color.WHITE);
		mLinePaint.setTextAlign(Paint.Align.CENTER);
		canvas.drawText(mContext.getResources().getString(R.string.ocr_tip), width / 2,
				(int) (ocrRectTop + ocrRectH + 50), mLinePaint);

	}

	private void drawMask(Canvas canvas) {
		// TODO 部分手机拍照变灰
		// final Rect frame = new Rect(ocrRectLeft, ocrRectTop, ocrRectLeft +
		// ocrRectW, ocrRectTop + ocrRectH);
		// canvas.save();
		// canvas.clipRect(frame, Region.Op.XOR);
		// canvas.drawColor(mMaskColor);
		// canvas.restore();
		// canvas.save();
		Rect frameTop = new Rect(0, 0, width, ocrRectTop);
		canvas.drawRect(frameTop, mRectPoint);
		Rect frameLeft = new Rect(0, ocrRectTop, ocrRectLeft, ocrRectTop + ocrRectH);
		canvas.drawRect(frameLeft, mRectPoint);
		Rect frameRight = new Rect(ocrRectLeft + ocrRectW, ocrRectTop, width, ocrRectTop + ocrRectH);
		canvas.drawRect(frameRight, mRectPoint);
		Rect frameBottom = new Rect(0, ocrRectTop + ocrRectH, width, height);
		canvas.drawRect(frameBottom, mRectPoint);

	}

}
