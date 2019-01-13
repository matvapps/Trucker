package com.foora.perevozkadev.data.db;

import com.foora.perevozkadev.data.db.model.FilterJson;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Alexandr.
 */
public interface LocalRepo {

    Flowable<List<FilterJson>> getFilters();
    void addFilterJson(FilterJson filterJson);
    void deleteFilterJson(FilterJson filterJson);
    void updateFilterJson(FilterJson filterJson);

}
