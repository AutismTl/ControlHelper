package com.autism.tl.controlhelper.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autism.tl.controlhelper.R;
import com.autism.tl.controlhelper.base.BaseActivity;
import com.autism.tl.controlhelper.base.User;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 唐亮 on 2017/8/12.
 */
public class ParentsHomeActivity extends BaseActivity{
    @BindViews({R.id.t_name,R.id.t_sex,R.id.t_ss,R.id.t_qq,R.id.t_xh,R.id.t_s_number,R.id.t_p_number,R.id.t_address})
    List<TextView> mainInfo;
    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.fab)
    FloatingActionMenu fab;
    @BindView(R.id.snumber)
    TextView snumber;
    @BindView(R.id.pnumber)
    TextView pnumber;
    @BindView(R.id.title_menu)
    Button menu;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.act_parents_home;
    }

    @Override
    public void doBusiness(Context mContext) {
        ButterKnife.bind(this);
        //开启蒙板
        fab.setClosedOnTouchOutside(true);
        menu.setBackgroundResource(R.drawable.menu);
        setNavigationView();
        setMainInfo();
    }

    @OnClick({R.id.fab_1, R.id.fab_2, R.id.fab_3, R.id.fab_4,R.id.fab_5})
    public void getMoreInfo(View view) {
        Intent intent =new Intent(this, PunishInfoActivity.class);
        intent.putExtra("classid",0);
        intent.putExtra("id", User.getInstance().getStudentId());
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

    @OnClick(R.id.title_menu)
    public void drawerOpen() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void setNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //菜单点击逻辑处理
                switch (item.getItemId()) {
                    case R.id.nav_change_password:
                        break;
                    case R.id.nav_change_admin:
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ParentsHomeActivity.this).edit();
                        editor.putBoolean("autoLogin", false);
                        editor.commit();
                        User.getInstance().init();
                        startActivity(LoginActivity.class);
                        finish();
                        break;
                    case R.id.nav_update:
                        showToast("暂无更新");
                        break;
                    case R.id.nav_about:
                        startActivity(RespondActivity.class);
                        break;
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }
    private void setMainInfo() {
        pnumber.setText("副书记");
        snumber.setText("辅导员");
    }
}
