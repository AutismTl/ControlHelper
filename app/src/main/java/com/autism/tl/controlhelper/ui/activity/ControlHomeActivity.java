package com.autism.tl.controlhelper.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.autism.tl.controlhelper.R;
import com.autism.tl.controlhelper.base.BaseActivity;
import com.autism.tl.controlhelper.base.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 唐亮 on 2017/8/1.
 */
public class ControlHomeActivity extends BaseActivity {

    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.title_menu)
    Button menu;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.act_control_home;
    }

    @Override
    public void doBusiness(Context mContext) {
        ButterKnife.bind(this);
        setNavigationView();
        menu.setBackgroundResource(R.drawable.menu);
    }


    @OnClick({R.id.zq,R.id.wg,R.id.qk,R.id.wj,R.id.check_change})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.zq:
                Bundle bundle1=new Bundle();
                bundle1.putInt("type",1);
                startActivity(ClassListActivity.class,bundle1);
                break;
            case R.id.wg:
                Bundle bundle3=new Bundle();
                bundle3.putInt("type",3);
                startActivity(NightBackActivity.class,bundle3);
                break;
            case R.id.qk:
                Bundle bundle2=new Bundle();
                bundle2.putInt("type",2);
                startActivity(ClassListActivity.class,bundle2);
                break;
            case R.id.wj:
                showToast("wj");
                break;
            case R.id.check_change:
                Bundle bundle0=new Bundle();
                bundle0.putInt("type",0);
                startActivity(ClassListActivity.class,bundle0);
                break;
        }
    }

    @OnClick(R.id.title_menu)
    public void drawerOpen(){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    private void setNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //菜单点击逻辑处理
                switch(item.getItemId()){
                    case R.id.nav_change_password:
                        break;
                    case R.id.nav_change_admin:
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ControlHomeActivity.this).edit();
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
}
