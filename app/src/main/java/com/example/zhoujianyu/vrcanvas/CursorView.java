package com.example.zhoujianyu.vrcanvas;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Vector;

/**
 * TODO: document your custom view class.
 */
public class CursorView extends View {
    public static int ROW_NUM = 32;
    public static int COL_NUM = 16;
    public int capaData[][] = new int[ROW_NUM][COL_NUM];
    public int col_id[]=new int[]{3,5,7,9,11,13};
    public int row_id[]=new int[]{16,18,20,22,24};
    public int point_x=-1;
    public int point_y=-1;

    public int left_base = 100;
    public int top_base=300;
    public int side_length = 50;
    public int x_interval = 20;
    public int y_interavl = 100;

    public int curX =0 ;
    public int curY = 0;
    public int count=0;

    public float radius = 20;
    public boolean clear=false;

    public Paint p = new Paint();
    public Vector<Pair<Float,Float>> points=new Vector<>();

    public CursorView(Context context) {
        super(context);
    }
    public CursorView(Context context,AttributeSet attrs) {
        super(context,attrs);
        p.setColor(Color.YELLOW);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i = 0;i<points.size();i++) canvas.drawCircle(points.get(i).first,points.get(i).second,radius,p);

//        if(this.point_x==-1 && this.point_y==-1) return;
//        int dx = (point_x-3)*this.px;
//        int dy = (point_y-16)*this.py;
//
//        setBackgroundColor(Color.WHITE);
//        Paint p = new Paint();p.setColor(Color.RED);
//        int top = left_base+(curX-row_id[0])*(side_length+x_interval);
//        int left = top_base+(curY-col_id[0])*(side_length+y_interavl);
//        Log.e("bug",Integer.toString(curX)+","+Integer.toString(curY));
//        canvas.drawRect(new Rect(left,top,left+side_length,top+side_length),p);
//        for(int i = 0;i<row_id.length;i++){
//            for(int j = 0;j<col_id.length;j++){
//                top = left_base+(row_id[i]-row_id[0])*(side_length+x_interval);
//                left = top_base+(col_id[j]-col_id[0])*(side_length+y_interavl);
//                canvas.drawRect(new Rect(left,top,left+side_length,top+side_length),p);
//            }
//        }
    }

    public void updateData(short[] data){
//        for(int i = 0;i<ROW_NUM;i++){
//            for(int j = 0;j<COL_NUM;j++){
//                capaData
//            }
//        }
        curX = row_id[count/6];
        curY = col_id[count%6];
        count++; if(count>=30) count=0;
    }
}