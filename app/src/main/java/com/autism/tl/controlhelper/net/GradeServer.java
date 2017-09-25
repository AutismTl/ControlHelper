package com.autism.tl.controlhelper.net;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by 唐亮 on 2017/8/7.
 */
public interface GradeServer {
    @GET("grade")
    Observable<String> getClassInfo();
}
