package com.autism.tl.controlhelper.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.autism.tl.controlhelper.R;
import com.autism.tl.controlhelper.base.BaseActivity;
import com.autism.tl.controlhelper.base.User;
import com.autism.tl.controlhelper.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by 唐亮 on 2017/7/29.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.admin)
    EditText admin;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.remember)
    CheckBox remember;
    @BindView(R.id.auto_login)
    CheckBox autoLogin;
    @BindView(R.id.login_root)
    LinearLayout root;
    @BindView(R.id.login)
    Button login;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.act_login;
    }

    @Override
    public void doBusiness(Context mContext) {
        ButterKnife.bind(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //是否保存账号信息
        admin.setText(prefs.getString("admin", null));
        admin.requestFocus();
        admin.setSelection(admin.getText().length());
        password.setText(prefs.getString("password", null));
        //判断是否设置了自动登陆
        if (prefs.getBoolean("autoLogin", false)) {
            $Log(String.valueOf(admin.getText()));
            if(admin.getText().toString().equals(String.valueOf(1))){
                User.getInstance().setClassId(15050402);
                User.getInstance().setStudentId(1505040228);
                startActivity(StudentHomeActivity.class);
                finish();
            }else if(admin.getText().toString().equals(String.valueOf(2))){
                User.getInstance().setStudentId(1503040213);
                startActivity(ParentsHomeActivity.class);
                finish();
            }else if(admin.getText().toString().equals(String.valueOf(3))){
                User.getInstance().setGradeId(15);
                startActivity(ControlHomeActivity.class);
                finish();
            }
        }
        //当password获得焦点时
        //输入法不覆盖登陆按钮
        password.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    CommonUtils.getCommonUtils().addLayoutListener(root, login);
                } else {
                }
            }
        });
    }

    @OnClick(R.id.login)
    public void login(View view) {
        String madmin = admin.getText().toString();
        String mpassword = password.getText().toString();
        if (madmin.equals("1") && mpassword.equals("123")) {
            User.getInstance().setClassId(15050402);
            User.getInstance().setStudentId(1505040228);
            startActivity(StudentHomeActivity.class);
        } else if (madmin.equals("2") && mpassword.equals("123")) {
            User.getInstance().setStudentId(1503040213);
            startActivity(ParentsHomeActivity.class);
        } else if (madmin.equals("3") && mpassword.equals("123")){
            User.getInstance().setGradeId(15);
            startActivity(ControlHomeActivity.class);
        } else
            showToast("错误的账号或密码");
        //保存 是否自动登陆
        if (autoLogin.isChecked()) {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            editor.putBoolean("autoLogin", true);
            editor.commit();
        } else {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            editor.putBoolean("autoLogin", false);
            editor.commit();
        }
        //保存 账号信息
        if (remember.isChecked()) {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            editor.putString("password", String.valueOf(password.getText()));
            editor.putString("admin", String.valueOf(admin.getText()));
            editor.commit();
        } else {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            editor.putString("password", null);
            editor.putString("admin", null);
            editor.commit();
        }
        finish();
    }

    @OnCheckedChanged(R.id.remember)
    public void setRemember(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            editor.putString("password", String.valueOf(password.getText()));
            editor.putString("admin", String.valueOf(admin.getText()));
            editor.commit();
        } else {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            editor.putString("password", null);
            editor.putString("admin", null);
            editor.commit();
        }
    }

    @OnCheckedChanged(R.id.auto_login)
    public void setAutoLogin(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            editor.putBoolean("autoLogin", true);
            editor.commit();
        } else {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            editor.putBoolean("autoLogin", false);
            editor.commit();
        }
    }

}
