package com.foora.perevozkadev.ui.search_order;

import com.foora.perevozkadev.data.db.model.FilterJson;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.MvpView;

import java.util.List;

/**
 * Created by Alexandr.
 */
public interface SearchOrderMvpView extends MvpView {
    void onGetOrders(List<Order> orders);
    void onGetFilters(List<FilterJson> filters);
}
