package com.apkclass.specialview;

import java.text.SimpleDateFormat;  
import java.util.Date;

import android.R.color;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * 圆圈进度控件
 * @author 姜坤
 * Email:jiangkunwork@126.com
 */
public class RoundProgressView extends View{
	
	 private static final String TAG = "RoundProgressView";
	/**最外围的颜色值*/  
    private int mOutRoundColor = Color.argb(60, 255, 255, 255);  
    /**中心圆的颜色值*/  
    private int mCenterRoundColor = Color.argb(255, 255, 255, 255);  
      
    /**进度的颜色*/  
    private int mProgressRoundColor = Color.argb(255, 255, 255, 255);  
    /**进度的背景颜色*/  
    private int mProgressRoundBgColor = Color.argb(100, 255, 255, 255);  
    /**进度条的宽度*/  
    private int mProgressWidth = 5;  
      
    private int[] colors = {Color.WHITE,Color.RED};  
    /***字体颜色*/  
    private int mTextColor = Color.rgb(118, 181, 66);  
    private float mPencentTextSize = 65;  
      
    private int mWidth,mHeight;  
    private int mPaddingX;  
      
    private float mProgress = 0.5f;  
    private float mMax = 1.0f;  
    /** 
     * 时间显示格式 
     */  
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("HH:mm:ss.S");  
      
    private Paint mPaint = new Paint();  
  
    public RoundProgressView(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
        init();  
    }  
  
    public RoundProgressView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        init();  
    }  
  
    public RoundProgressView(Context context) {  
        super(context);  
        init();  
    }  
      
    public void init(){
    	
    }  
      
    @SuppressLint("DrawAllocation")  
    @Override  
    protected void onDraw(Canvas canvas) {  
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();
        
        Log.e(TAG, "mwidth="+mWidth+"mHeight="+mHeight);
          
        if(mWidth > mHeight){
            mPaddingX = (mWidth-mHeight)/2;
            mWidth = mHeight;  
        }
        
        if(mHeight > mWidth){
        	mPaddingX = (mHeight-mHeight)/2;
        	mHeight = mWidth;
        }
        
        //画椭圆
        mPaint.setAntiAlias(true); // 消除锯齿  
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(mOutRoundColor);  
        RectF oval = new RectF(new Rect(mPaddingX, 0, mWidth+mPaddingX, mHeight));  
        canvas.drawArc(oval, 0, 360, true, mPaint);  
          
        //画外圈圆
        int halfWidth = mWidth/6;  
        mPaint.setColor(mProgressRoundBgColor);  
        mPaint.setStrokeWidth(mProgressWidth);  
        mPaint.setStyle(Style.STROKE);  
        oval = new RectF(new Rect(halfWidth+mPaddingX, halfWidth, halfWidth*5+mPaddingX, halfWidth*5));  
        canvas.drawArc(oval, 0, 360, false, mPaint);  
          
        mPaint.setColor(mProgressRoundColor);  
        canvas.drawArc(oval, 90, 360*mProgress/mMax, false, mPaint);  
          
        //画白色圈
        halfWidth = mWidth/4;  
        mPaint.setStyle(Style.FILL);  
        mPaint.setColor(mCenterRoundColor);  
        oval = new RectF(new Rect(halfWidth+mPaddingX, halfWidth, halfWidth*3+mPaddingX, halfWidth*3));  
        canvas.drawArc(oval, 0, 360, false, mPaint);  
        
        
        mPaint.reset();  
        mPaint.setTextSize(mPencentTextSize);  
        mPaint.setColor(mTextColor);  
        mPaint.setStyle(Style.FILL);  
        mPaint.setTextAlign(Align.CENTER);  
        String number = (int)(mProgress*100/mMax)+"";  
        Log.e(TAG, "number="+ number);
        canvas.drawText(number, mWidth/2+mPaddingX, mHeight/2+mPencentTextSize/3, mPaint);  
          
        float textWidth = mPaint.measureText(number);  
        mPaint.setTextSize(mPencentTextSize/2);  
        canvas.drawText("%", mWidth/2+mPaddingX+textWidth/2+5, mHeight/2-mPencentTextSize/8, mPaint);  
          
        mPaint.setTextSize(mPencentTextSize/2);  
        canvas.drawText(mDateFormat.format(new Date(System.currentTimeMillis())), mWidth/2+mPaddingX, mHeight/2+halfWidth/4*3, mPaint);  
    }  
      
    public void setMax(float mMax) {  
        this.mMax = mMax;  
    }  
      
    public void setProgress(float mProgress) {  
        this.mProgress = mProgress;  
        invalidate();
    }  
      
    public float getMax() {  
        return mMax;  
    }  
      
    public float getProgress() {  
        return mProgress;  
    }  
      
}
