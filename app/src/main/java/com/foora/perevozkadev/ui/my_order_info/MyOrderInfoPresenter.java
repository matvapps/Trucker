package com.foora.perevozkadev.ui.my_order_info;

import android.util.Log;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.BasePresenter;

import java.io.IOException;

import io.reactivex.Scheduler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr.
 */
public class MyOrderInfoPresenter<V extends MyOrderInfoMvpView> extends BasePresenter<V> implements MyOrderInfoMvpPresenter<V> {

    public static final String TAG = MyOrderInfoPresenter.class.getSimpleName();

    public MyOrderInfoPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void getOrderById(int id) {
        if (!isViewAttached()) {
            Log.e(TAG, "getOrderById: view isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().getOrderById(getDataManager().getUserToken(), id)
                .enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            getMvpView().onGetOrder(response.body());
                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });

    }
}
