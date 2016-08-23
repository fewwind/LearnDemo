package com.lineprogressbutton.fewwind.myapplication;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

public class WatermarkUtil {

	/**
	 * 加水印 也可以加文字
	 * 
	 * @param src
	 * @param watermark
	 * @return
	 */
	public static Bitmap watermarkBitmap(Bitmap src, Bitmap watermark,
										 Point outSize) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();

		// 需要处理图片太大造成的内存超过的问题,这里我的图片很小所以不写相应代码了
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_4444);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newb);
		cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
		Paint paint = new Paint();
		// 加入图片
		if (watermark != null) {
			int ww = watermark.getWidth();
			int wh = watermark.getHeight();
			// paint.setAlpha(50);
			// cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, paint);//
			// 在src的右下角画入水印
			System.out.println("src.getWidth()" + w);
			System.out.println("watermark.getWidth()" + ww);
			System.out.println("watermark.getHeight()" + wh);
			System.out.println("w-ww " + (w - ww));
			if (outSize.x == 720) {
				cv.drawBitmap(watermark, w - ww + 100, h - wh, paint);// 在src的左上角画入水印
			} else if (outSize.x == 1080) {
				cv.drawBitmap(watermark, w - ww, h - wh, paint);// 在src的左上角画入水印
			} else {
				cv.drawBitmap(watermark, w - ww, h - wh, paint);// 在src的左上角画入水印
			}

		} else {
			Log.i("i", "water mark failed");
		}
		/*
		 * //加入文字 if(title!=null) { String familyName ="宋体"; Typeface font =
		 * Typeface.create(familyName,Typeface.NORMAL); TextPaint textPaint=new
		 * TextPaint(); textPaint.setColor(Color.RED);
		 * textPaint.setTypeface(font); textPaint.setTextSize(40); //这里是自动换行的 //
		 * StaticLayout layout = new
		 * StaticLayout(title,textPaint,w,Alignment.ALIGN_OPPOSITE
		 * ,1.0F,0.0F,true); // layout.draw(cv); //文字就加左上角算了
		 * cv.drawText(title,w-400,h-40,textPaint); }
		 */
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		cv.restore();// 存储
		return newb;
	}

	public static void watermarkBitmap(Canvas canvas, Bitmap watermark) {

		// 需要处理图片太大造成的内存超过的问题,这里我的图片很小所以不写相应代码了
		// 加入图片
		if (watermark != null&&!watermark.isRecycled()) {
			int ww = watermark.getWidth();
			int wh = watermark.getHeight();
			canvas.drawBitmap(watermark, canvas.getWidth()-ww, canvas.getHeight()-wh, null);
		}
	}

	public static void mergeBitmap(Canvas canvas, Bitmap watermark) {

		// 需要处理图片太大造成的内存超过的问题,这里我的图片很小所以不写相应代码了
		// 加入图片
		if (watermark != null&&!watermark.isRecycled()) {
			canvas.drawBitmap(watermark,0, 0, null);
		}
	}
}
