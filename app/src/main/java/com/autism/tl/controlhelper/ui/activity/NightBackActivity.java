package com.autism.tl.controlhelper.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.autism.tl.controlhelper.R;
import com.autism.tl.controlhelper.base.BaseActivity;
import com.autism.tl.controlhelper.db.DBHelper;
import com.autism.tl.controlhelper.model.PostInfo;
import com.autism.tl.controlhelper.model.Respond;
import com.autism.tl.controlhelper.model.StudentInfo;
import com.autism.tl.controlhelper.net.HttpMethods;
import com.autism.tl.controlhelper.ui.adapter.StudentListAdapter;
import com.autism.tl.controlhelper.ui.view.AutoEditView;
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
 * Created by 唐亮 on 2017/8/21.
 */

public class NightBackActivity extends BaseActivity {

    @BindView(R.id.icv)
    AutoEditView icv;
    @BindView(R.id.student_list)
    RecyclerView studentList;
    @BindView(R.id.input_achieve)
    Button sure;
    private DBHelper mHelper = DBHelper.getDBHelper();
    private ProgressDialog progressDialog;
    private List<String> post=new ArrayList<String>();
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.act_nightback;
    }

    @Override
    public void doBusiness(Context mContext) {
        ButterKnife.bind(this);
        if (mHelper.getStudentInfoSize() == 0) {
            questAllStudentInfo();
        }
        icv.setInputCompleteListener(new AutoEditView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                if(icv.getTextContent().length()==5){
                    //如果输入法打开则关闭，如果没打开则打开
                    InputMethodManager m=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    getAllStudentInfo(mHelper.getDormitory(icv.getTextContent()));
                }
            }

            @Override
            public void deleteContent() {
                Log.i("icv_delete", icv.getTextContent());
            }
        });
        getPostItem();
    }

   @OnClick(R.id.delete)
    public void deleteAll(){
        icv.clearAllText();
       //如果输入法打开则关闭，如果没打开则打开
       InputMethodManager m=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
       m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
   }
    @OnClick(R.id.input_achieve)
    public void post() {
        if (studentList.getChildCount()!=0) {
            final EditText editText = new EditText(this);
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            editText.requestFocus();
            dialog.setTitle("请输入操作者姓名").setIcon(R.drawable.alertdialog)
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
                            if (CommonUtils.getCommonUtils().isNetworkAvailable(NightBackActivity.this)) {
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
                                $Log(String.valueOf(3));
                                String person = String.valueOf(editText.getText());
                                String name = format2.format(date) + "晚归";
                                HttpMethods.getInstance().postPunishInfo(subscriber, stringBuffer.toString(), 3,
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

    public void getAllStudentInfo(List<StudentInfo> dorm) {
        studentList.setNestedScrollingEnabled(false);
        if (mHelper.getStudentInfoSize() == 0) {
            questAllStudentInfo();
        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(NightBackActivity.this);
            studentList.setLayoutManager(layoutManager);
            studentList.addItemDecoration(new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL_LIST));
            StudentListAdapter adapter = new StudentListAdapter(dorm,mHelper.getImageList(dorm));
            studentList.setAdapter(adapter);
        }
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
