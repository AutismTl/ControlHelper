package com.autism.tl.controlhelper.net;

import com.autism.tl.controlhelper.model.PunishInfo;
import com.autism.tl.controlhelper.model.Respond;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by 唐亮 on 2017/8/16.
 */

public interface PunishInfoServer {

    //Post PunishInfo
    @FormUrlEncoded
    @POST("getinfo")
    Observable<Respond> postPunishInfo(@Field("id") String id, @Field("type") int type,
                                       @Field("name") String name, @Field("person") String person,
                                       @Field("date") String date);

    //Get  PunishList
    @GET("punish/{type}/{classid}/{id}")
    Observable<List<PunishInfo>> getPunishInfo(@Path("type") int type,@Path("classid") int classid,@Path("id") int id );
}
