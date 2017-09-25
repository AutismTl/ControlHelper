package com.autism.tl.controlhelper.ui.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.autism.tl.controlhelper.R;

/**
 * Created by 唐亮 on 2017/7/30.
 */
public class CommonTopLayout extends RelativeLayout{
    private Button button;
    public CommonTopLayout(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.common_top,this);
        button=(Button)findViewById(R.id.title_menu);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
    }
}
