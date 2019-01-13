package com.foora.perevozkadev;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.utils.AppLogger;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Alexandr
 */
public class PerevozkaApp extends Application{
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

}
