package com.foora.perevozkadev.ui.add_order;

/**
 * Created by Alexandr.
 */
public interface FragmentToActivityCallbackListener {
    void onReceiveRoutes();

    void onReceiveCargoInfo();

    void onReceiveContactInfo();
}