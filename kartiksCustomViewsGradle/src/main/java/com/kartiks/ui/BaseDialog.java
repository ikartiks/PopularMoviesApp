package com.kartiks.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kartiks.sample.R;

/**
 * Created by kartik on 6/7/15.
 */
public class BaseDialog extends Dialog{

    Context context;

    BaseDialog dialog=this;
    static int bgResourceId;

    private BaseDialog(Context context) {
        super(context);
        this.context=context;
    }

    private BaseDialog(Context context, int theme) {
        super(context, theme);
        this.context=context;
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context=context;
    }

    public static BaseDialog createFullScreenBaseDialog(Context context, int style,int bgResourceId){

        BaseDialog.bgResourceId=bgResourceId;
        Activity activity= (Activity) context;
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        BaseDialog baseDialog = new BaseDialog(context, style);
        //baseDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // must be called b4 adding content
        baseDialog.getWindow().setLayout(width, height);

        //ShapeDrawable shapeDrawable=new ShapeDrawable(new RoundRectShape(0.5f,new RectF(0.5f,0.5f,0.5f,0.5f),0.5f));
        baseDialog.getWindow().setBackgroundDrawableResource(R.drawable.overlay);

        baseDialog.setContentView(R.layout.base_dialog);
        return baseDialog;
    }

    public void setContent(View v){
        RelativeLayout mainContainer= (RelativeLayout) findViewById(R.id.MainContainer);
        mainContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        LinearLayout container=(LinearLayout)findViewById(R.id.Container);
        container.setBackgroundResource(bgResourceId);
        container.addView(v,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
    }

}