package com.autism.tl.controlhelper.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.autism.tl.controlhelper.R;
import com.autism.tl.controlhelper.base.BaseActivity;
import com.autism.tl.controlhelper.base.User;
import com.autism.tl.controlhelper.model.StudentInfo;
import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 唐亮 on 2017/8/12.
 */

public class StudentInfoActivity extends BaseActivity{

    @BindViews({R.id.t_name,R.id.t_sex,R.id.t_ss,R.id.t_qq,R.id.t_xh,R.id.t_s_number,R.id.t_p_number,R.id.t_address})
    List<TextView>mainInfo;
    @BindView(R.id.image)
    ImageView picture;
    @BindView(R.id.fab)
    FloatingActionMenu fab;
    int id;

    @Override
    public void initParms(Bundle parms) {
    }

    @Override
    public int bindLayout() {
        return R.layout.act_studentinfo;
    }

    @Override
    public void doBusiness(Context mContext) {
        ButterKnife.bind(this);
        setMainInfo();
    }

    @OnClick({R.id.fab_1, R.id.fab_2, R.id.fab_3, R.id.fab_4,R.id.fab_5})
    public void getMoreInfo(View view) {
        Intent intent =new Intent(this, PunishInfoActivity.class);
        intent.putExtra("classid",0);
        intent.putExtra("id",id);
        switch (view.getId()) {
            case R.id.fab_1:
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            case R.id.fab_2:
                intent.putExtra("type",2);
                startActivity(intent);
                break;
            case R.id.fab_3:
                intent.putExtra("type",3);
                startActivity(intent);
                break;
            case R.id.fab_4:

                break;
            case R.id.fab_5:
                showToast("暂无数据");
                break;
        }
        fab.close(true);
    }


    @OnClick({R.id.p_call,R.id.s_call})
    public void call(View view){
        switch (view.getId()){
            case R.id.s_call:
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + mainInfo.get(5).getText());
                intent.setData(data);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    showToast("请先授予该应用拨打电话权限");
                    return;
                }
                startActivity(intent);
                break;
            case R.id.p_call:
                Intent intent1 = new Intent(Intent.ACTION_CALL);
                Uri data1 = Uri.parse("tel:" + mainInfo.get(6).getText());
                intent1.setData(data1);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    showToast("请先授予该应用拨打电话权限");
                    return;
                }
                startActivity(intent1);
                break;
        }
    }

    private void setMainInfo() {
        if(User.getInstance().getGradeId()!=0){
            fab.setVisibility(View.VISIBLE);
        }
        StudentInfo studentInfo= (StudentInfo) getIntent().getSerializableExtra("student_info");
                        Glide
                                .with(StudentInfoActivity.this)
                                .load("http://"+studentInfo.getImageId())
                                .error(R.mipmap.ic_launcher)
                                .crossFade()
                                .into(picture);
                        mainInfo.get(0).setText(studentInfo.getName());
                        mainInfo.get(1).setText(studentInfo.getSex());
                        mainInfo.get(2).setText(studentInfo.getHome());
                        mainInfo.get(3).setText(studentInfo.getQq());
                        mainInfo.get(4).setText(String.valueOf(studentInfo.getId()));
                        mainInfo.get(5).setText(studentInfo.getNumber());
                        mainInfo.get(6).setText(studentInfo.getPhone());
                        mainInfo.get(7).setText(studentInfo.getAddress());
                        id=(int)studentInfo.getId();
    }
}
