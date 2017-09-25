package com.autism.tl.controlhelper.net;

import com.autism.tl.controlhelper.model.ClassInfo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by 唐亮 on 2017/8/7.
 */
public interface ClassInfoServer {
    @GET("class/{id}")
    Observable<List<ClassInfo>> getClassInfo(@Path("id") int id);
}
