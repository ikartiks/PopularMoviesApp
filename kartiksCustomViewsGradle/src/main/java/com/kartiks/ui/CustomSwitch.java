package com.kartiks.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Switch;

import com.kartiks.sample.R;

public class CustomSwitch extends Switch {
	
	Context context;

	public CustomSwitch(Context context) {
		super(context);
		this.context = context;
		
	}

	public CustomSwitch(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTextView, 0, 0);

		try {
			String resourse = a.getString(R.styleable.CustomTextView_ttfResourseName);
			// LoggerGeneral.e("res "+resourse);
			if (resourse != null&&!isInEditMode()) {
				Typeface tf = Typeface.createFromAsset(context.getAssets(), resourse);
				setTypeface(tf);
				setSwitchTypeface(tf);
			}
			

		} finally {
			a.recycle();
		}
	}

	public CustomSwitch(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTextView, 0, 0);
		
		try {
			String resourse = a.getString(R.styleable.CustomTextView_ttfResourseName);
			// LoggerGeneral.e("res "+resourse);
			if (resourse != null&&!isInEditMode()) {
				Typeface tf = Typeface.createFromAsset(context.getAssets(), resourse);
				setTypeface(tf);
				setSwitchTypeface(tf);
			}
			

		} finally {
			a.recycle();
		}
	}

	public void setCustomTypeFace(String path) {

		if (path != null) {
			Typeface tf = Typeface.createFromAsset(context.getAssets(), path);
			setTypeface(tf);
			setSwitchTypeface(tf);
		}
		invalidate();
		requestLayout();
	}

}
