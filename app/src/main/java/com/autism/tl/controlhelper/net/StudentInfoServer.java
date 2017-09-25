package com.autism.tl.controlhelper.net;

import com.autism.tl.controlhelper.model.StudentInfo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by 唐亮 on 2017/8/10.
 */
public interface StudentInfoServer {
    @GET("student/{id}")
    Observable<List<StudentInfo>> getStudentInfo(@Path("id") int id);

    @GET("all_student")
    Observable<List<StudentInfo>> getAllStudentInfo();
}
