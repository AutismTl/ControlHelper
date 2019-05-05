package com.autism.tl.controlhelper.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.autism.tl.controlhelper.R;
import com.autism.tl.controlhelper.base.BaseActivity;
import com.autism.tl.controlhelper.base.User;
import com.autism.tl.controlhelper.db.DBHelper;
import com.autism.tl.controlhelper.model.StudentInfo;
import com.autism.tl.controlhelper.net.HttpMethods;
import com.autism.tl.controlhelper.ui.adapter.StudentHomeAdapter;
import com.autism.tl.controlhelper.ui.view.DividerItemDecoration;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by 唐亮 on 2017/7/30.
 */
public class StudentHomeActivity extends BaseActivity {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.student_list)
    RecyclerView studentList;
    @BindView(R.id.fab)
    FloatingActionMenu fab;
    @BindView(R.id.title_menu)
    Button menu;
    @BindView(R.id.title_right)
    Button map;
    private DBHelper mHelper = DBHelper.getDBHelper();
    private ProgressDialog progressDialog;



    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.act_student_home;
    }

    @Override
    public void doBusiness(Context mContext) {
        ButterKnife.bind(this);
        //开启蒙板
        fab.setClosedOnTouchOutside(true);
        //配置TOP
        menu.setBackgroundResource(R.drawable.menu);
        map.setVisibility(View.VISIBLE);
        map.setBackgroundResource(R.drawable.map);

        setOnSwipeRefresh();
        setNavigationView();
        getStudentInfo(User.getInstance().getClassId());
    }
    @OnClick(R.id.title_right)
    public void openMap(){
        startActivity(MapActivtiy.class);
    }
    @OnClick({R.id.fab_1, R.id.fab_2, R.id.fab_3, R.id.fab_4,R.id.fab_5})
    public void getMoreInfo(View view) {
        Intent intent =new Intent(this, PunishInfoActivity.class);
        intent.putExtra("classid",0);
        intent.putExtra("id",User.getInstance().getStudentId());
        Log.w("dasdsa", String.valueOf(User.getInstance().getStudentId()));
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

    @OnClick(R.id.title_menu)
    public void drawerOpen() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @SuppressLint("ResourceAsColor")
    private void setOnSwipeRefresh() {
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary, R.color.gold);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                questStudentInfo(User.getInstance().getClassId());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //菜单点击逻辑处理
                switch (item.getItemId()) {
                    case R.id.nav_change_password:
                        showToast("暂无数据");
                        break;
                    case R.id.nav_change_admin:
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(StudentHomeActivity.this).edit();
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

    public void getStudentInfo(int id) {
        if (mHelper.getStudentInfoSize(id) == 0) {
            questStudentInfo(id);
        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(StudentHomeActivity.this);
            studentList.setLayoutManager(layoutManager);
            studentList.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL_LIST));
            StudentHomeAdapter adapter = new StudentHomeAdapter(mHelper.getStudentInfo(id),mHelper.getImageList(id));
            studentList.setAdapter(adapter);
        }
    }

    private void questStudentInfo(final int id) {
        showProgressDialog();
        Subscriber<List<StudentInfo>> subscriber = new Subscriber<List<StudentInfo>>() {
            @Override
            public void onCompleted() {
                showToast("加载完成");
            }

            @Override
            public void onError(Throwable e) {
                closeProgressDialog();
                showToast("加载失败，请检查网络连接");
            }

            @Override
            public void onNext(final List<StudentInfo> studentInfoList) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mHelper.loadStudentInfo(studentInfoList, id);
                    }
                }).start();
                LinearLayoutManager layoutManager = new LinearLayoutManager(StudentHomeActivity.this);
                studentList.setLayoutManager(layoutManager);
                StudentHomeAdapter adapter = new StudentHomeAdapter(studentInfoList,mHelper.getImageList(studentInfoList));
                studentList.setAdapter(adapter);
                closeProgressDialog();
                showToast("加载完成");
            }

        };
        HttpMethods.getInstance().getStudentInfo(subscriber, id);
    }
    //显示进度对话框
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    //关闭进度对话框
    public void closeProgressDialog() {
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }

}
