package com.autism.tl.controlhelper.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.autism.tl.controlhelper.R;
import com.autism.tl.controlhelper.base.BaseActivity;
import com.autism.tl.controlhelper.db.DBHelper;
import com.autism.tl.controlhelper.model.StudentInfo;
import com.autism.tl.controlhelper.net.HttpMethods;
import com.autism.tl.controlhelper.ui.adapter.StudentHomeAdapter;
import com.autism.tl.controlhelper.ui.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by 唐亮 on 2017/12/17.
 */
public class SearchActivity extends BaseActivity{
    @BindView(R.id.student_list)
    RecyclerView studentList;
    @BindView(R.id.value)
    EditText value;
    @BindView(R.id.spinner)
    Spinner spinner;
    private DBHelper mHelper = DBHelper.getDBHelper();
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    private ProgressDialog progressDialog;
    private String key;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.act_search;
    }

    @Override
    public void doBusiness(Context mContext) {
        ButterKnife.bind(this);
        setSpinner();
        if (mHelper.getStudentInfoSize() == 0) {
            questAllStudentInfo();
        }
    }
    @OnClick(R.id.input_achieve)
    public void post() {
        studentList.setNestedScrollingEnabled(false);
        if(key==null|| TextUtils.isEmpty(value.getText())){
            showToast("搜索信息不能为空");
        }else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this);
            studentList.setLayoutManager(layoutManager);
            studentList.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL_LIST));
            Log.w("dasdasdasd",key+" "+value.getText().toString());
            Log.w("dasdasdasd",mHelper.searchStudent(key,value.getText().toString()).toString());
            StudentHomeAdapter adapter = new StudentHomeAdapter(mHelper.searchStudent(key,value.getText().toString()),mHelper.getImageList( mHelper.searchStudent(key,value.getText().toString())));
            studentList.setAdapter(adapter);
        }
    }
    private void setSpinner() {
        data_list = new ArrayList<String>();
        data_list.add("姓名");
        data_list.add("学号");
        data_list.add("QQ号");
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arr_adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                key=data_list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                key=null;
            }
        });
    }

    private void questAllStudentInfo() {
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
                        mHelper.loadAllStudentInfo(studentInfoList);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                closeProgressDialog();
                                showToast("加载完成");
                            }
                        });
                    }
                }).start();
            }
        };
        HttpMethods.getInstance().getAllStudentInfo(subscriber);
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
