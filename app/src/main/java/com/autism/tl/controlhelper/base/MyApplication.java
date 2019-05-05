package com.autism.tl.controlhelper.base;

import android.app.Application;
import android.content.Context;

import com.autism.tl.greendao.gen.DaoMaster;
import com.autism.tl.greendao.gen.DaoSession;
import com.baidu.mapapi.SDKInitializer;

/**
 * Created by 唐亮 on 2017/8/6.
 */
public class MyApplication extends Application {
    private static Context mContext;
    public static DaoMaster daoMaster;
    public static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        mContext = getApplicationContext();
}

    public static Context getContext() {
        return mContext;
    }

    /**
     * 取得DaoMaster
     *
     * @param context
     * @return daoMaster
     */
    public static DaoMaster getDaoMaster(Context context) {
        DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, "tl-db", null);
        daoMaster = new DaoMaster(helper.getWritableDatabase());
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return daoSession
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }



}
