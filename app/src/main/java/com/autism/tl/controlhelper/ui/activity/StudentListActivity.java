package com.autism.tl.controlhelper.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.autism.tl.controlhelper.R;
import com.autism.tl.controlhelper.base.BaseActivity;
import com.autism.tl.controlhelper.db.DBHelper;
import com.autism.tl.controlhelper.model.ClassInfo;
import com.autism.tl.controlhelper.model.PostInfo;
import com.autism.tl.controlhelper.model.Respond;
import com.autism.tl.controlhelper.model.StudentInfo;
import com.autism.tl.controlhelper.net.HttpMethods;
import com.autism.tl.controlhelper.ui.adapter.StudentListAdapter;
import com.autism.tl.controlhelper.ui.view.DividerItemDecoration;
import com.autism.tl.controlhelper.utils.CommonUtils;
import com.autism.tl.controlhelper.utils.RxBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 唐亮 on 2017/8/14.
 */

public class StudentListActivity extends BaseActivity {

    @BindView(R.id.student_list)
    RecyclerView studentList;
    @BindView(R.id.name)
    EditText course;
    @BindView(R.id.input_achieve_root)
    LinearLayout root;
    @BindView(R.id.input_achieve)
    Button sure;
    private DBHelper mHelper = DBHelper.getDBHelper();
    private ProgressDialog progressDialog;
    private List<String> post=new ArrayList<String>();
    private int type;
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.act_studentlist;
    }

    @Override
    public void doBusiness(Context mContext) {
        ButterKnife.bind(this);
        ClassInfo classInfo=(ClassInfo) getIntent().getSerializableExtra("class_info");
        type=getIntent().getIntExtra("type",0);
        if(type==2){
            course.setVisibility(View.VISIBLE);
            //当course获得焦点时
            //输入法不覆盖按钮
            course.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        CommonUtils.getCommonUtils().addLayoutListener(root,sure);
                    } else {
                    }
                }
            });
        }
        getStudentInfo((int)classInfo.getId());
        getPostItem();
    }

    @OnClick(R.id.input_achieve)
    public void post() {
            if(TextUtils.isEmpty(course.getText())&&type==2){
                showToast("课程名未填写");
            }
            else{
                final EditText editText = new EditText(this);
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                editText.requestFocus();
                dialog.setTitle("请输入操作者姓名").setIcon(R.drawable.admin)
                        .setView(editText)
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Subscriber<Respond> subscriber = new Subscriber<Respond>() {
                                    @Override
                                    public void onCompleted() {
                                        showToast("加载完成");
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        e.printStackTrace();
                                    }

                                    @Override
                                    public void onNext(Respond s) {
                                        showToast("加载完成");
                                    }
                                };
                                if (CommonUtils.getCommonUtils().isNetworkAvailable(StudentListActivity.this)) {
                                    StringBuffer stringBuffer = new StringBuffer();
                                    Iterator<String> it = post.iterator();
                                    while (it.hasNext()) {
                                        stringBuffer.append(it.next());
                                        stringBuffer.append("|");
                                    }
                                    long time = System.currentTimeMillis();
                                    Date date = new Date(time);
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                    Log.e("time", "time2=" + format.format(date));
                                    SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");
                                    Log.e("time", "time4=" + format1.format(date));
                                    SimpleDateFormat format2 = new SimpleDateFormat("E");
                                    Log.e("time", "time6=" + format2.format(date));
                                    $Log(String.valueOf(type));
                                    String person = String.valueOf(editText.getText());
                                    String name=null;
                                    if(type==1){
                                        name=format2.format(date) + "早签未到";
                                    }else if(type==2){
                                        name= String.valueOf(course.getText());
                                    }
                                    $Log(person);
                                    $Log(name);
                                    HttpMethods.getInstance().postPunishInfo(subscriber, stringBuffer.toString(), type,
                                            name, person, format.format(date) + " " + format1.format(date));
                                    showToast("上传成功");
                                } else {
                                    showToast("上传失败 请检查网络连接");
                                }
                            }
                        })
                        .setNegativeButton("取消", null).show();
            }
        }

    private void getPostItem() {
        RxBus.getInstance().toObserverable(PostInfo.class)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PostInfo>() {
                    @Override
                    public void call(PostInfo postInfo) {
                        if(postInfo.getIs()){
                            post.add(postInfo.getId());
                        }else {
                            Iterator<String> it = post.iterator();
                            while(it.hasNext()){
                                String x = it.next();
                                if(x.equals(postInfo.getId())){
                                    it.remove();
                                }
                            }
                        }
                    }
                });
    }

    public void getStudentInfo(int id) {
        studentList.setNestedScrollingEnabled(false);
        if (mHelper.getStudentInfoSize(id) == 0) {
            questStudentInfo(id);
        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(StudentListActivity.this);
            studentList.setLayoutManager(layoutManager);
            studentList.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL_LIST));
            StudentListAdapter adapter = new StudentListAdapter(mHelper.getStudentInfo(id),mHelper.getImageList(id));
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
                LinearLayoutManager layoutManager = new LinearLayoutManager(StudentListActivity.this);
                studentList.setLayoutManager(layoutManager);
                studentList.addItemDecoration(new DividerItemDecoration(StudentListActivity.this,
                        DividerItemDecoration.VERTICAL_LIST));
                StudentListAdapter adapter = new StudentListAdapter(studentInfoList,mHelper.getImageList(studentInfoList));
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
