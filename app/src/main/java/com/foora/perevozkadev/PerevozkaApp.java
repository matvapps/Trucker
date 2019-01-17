package com.foora.perevozkadev;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.utils.AppLogger;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Alexandr
 */
public class PerevozkaApp extends MultiDexApplication {
    DataManager mDataManager;

//    @Inject
//    CalligraphyConfig mCalligraphyConfig;

    @Override
    public void onCreate() {
        super.onCreate();


        AppLogger.init();
        Fabric.with(this, new Crashlytics());

//        CalligraphyConfig.initDefault(mCalligraphyConfig);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
