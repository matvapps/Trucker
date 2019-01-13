package com.foora.perevozkadev.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.foora.perevozkadev.data.db.model.FilterJson;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Alexandr.
 */
@Dao
public interface FilterDao {

    @Query("SELECT * FROM filter")
    Flowable<List<FilterJson>> getFilters();

    @Insert
    void insert(FilterJson filterJson);

    @Delete
    void delete(FilterJson filterJson);

    @Update
    void update(FilterJson filterJson);

    @Query("DELETE FROM filter")
    void clearTable();

}
