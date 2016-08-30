package com.lineprogressbutton.fewwind.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import com.lineprogressbutton.fewwind.myapplication.base.BaseActivity;
import com.lineprogressbutton.fewwind.myapplication.test.EnumTest;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends BaseActivity {

    private static final String TAG = "tag";
    LineProgressButton mLineProgressBar;
    private float i = 0.0f;

    AppCompatSeekBar sb1;
    AppCompatSeekBar sb2;
    int alph1 = 30;
    int alph2 = 60;

    GifImageView gifImageView;
    TextView tv;
    JCVideoPlayerStandard videoPlayerStandard;
    FloatingActionButton fab;

    @Bind(R.id.id_main_pb)
    ProgressBar mPb;

    @Bind(R.id.id_btn_circle)
    Button mCircle;

    @Bind(R.id.id_iv_0)
    ImageView iv0;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        videoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.custom_videoplayer_standard);
        sb1 = (AppCompatSeekBar) findViewById(R.id.id_sb1);
        sb2 = (AppCompatSeekBar) findViewById(R.id.id_sb2);
        iv = (ImageView) findViewById(R.id.id_iv);
        tv = (TextView) findViewById(R.id.id_tv);
        tv.setText("图片1: " + alph1 + "   图片2： " + alph2);
        gifImageView = (GifImageView) findViewById(R.id.gifImageView);

        mCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CircularAnim.startActivityAsCircular(MainActivity.this, SecondActivity.class, mCircle, R.color.colorAccent);
            }
        });


        long timeZone = TimeUnit.HOURS.convert(TimeZone.getDefault().getRawOffset(), TimeUnit.MILLISECONDS);
        Drawable progressDrawable = mPb.getProgressDrawable();
//        Drawable drawable = DrawableUtils.tintDrawable(progressDrawable, ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Resources.getSystem().getDisplayMetrics().heightPixels - 750);

        iv.setLayoutParams(params);
        iv0.setLayoutParams(params);
        tv.setText("图片1: " + alph1 + "   图片2： " + alph2);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveImage();
//                CircularAnim.startActivityAsCircular(MainActivity.this, SecondActivity.class, fab, R.color.colorAccent);
//                SecondActivity.startActivity(view.getContext());
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mLineProgressBar = (LineProgressButton) findViewById(R.id.id_line_progerss_bar);

        mLineProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLineProgressBar.post(loadTask);


            }
        });

//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.git);
        File gifFile = new File(Environment.getExternalStorageDirectory(), "git.gif");
        try {
            GifDrawable gifFromFile = new GifDrawable(gifFile);
            Bitmap bitmap = BitmapFactory.decodeFile(gifFile.getAbsolutePath());
//            gifImageView.setImageBitmap(bitmap);
            gifImageView.setImageDrawable(gifFromFile);
        } catch (IOException e) {
            e.printStackTrace();
        }


//            gifImageView.setImageDrawable(gifFromFile);

        sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alph1 = progress;
                tv.setText("图片1: " + alph1 + "   图片2： " + alph2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alph2 = progress;
                tv.setText("图片1: " + alph1 + "   图片2： " + alph2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        saveImage();

    }


    @Override protected int initLayoutId() {
        return R.layout.activity_main;
    }


    private void setVideo() {
        File file = new File(Environment.getExternalStorageDirectory(), "video.mp4");
        videoPlayerStandard.setUp(file.getAbsolutePath(), "haha");
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayerStandard.release();
    }

    public void checkPermisson() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) {

        } else {

        }
    }

    Runnable loadTask = new Runnable() {
        @Override
        public void run() {

            if (i <= 1.0) {
                mLineProgressBar.postDelayed(loadTask, 200);
                i += 0.05f;
                mLineProgressBar.setPrecent(i, "下载中 " + (int) (i * 100) + "%");
            } else {
                mLineProgressBar.setContext("下载完成");

            }
        }
    };

    Canvas canvas;

    private void showDialog() {
        AlertDialog mDialog = new AlertDialog.Builder(this).setTitle("title")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();


        mDialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_bg);
    }

    public void saveImage() {
        View parent = (View) tv.getParent();
//        parent.scrollBy(20,0);

        iv.post(new Runnable() {
            @Override
            public void run() {
                float x = 4 / 3f;
//                Log.e(TAG, "Runnable" + iv.getMeasuredHeight() + "高==宽  ：" + iv.getWidth());
                int value = (int) (1080 * x);
                Log.e(TAG, "Runnable" + 1080 * x + "高==宽  ：" + value);
            }
        });

        Bitmap bitmap1 = Util.getTransparentBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.test3), alph1)
                .copy(Bitmap.Config.ARGB_8888, true);
//        Bitmap bitmap3 = resizeImage(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), true);
//
//        Canvas convas2 = new Canvas(bitmap3);
//        convas2.drawBitmap(bitmap1, new Matrix(), null);


        Bitmap temp = BitmapFactory.decodeResource(getResources(), R.drawable.launcher_img);

        Log.e(TAG, bitmap1.getWidth() + "==" + bitmap1.getHeight());
//        Bitmap bitmap = resizeImage(temp, bitmap1.getWidth(), bitmap1.getHeight());
        Bitmap bitmap = resizeImage(temp, bitmap1.getWidth(), bitmap1.getHeight(), false);

        Bitmap bitmap2 = Util.getTransparentBitmap(bitmap, alph2);

//        convas2.drawBitmap(bitmap2, 0, 0, null);
//        Bitmap bitmap2 = Util.getTransparentBitmap(Bitmap.createScaledBitmap(temp,iv.getWidth(),iv.getHeight(),false), alph2);
//                .copy(Bitmap.Config.ARGB_8888, true);

        canvas = new Canvas(bitmap1);

//        WatermarkUtil.mergeBitmap(canvas, bitmap2);

//        iv.setImageBitmap(bitmap1);
//        iv0.setImageBitmap(bitmap2);
        iv.setAlpha(alph1);
        iv0.setAlpha(alph2);
    }

    public void caluSize(int wid) {

    }

    @OnClick(R.id.id_iv_0)
    public void clickIv0(View view) {

        showDialog();
        int random = new Random().nextInt(3) + 1;
        EnumTest test;
        if (random % 3 == 0) {
            test = new EnumTest(0, EnumTest.FirstEnum.APK_PLUGIN);
        } else if (random % 2 == 0) {
            test = new EnumTest(0, EnumTest.FirstEnum.EMBED);
        } else {
            test = new EnumTest(0, EnumTest.FirstEnum.ZIP);
        }

        switch (test.firstEnum) {
            case APK_PLUGIN:
                Log.i("tag", random + "--APK_PLUGIN");
                break;
            case EMBED:
                Log.i("tag", random + "--EMBED");
                break;
            case ZIP:
                Log.i("tag", random + "--ZIP");
                break;
        }
    }
//    @OnClick(R.id.id_iv)
//    public void clickIv1(View view){
//        Log.i("tag","上边的的==点击了");
//    }


    public static Bitmap resizeImage(Bitmap bitmap, int w, int h, boolean flag) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);

        if (flag) {
            return Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                    height, matrix, true);
        } else {
            return Bitmap.createBitmap(BitmapOrg, (width - newWidth) / 2, (height - newHeight) / 2, w,
                    h);
        }

    }

    public void startCarmea() {
        Intent intentCamera = new Intent();
        final Intent intent_camera = getPackageManager()
                .getLaunchIntentForPackage("com.android.camera");
        if (intent_camera != null) {
            intentCamera.setPackage("com.android.camera");
        }
        intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intentCamera);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
