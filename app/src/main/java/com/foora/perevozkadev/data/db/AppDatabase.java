package com.foora.perevozkadev.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.foora.perevozkadev.data.db.dao.FilterDao;
import com.foora.perevozkadev.data.db.model.FilterJson;

/**
 * Created by Alexandr.
 */
@Database(entities = {FilterJson.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FilterDao filterDao();

}
