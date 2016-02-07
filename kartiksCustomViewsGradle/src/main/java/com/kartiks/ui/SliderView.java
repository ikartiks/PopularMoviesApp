package com.kartiks.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class SliderView extends View {

	private OnReachedListener reachedListener;
	
	Paint paint=new Paint();
	RectF rect;
	//ImageView view;
	Bitmap bm;
	GestureDetector gdt;
	int x,finalWidth,finalHeight,bmwidth,bmheight;
	boolean isEnabled=true;
	
	Timer t;
	
	/*public SliderView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		rect=new Rect(0,100, 100, 120);
		bm=BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
	}*/
	
	public SliderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		//bm=BitmapFactory.decodeResource(getResources(), R.drawable.pointer);
		bmwidth=bm.getWidth();
		bmheight=bm.getHeight();
		//setLayerType(LAYER_TYPE_SOFTWARE, paint);
		setWillNotDraw(false);
	}
	
	/*
	public SliderView(Context context) {
		super(context);
		rect=new Rect(0,100, 100, 120);
		bm=BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
	}*/
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		setMeasuredDimension(widthMeasureSpec, bmheight);
	}
	
	public void setOnReachedListener(OnReachedListener onReachedListener){
		reachedListener=onReachedListener;
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected synchronized void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		//LoggerGeneral.e(" bmh ");
		//LoggerGeneral.e("getWidth() "+getWidth());
		if(finalWidth==0){
			finalWidth=getWidth();
			finalHeight=getHeight();
		}
			
		rect=new RectF(bmwidth/2,finalHeight-10, finalWidth-bmwidth/2, finalHeight);
		
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(3);
		//canvas.drawRect(rect, paint);
		canvas.drawRoundRect(rect, 10, 10, paint);
		canvas.drawBitmap(bm, x, finalHeight-bmheight, paint);
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//LoggerGeneral.e("in");
		
		x=(int)event.getX(); 
		switch (event.getAction()) {
		
			case MotionEvent.ACTION_DOWN:		
				if(reachedListener!=null&&x>=finalWidth-bmwidth&&!isEnabled){
					
					x=0;
					invalidate();
					t=new Timer();
					t.schedule(new MyTimer(), 2000);
					reachedListener.onLoggedOut();
				}
				break;
				
			case MotionEvent.ACTION_MOVE:
				 
				if(isEnabled){
					
					//LoggerGeneral.e("asd");
					//x=(int)event.getX();
					if(x>finalWidth-10)
						x=finalWidth-10;
					//invalidate();
					//requestLayout();
					invalidate();
					
					//LoggerGeneral.e(finalWidth+" "+x+" "+bmwidth+" "+(finalWidth-10-bmwidth));
					
					if(reachedListener!=null&&x>=finalWidth-bmwidth){
						
						x=finalWidth-10-bmwidth;
						isEnabled=false;
						reachedListener.onReached();
					}
					
				}
					
				break;					
			case MotionEvent.ACTION_UP:
				//invalidate();
				break;
			default:
				break;
		}
		return true;
	}
	
	public interface OnReachedListener{
		
		public void onReached();
		public void onLoggedOut();
	}
	
	public class MyTimer extends TimerTask{
		@Override
		public void run() {
			isEnabled=true;
		}
	}
	
}