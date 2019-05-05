package com.autism.tl.controlhelper.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.autism.tl.controlhelper.R;
import com.autism.tl.controlhelper.base.BaseActivity;
import com.autism.tl.controlhelper.model.PunishInfo;
import com.autism.tl.controlhelper.net.HttpMethods;
import com.autism.tl.controlhelper.ui.adapter.PunishListAdapter;
import com.autism.tl.controlhelper.ui.view.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by 唐亮 on 2017/8/19.
 */

public class PunishInfoActivity extends BaseActivity {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.punish_list)
    RecyclerView punishList;
    @BindView(R.id.title_text)
    TextView text;
    private int type;
    private int classid;
    private int id;
    private ProgressDialog progressDialog;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.act_punishinfo;
    }

    @Override
    public void doBusiness(Context mContext) {
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", -1);
        classid = getIntent().getIntExtra("classid", -1);
        id = getIntent().getIntExtra("id", -1);
        setOnSwipeRefresh();
        getPunishInfo();
    }

    @SuppressLint("ResourceAsColor")
    private void setOnSwipeRefresh() {
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary, R.color.gold);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //更新事件
                questPunishInfo(type, classid, id);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getPunishInfo() {
        switch (type) {
            case 1:
                text.setText("早签");
                break;
            case 2:
                text.setText("缺课");
                break;
            case 3:
                text.setText("晚归");
                break;
        }
        questPunishInfo(type, classid, id);
    }

    private void questPunishInfo(int type, int classid, int id) {
        showProgressDialog();
        Subscriber<List<PunishInfo>> subscriber = new Subscriber<List<PunishInfo>>() {
            @Override
            public void onCompleted() {
                showToast("加载完成");
            }

            @Override
            public void onError(Throwable e) {
                closeProgressDialog();
                showToast("暂无数据，请添加数据或检查网络连接");
            }

            @Override
            public void onNext(final List<PunishInfo> punishInfoList) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(PunishInfoActivity.this);
                punishList.setLayoutManager(layoutManager);
                punishList.addItemDecoration(new DividerItemDecoration(PunishInfoActivity.this,
                        DividerItemDecoration.VERTICAL_LIST));
                PunishListAdapter adapter = new PunishListAdapter(punishInfoList);
                punishList.setAdapter(adapter);
                closeProgressDialog();
                showToast("加载完成");
            }

        };
        HttpMethods.getInstance().getPunishInfo(subscriber, type, classid, id);
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
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
