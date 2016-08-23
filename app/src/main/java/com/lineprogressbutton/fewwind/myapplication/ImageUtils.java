package com.lineprogressbutton.fewwind.myapplication;

/*
 * Copyright (C) 2014 RC Platform (HK) Inc.,
 */
/**
 * @Title: ImageUtils.java
 * @Package com.example.studyblur
 * @Description: 图片工具类(用一句话描述该文件做什么)
 * @author cuizheng Email： cuizheng@rcplatformhk.com
 * @date 2014-5-12 下午4:06:55
 * @version : 1.0
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author cuizheng Email： cuizheng@rcplatformhk.com
 * @version : 1.0
 * @ClassName: ImageUtils
 * @Description: 图片工具类(这里用一句话描述这个类的作用)
 */
public class ImageUtils {

    private static final String TAG = "123";
    private static final String[] IMAGE_FORMAT = new String[]{"jpg", "png",
            "gif", "bmp", "jpeg", "jfif", "tif", "tiff", "ico"};

    private static float[] sMatrixScaleValues = new float[9];






    private static void galleryAddPic(Context context, String photoPath) {
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(photoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }


    public static Bitmap createRepeater(int size, Bitmap src) {
        int count = (size + src.getWidth() - 1) / src.getWidth();
        Bitmap bitmap = Bitmap.createBitmap(size, size, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; ++j) {
                canvas.drawBitmap(src, j * src.getWidth(), i * src.getHeight(),
                        null);
            }
        }
        return bitmap;
    }

    /**
     * 获取按照请求的大小压缩图片
     *
     * @param reqWidth  请求宽度
     * @param reqHeight 请求高度
     * @return 设定文件.Bitmap 返回类型
     * @throws
     * @Title: getSampleSizeBitmap
     * @Description: 图片路径
     */
    public static Bitmap getSampleSizeBitmap(Context context, Uri uri,
                                             int reqWidth, int reqHeight) {
        InputStream input = null;
        try {
            input = context.getContentResolver().openInputStream(uri);
            Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inDither = true;// optional
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            BitmapFactory.decodeStream(input, null, options);
            input.close();
            int imageHeight = options.outHeight;
            int imageWidth = options.outWidth;
            int inSampleSize = 1;
            if (imageHeight > reqHeight || imageWidth > reqWidth) {
                final int halfHeight = imageHeight / 2;
                final int halfWidth = imageWidth / 2;
                // Calculate the largest inSampleSize value that is a power of 2
                // and
                // keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) > reqHeight
                        && (halfWidth / inSampleSize) > reqWidth) {
                    inSampleSize *= 2;
                }
            }
            options.inSampleSize = inSampleSize;
            options.inDither = true;// optional
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
            options.inJustDecodeBounds = false;
            input = context.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(input, null, options);
            input.close();
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getSampleSizeBitmap(Context context, int imgRes,
                                             int reqWidth, int reqHeight) {
        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), imgRes, opts);
        int imageHeight = opts.outHeight;
        int imageWidth = opts.outWidth;
        int inSampleSize = 1;
        if (imageHeight > reqHeight || imageWidth > reqWidth) {

            final int halfHeight = imageHeight / 2;
            final int halfWidth = imageWidth / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        opts.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                imgRes, opts);
        return bitmap != null ? bitmap : BitmapFactory.decodeResource(
                context.getResources(), imgRes);
    }

    public static Bitmap getSampleSizeBitmap(String imagePath, int reqWidth,
                                             int reqHeight) {
        Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        int inSampleSize = 1;
        if (imageHeight > reqHeight || imageWidth > reqWidth) {

            final int halfHeight = imageHeight / 2;
            final int halfWidth = imageWidth / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath, options);
    }

    //缩放bitmap，如果是true则是等比缩放
    public static Bitmap resizeImage(Bitmap bitmap, int w, int h, boolean flag) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        float mix = Math.min(scaleWidth, scaleHeight);
        Matrix matrix = new Matrix();

        if (flag) {
            matrix.postScale(mix, mix);

        } else {
            matrix.postScale(scaleWidth, scaleHeight);
        }
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);

        return Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
    }


    //从bitmap 中间截取
    public static Bitmap resizeImageMiddle(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();

        return Bitmap.createBitmap(BitmapOrg, (width - newWidth) / 2, (height - newHeight) / 2, newWidth,
                newHeight);
    }

    public static Bitmap getTransparentBitmap(Bitmap sourceImg, int number) {
        int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];

        sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg

                .getWidth(), sourceImg.getHeight());// 获得图片的ARGB值

        number = number * 255 / 100;

        for (int i = 0; i < argb.length; i++) {

            argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);

        }

        sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg

                .getHeight(), Bitmap.Config.ARGB_8888);

        return sourceImg;
    }


    public static void calcSampleSize(BitmapFactory.Options options,
                                      int reqWidth, int reqHeight) {
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        int inSampleSize = 1;
        if (imageHeight > reqHeight || imageWidth > reqWidth) {

            final int halfHeight = imageHeight / 2;
            final int halfWidth = imageWidth / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        options.inSampleSize = inSampleSize;
    }

    public static void storeImage(Bitmap image, File pictureFile) {
        if (pictureFile == null) {
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }


    // public static boolean isImage(Context context,Uri uri) {
    // String realPath = RCImageUtils.getRealPath(context, uri);
    // if (uri == null || TextUtils.isEmpty(realPath))
    // return false;
    // String suffix = realPath.substring(realPath.lastIndexOf(".")+1);
    // suffix = suffix.toLowerCase();
    // for(String s:IMAGE_FORMAT){
    // if(s.equals(suffix)){
    // return true;
    // }
    // }
    // return false;
    // // return
    // RCImageUtils.isDownloadsDocument(uri)||RCImageUtils.isExternalStorageDocument(uri)||RCImageUtils.isMediaDocument(uri);
    // }

    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap, boolean isChecked) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;

            left = 0;
            top = 0;
            right = width;
            bottom = width;

            height = width;

            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;

            float clip = (width - height) / 2;

            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;

            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setAlpha(155);
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);// 设置画笔无锯齿
        paint.setFilterBitmap(true);
        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas

        // 以下有两种方法画圆,drawRounRect和drawCircle
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        // canvas.drawCircle(roundPx, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(bitmap, src, dst, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle
        if (isChecked) {
            paint2.setColor(0xaa000000);
            canvas.drawCircle(width / 2, height / 2, width / 2, paint2);
        }
        return output;
    }

    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static int[] getImageSize(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inDither = true;// optional
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        BitmapFactory.decodeFile(path, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        int[] size = new int[2];
        size[0] = imageWidth;
        size[1] = imageHeight;
        return size;
    }

    public static float getMatrixScale(Matrix matrix) {
        for (int i = 0; i < sMatrixScaleValues.length; i++) {
            sMatrixScaleValues[i] = 0;
        }
        matrix.getValues(sMatrixScaleValues);
        float scalex = sMatrixScaleValues[Matrix.MSCALE_X];
        float skewy = sMatrixScaleValues[Matrix.MSKEW_Y];
        float scale = (float) Math.sqrt(scalex * scalex + skewy * skewy);
        return scale;
    }

    public static float getMatrixTransX(Matrix matrix) {
        float[] values = new float[9];
        matrix.getValues(values);
        float transX = values[Matrix.MTRANS_X];
        return transX;
    }

    public static float getMatrixTransY(Matrix matrix) {
        float[] values = new float[9];
        matrix.getValues(values);
        float transY = values[Matrix.MTRANS_Y];
        return transY;
    }

    public static float getMatrixRotate(Matrix matrix) {
        float[] v = new float[9];
        matrix.getValues(v);
        float rAngle = Math.round(Math.atan2(v[Matrix.MSKEW_X],
                v[Matrix.MSCALE_X]) * (180 / Math.PI));
        return rAngle;
    }


    public static float centerCrop(Matrix matrix, Bitmap bitmap, int reqWidth,
                                   int reqHeight) {
        float scale;
        float dx = 0;
        float dy = 0;
        int dwidth = bitmap.getWidth();
        int dheight = bitmap.getHeight();
        if (dwidth * reqWidth > reqHeight * dheight) {
            scale = (float) reqHeight / (float) dheight;
            dx = (reqWidth - dwidth * scale) * 0.5f;
        } else {
            scale = (float) reqWidth / (float) dwidth;
            dy = (reqHeight - dheight * scale) * 0.5f;
        }
        matrix.setScale(scale, scale);
        matrix.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));
        return scale;
    }

    public static float fitCenterRotate(Matrix matrix, Bitmap bitmap, int reqWidth,
                                        int reqHeight) {
        //小的
        float scale;
        float dx = 0;
        float dy = 0;
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        float widthScale = 1f;
        float heightScale = 1f;
        //为了让小图居中显示，但加上，旋转效果不好
        widthScale = (float) reqWidth / bitmapWidth;
        heightScale = (float) reqHeight / bitmapHeight;
        scale = Math.min(widthScale, heightScale);
        if (widthScale == 1f && heightScale == 1f) {
            dx = (reqWidth - bitmapWidth * scale) * 0.5f;
            dy = (reqHeight - bitmapHeight * scale) * 0.5f;
        } else {
            if (widthScale > heightScale) {
                dx = (reqWidth - bitmapWidth * scale) * 0.5f;
            } else {
                dy = (reqHeight - bitmapHeight * scale) * 0.5f;
            }
        }
        matrix.setScale(scale, scale);
        matrix.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));
        return scale;
    }

    public static float fitCenter(Matrix matrix, Bitmap bitmap, int reqWidth,
                                  int reqHeight) {
        //小的
        float scale;
        float dx = 0;
        float dy = 0;
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        float widthScale = 1f;
        float heightScale = 1f;
        //为了让小图居中显示，但加上，旋转效果不好
        if (bitmapWidth >= reqWidth || bitmapHeight >= reqHeight) {
            widthScale = (float) reqWidth / bitmapWidth;
            heightScale = (float) reqHeight / bitmapHeight;
        }
        scale = Math.min(widthScale, heightScale);
        if (widthScale == 1f && heightScale == 1f) {
            dx = (reqWidth - bitmapWidth * scale) * 0.5f;
            dy = (reqHeight - bitmapHeight * scale) * 0.5f;
        } else {
            if (widthScale > heightScale) {
                dx = (reqWidth - bitmapWidth * scale) * 0.5f;
            } else {
                dy = (reqHeight - bitmapHeight * scale) * 0.5f;
            }
        }
        matrix.setScale(scale, scale);
        matrix.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));
        //充满width
        return scale;
    }

    public static float centerCropY(Matrix matrix, Bitmap bitmap, int reqWidth,
                                    int reqHeight) {
        float dx = 0;
        float dy = 0;
        int dwidth = bitmap.getWidth();
        int dheight = bitmap.getHeight();
        float widthScale = (float) reqWidth / (float) dwidth;
        float heightScale = (float) reqHeight / (float) dheight;
        float centerCropScale = Math.max(widthScale, heightScale);
        dy = (reqHeight - dheight * centerCropScale) * 0.5f;
        dx = (reqWidth - dwidth * centerCropScale) * 0.5f;
        matrix.setScale(centerCropScale, centerCropScale);
        matrix.postTranslate((int) (dx), (int) (dy));
        return centerCropScale;
    }

    public static float center(Matrix matrix, Bitmap bitmap, int reqWidth, int reqHeight) {
        int bwidth = bitmap.getWidth();
        int bheight = bitmap.getHeight();
        int ox = (reqWidth - bwidth) / 2;
        int oy = (reqHeight - bheight) / 2;
        matrix.setScale(1, 1);
        matrix.postTranslate(ox, oy);
        return 1;
    }


    public static float getMatrixRotation(Matrix matrix) {
        for (int i = 0; i < sMatrixScaleValues.length; i++) {
            sMatrixScaleValues[i] = 0;
        }
        matrix.getValues(sMatrixScaleValues);
        float rAngle = Math.round(Math.atan2(sMatrixScaleValues[Matrix.MSKEW_X],
                sMatrixScaleValues[Matrix.MSCALE_X])
                * (180 / Math.PI));
        return rAngle;
    }
}
