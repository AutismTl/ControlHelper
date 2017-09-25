package com.autism.tl.controlhelper.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autism.tl.controlhelper.R;
import com.autism.tl.controlhelper.base.BaseActivity;
import com.autism.tl.controlhelper.base.User;
import com.autism.tl.controlhelper.db.DBHelper;
import com.autism.tl.controlhelper.model.ClassInfo;
import com.autism.tl.controlhelper.net.HttpMethods;
import com.autism.tl.controlhelper.ui.adapter.ClassListAdapter;
import com.autism.tl.controlhelper.ui.view.DividerItemDecoration;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by 唐亮 on 2017/8/5.
 */
public class ClassListActivity extends BaseActivity {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fab)
    FloatingActionMenu fab;
    @BindView(R.id.fab_1)
    FloatingActionButton fab1;
    @BindView(R.id.fab_2)
    FloatingActionButton fab2;
    @BindView(R.id.fab_3)
    FloatingActionButton fab3;
    @BindView(R.id.fab_4)
    FloatingActionButton fab4;
    @BindView(R.id.class_list)
    RecyclerView classList;
    @BindView(R.id.title_menu)
    Button topImage;
    @BindView(R.id.title_text)
    TextView grade;
    private DBHelper mHelper = DBHelper.getDBHelper();
    private int currentGrade = User.getInstance().getGradeId();
    private ProgressDialog progressDialog;
    int type;

    @Override
    public void initParms(Bundle parms) {
     type=parms.getInt("type",-1);
     $Log(String.valueOf(type));
    }

    @Override
    public int bindLayout() {
        return R.layout.act_classlist;
    }

    @Override
    public void doBusiness(Context mContext) {
        ButterKnife.bind(this);
        setTop();
        setOnSwipeRefresh();
        getClassInfo(User.getInstance().getGradeId());
        setFab();
    }

    @OnClick({R.id.fab_1, R.id.fab_2, R.id.fab_3, R.id.fab_4})
    public void changeList(View view) {
        switch (view.getId()) {
            case R.id.fab_1:
                getClassInfo(GRADE1);
                currentGrade = GRADE1;
                grade.setText(String.valueOf(currentGrade) + "级");

                break;
            case R.id.fab_2:
                getClassInfo(GRADE2);
                currentGrade = GRADE2;
                grade.setText(String.valueOf(currentGrade) + "级");
                break;
            case R.id.fab_3:
                getClassInfo(GRADE3);
                currentGrade = GRADE3;
                grade.setText(String.valueOf(currentGrade) + "级");
                break;
            case R.id.fab_4:
                getClassInfo(GRADE4);
                currentGrade = GRADE4;
                grade.setText(String.valueOf(currentGrade) + "级");
                break;
        }
        fab.close(true);
    }

    private void setTop() {
        grade.setText(String.valueOf(currentGrade) + "级");
    }

    @SuppressLint("ResourceAsColor")
    private void setOnSwipeRefresh() {
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary, R.color.gold);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //更新事件
                questClassInfo(currentGrade);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setFab() {
        fab.setClosedOnTouchOutside(true);
        fab1.setLabelText(String.valueOf(GRADE1) + "级");
        fab2.setLabelText(String.valueOf(GRADE2) + "级");
        fab3.setLabelText(String.valueOf(GRADE3) + "级");
        fab4.setLabelText(String.valueOf(GRADE4) + "级");
    }

    public void getClassInfo(int id) {
        if (mHelper.getClassInfoSize(id) == 0) {
            questClassInfo(id);
        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(ClassListActivity.this);
            classList.setLayoutManager(layoutManager);
            classList.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL_LIST));
            ClassListAdapter adapter = new ClassListAdapter(mHelper.getClassInfo(id),type);
            classList.setAdapter(adapter);
        }
    }

    private void questClassInfo(final int id) {
        showProgressDialog();
        Subscriber<List<ClassInfo>> subscriber = new Subscriber<List<ClassInfo>>() {
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
            public void onNext(final List<ClassInfo> classInfoList) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mHelper.loadClassInfo(classInfoList, id);
                    }
                }).start();
                LinearLayoutManager layoutManager = new LinearLayoutManager(ClassListActivity.this);
                classList.setLayoutManager(layoutManager);
                classList.addItemDecoration(new DividerItemDecoration(ClassListActivity.this,
                        DividerItemDecoration.VERTICAL_LIST));
                ClassListAdapter adapter = new ClassListAdapter(classInfoList,type);
                classList.setAdapter(adapter);
                closeProgressDialog();
                showToast("加载完成");
            }

        };
        HttpMethods.getInstance().getClassInfo(subscriber, id);
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
