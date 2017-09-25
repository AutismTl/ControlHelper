package com.autism.tl.controlhelper.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.autism.tl.controlhelper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 唐亮 on 2017/9/25.
 */
public class RespondActivity extends Activity implements View.OnClickListener{

    private Button button;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.context)
    EditText text;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_respond);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        button=(Button)findViewById(R.id.submit);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                if(TextUtils.isEmpty(phone.getText())||TextUtils.isEmpty(text.getText())){
                    Toast.makeText(this,"信息不能为空",Toast.LENGTH_SHORT).show();
                }else
                Toast.makeText(this,"提交成功，谢谢您的支持",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
