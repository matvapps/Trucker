package com.foora.perevozkadev.ui.search_order;

import com.foora.perevozkadev.data.db.model.FilterJson;
import com.foora.perevozkadev.ui.base.MvpPresenter;

/**
 * Created by Alexandr.
 */
public interface SearchOrderMvpPresenter<V extends SearchOrderMvpView> extends MvpPresenter<V> {
    void getOrders(float weightFrom, float weightTo, float volumeFrom, float volumeTo);
    void addFilter(FilterJson filterJson);
    void deleteFilter(FilterJson filterJson);
    void updateFilter(FilterJson filterJson);
    void getFilters();
}
