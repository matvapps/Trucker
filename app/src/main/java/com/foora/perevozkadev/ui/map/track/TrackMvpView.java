package com.foora.perevozkadev.ui.map.track;

import com.foora.perevozkadev.data.network.model.Location;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.MvpView;

/**
 * Created by Alexandr.
 */
public interface TrackMvpView extends MvpView {

    void onTrackOrder(Location location);
    void onGetOrder(Order order);

}
