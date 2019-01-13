package com.foora.perevozkadev.data.db;

import android.arch.persistence.room.Room;
import android.content.Context;

/**
 * Created by Alexandr.
 */
public class BaseLocal {

    private AppDatabase appDatabase;

    public AppDatabase getDatabase(Context context) {
        appDatabase =  Room.databaseBuilder(context,
                AppDatabase.class, "perevozka").build();
//        appDatabase = RoomAsset
//                .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "countries.db")
//                .build();
        return appDatabase;
    }

}
