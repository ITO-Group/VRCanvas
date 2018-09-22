package com.example.zhoujianyu.vrcanvas;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    public static final int ROW_NUM = 32;
    public static final int COL_NUM = 16;
    public static int screenWidth = 0;
    public static int screenHeight = 0;
    public static int capaWidth = 0;
    public static int capaHeight = 0;
    public int diff_data[][] = new int[ROW_NUM][COL_NUM];

    public CursorView mCursorView;
    public ImageView screenShotView;
    public long last_time=System.currentTimeMillis();

    View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Float touchX=new Float(event.getX());
            Float touchY=new Float(event.getY());
//            Toast.makeText(MainActivity.this, touchX.toString()+","+touchY.toString(), Toast.LENGTH_SHORT).show();
            if(event.getAction()==android.view.MotionEvent.ACTION_UP){
//                ((CursorView) v).touchX = 0;
//                ((CursorView) v).touchY = 0;
//                v.invalidate();
            }
            else{
                ((CursorView) v).points.add(new Pair<Float,Float>(touchX,touchY));
                v.invalidate();
            }
            return true;
        }
    };

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCursorView = findViewById(R.id.cursor_view);
        mCursorView.setOnTouchListener(mOnTouchListener);
        screenShotView = findViewById(R.id.imageView1);
        init();
        readDiffStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.item1){
            View view = this.getWindow().getDecorView();
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            Bitmap bitmap=Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
            screenShotView.setImageBitmap(bitmap);
            screenShotView.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){}
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            screenShotView.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }).start();
        }
        else if(item.getItemId()==R.id.item2){
            mCursorView.clear=true;
            mCursorView.points.clear();
            mCursorView.invalidate();
            mCursorView.clear=false;
        }
        return true;
    }
    public void init(){
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        capaWidth = screenWidth/COL_NUM;
        capaHeight = screenHeight/ROW_NUM;
    }

    /**
     * callback method after everytime native_lib.cpp read an image of capacity data
     * The function first convert
     * @param data: 32*16 short array
     */
    public void processDiff(short[] data) throws InterruptedException{
//        Log.e("bug",Long.toString(System.currentTimeMillis()-last_time));
//        if(System.currentTimeMillis()-last_time>=100) {
//            mCursorView.updateData(data);
//            mCursorView.invalidate();
//            last_time=System.currentTimeMillis();
//        }

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native void readDiffStart ();
    public native void readDiffStop ();
}
