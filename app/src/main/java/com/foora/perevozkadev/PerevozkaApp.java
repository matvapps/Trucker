package com.foora.perevozkadev;

import android.app.Application;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.utils.AppLogger;

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

//        CalligraphyConfig.initDefault(mCalligraphyConfig);
    }

}
