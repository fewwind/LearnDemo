package com.lineprogressbutton.fewwind.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import java.io.IOException;

public class SecondActivity extends AppCompatActivity {


    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);

        mIv = (ImageView) findViewById(R.id.id_second_iv);

        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.launcher_img);


        mIv.setImageBitmap(bitmap);

        mIv.post(new Runnable() {
            @Override
            public void run() {
                Log.e("tag", mIv.getWidth() + "--" + mIv.getHeight() + "==" + bitmap.getWidth() + "--" + bitmap.getHeight());

                Bitmap resizeImage = ImageUtils.resizeImage(bitmap, mIv.getWidth(), mIv.getHeight(), true);
                String[] imageDigree = getImageDigree(Environment.getExternalStorageDirectory().getAbsolutePath() + "/git.jpg");
                Log.e("tag", imageDigree[0] + imageDigree[1] + "===" + resizeImage.getWidth() + "--" + resizeImage.getHeight());

                float rato1 = Integer.valueOf(imageDigree[0]) * 1.0f / Integer.valueOf(imageDigree[1]);
                float rato2= resizeImage.getWidth() * (1.0f) / resizeImage.getHeight();

                Log.e("tag", rato1 + "===" + rato2);
            }
        });
    }


    public static String[] getImageDigree(String path) {

        String[] wh = new String[2];
        ExifInterface exif = null;
        int digree = 0;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
            exif = null;
            return new String[]{" ", " "};
        }
        // 读取图片中相机方向信息

        String FImageLength = wh[0] = exif
                .getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
        String FImageWidth = wh[1] = exif
                .getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
        int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        return wh;
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SecondActivity.class);
        context.startActivity(intent);
    }
}
