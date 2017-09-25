package com.autism.tl.controlhelper.net;

import com.autism.tl.controlhelper.model.ClassInfo;
import com.autism.tl.controlhelper.model.PunishInfo;
import com.autism.tl.controlhelper.model.Respond;
import com.autism.tl.controlhelper.model.StudentInfo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 唐亮 on 2017/8/7.
 */
public class HttpMethods {
    private static final int DEFAULT_TIMEOUT = 5;
    public static final String BASE_URL = "http://www.mad-tg.cn/blog/";
    private static Retrofit retrofit;

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

            retrofit = new Retrofit.Builder()
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }
        retrofit.baseUrl();
        return retrofit;
    }

    /**
     * 在访问HttpMethods时创建单例
     */
    private static class singleHttpMethods {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }


    /**
     * 获取单例
     */
    public static HttpMethods getInstance() {
        return singleHttpMethods.INSTANCE;
    }

    /**
     * 获取班级列表信息
     */
    public void getClassInfo(Subscriber<List<ClassInfo>> subscriber, int id) {
        ClassInfoServer classInfoServer;
        classInfoServer = retrofit.create(ClassInfoServer.class);
        classInfoServer.getClassInfo(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取学生列表信息
     */
    public void getStudentInfo(Subscriber<List<StudentInfo>> subscriber, int id) {
        StudentInfoServer studentInfoServer;
        studentInfoServer = retrofit.create(StudentInfoServer.class);
        studentInfoServer.getStudentInfo(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取学生列表信息
     */
    public void getAllStudentInfo(Subscriber<List<StudentInfo>> subscriber) {
        StudentInfoServer studentInfoServer;
        studentInfoServer = retrofit.create(StudentInfoServer.class);
        studentInfoServer.getAllStudentInfo()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    /**
     * 获取惩罚信息
     */

    public void getPunishInfo(Subscriber<List<PunishInfo>> subscriber, int type, int classid, int id) {
        PunishInfoServer punishInfoServer;
        punishInfoServer = retrofit.create(PunishInfoServer.class);
        punishInfoServer.getPunishInfo(type, classid, id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /**
     * 提交惩罚信息
     */

    public void postPunishInfo(Subscriber<Respond> subscriber, String id, int type, String name, String person, String date) {
        PunishInfoServer punishInfoServer;
        punishInfoServer = retrofit.create(PunishInfoServer.class);
        punishInfoServer.postPunishInfo(id, type, name, person, date)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
