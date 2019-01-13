package com.foora.perevozkadev.data.db;

import android.content.Context;

import com.foora.perevozkadev.data.db.model.FilterJson;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Alexandr.
 */
public class LocalRepoImpl extends BaseLocal implements LocalRepo{

    private Context context;

    public LocalRepoImpl(Context context) {
        this.context = context;
    }

    @Override
    public Flowable<List<FilterJson>> getFilters() {
        return getDatabase(context).filterDao().getFilters();
    }

    public void addFilterJson(FilterJson filterJson) {
        getDatabase(context).filterDao().insert(filterJson);
    }

    public void deleteFilterJson(FilterJson filterJson) {
        getDatabase(context).filterDao().delete(filterJson);
    }

    public void updateFilterJson(FilterJson filterJson) {
        getDatabase(context).filterDao().update(filterJson);
    }

}
