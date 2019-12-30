package com.zdd.autolibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.zdd.autolibrary.model.ImgRect;
import com.zdd.autolibrary.opencv.OpencvHelper;
import com.zdd.autolibrary.sdk.Auto;
import com.zdd.autolibrary.utils.FileUtil;
import com.zdd.autolibrary.utils.ShellUtil;
import java.io.File;
import java.io.FileInputStream;

/**
 * 模板的截图一定要从原图大小中截取，不能缩放
 * decodeResource取出来的图片大小跟所放的文件夹目录有关，drawable-xxhdpi。本地和resource的bitmap大小一定要对应上
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    private TextView tvResult;

    private static final String FILE_NAME="lbpcascade_frontalface.xml";

    private File mCascade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        imageView = (ImageView) findViewById(R.id.image_view);
        tvResult=(TextView)findViewById(R.id.tv_result);
        findViewById(R.id.show).setOnClickListener(this);
        findViewById(R.id.process).setOnClickListener(this);
        findViewById(R.id.click_test).setOnClickListener(this);

        mCascade=new File(getDir("cascade", Context.MODE_PRIVATE),FILE_NAME);
        FileUtil.copyAssetsToFile(this,FILE_NAME,mCascade.getAbsolutePath());


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Auto.click(255,246);
            }
        },2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Auto.click(100,100);
            }
        },2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Auto.startApp(TestActivity.this,"com.foreverht.newland.workplus");
            }
        },5000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Auto.finishApp("com.foreverht.newland.workplus");
            }
        },7000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Auto.startApp(TestActivity.this,"com.foreverht.newland.workplus");
            }
        },9000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Auto.screencap("/mnt/sdcard/test.png");



            }
        },3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream("/mnt/sdcard/test.png");
                    Bitmap bitmap  = BitmapFactory.decodeStream(fis);

                    Log.d("zdd","width="+bitmap.getWidth()+"    height="+bitmap.getHeight());
                    imageView.setImageBitmap(bitmap);

                    Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.test10);
                    ImgRect imgRect=OpencvHelper.matchBitmap(bitmap,bitmap2,Bitmap.Config.ARGB_8888,5);

                    Log.d("zdd",imgRect.toString());
                    Auto.click(imgRect.getCenterX(),imgRect.getCenterY());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },10*1000);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.show) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test9);
            imageView.setImageBitmap(bitmap);
        }else if(v.getId() == R.id.click_test){
            Toast.makeText(this,"按钮被点击",Toast.LENGTH_LONG).show();
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test9);
            Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.test10);
//            imageView.setImageBitmap(cvtBitmap(bitmap,Bitmap.Config.ARGB_8888));


//            imageView.setImageBitmap(matchBitmap(bitmap,bitmap2,Bitmap.Config.ARGB_8888,5));

//            imageView.setImageBitmap(txtAreaBitmap(bitmap,Bitmap.Config.ARGB_8888));

            ImgRect imgRect=OpencvHelper.matchBitmap(bitmap,bitmap2,Bitmap.Config.ARGB_8888,5);

            tvResult.setText(imgRect.toString());

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShellUtil.exitShell();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Toast.makeText(this,"你点击的屏幕位置为："+event.getX()+" * "+event.getY(),Toast.LENGTH_LONG);
        tvResult.setText("你点击的屏幕位置为："+event.getX()+" * "+event.getY());

        return super.onTouchEvent(event);
    }

}