package com.foora.perevozkadev.ui.my_orders;

import android.util.Log;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.network.model.GetOrderResponse;
import com.foora.perevozkadev.ui.base.BasePresenter;

import java.io.IOException;

import io.reactivex.Scheduler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr.
 */
public class MyOrdersPresenter<V extends MyOrdersMvpView> extends BasePresenter<V> implements MyOrdersMvpPresenter<V> {

    public static final String TAG = MyOrdersPresenter.class.getSimpleName();

    public MyOrdersPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void getUserOrders() {
        if (!isViewAttached()) {
            Log.e(TAG, "getUserOrders: view isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().getUserOrders(getDataManager().getUserToken())
                .enqueue(new Callback<GetOrderResponse>() {
                    @Override
                    public void onResponse(Call<GetOrderResponse> call, Response<GetOrderResponse> response) {
                        getMvpView().hideLoading();

                        Log.d(TAG, "onResponse: getUserOrders response code: " + response.code());

                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                Log.d(TAG, "onResponse: " + response.body().toString());
                                getMvpView().onGetUserOrders(response.body().getOrders());
                            } else
                                Log.e(TAG, "onResponse: body is null");
                        } else {

                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            getMvpView().onError("Не удалось получить ваши заказы");

                        }
                    }

                    @Override
                    public void onFailure(Call<GetOrderResponse> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                        getMvpView().onError("Не удалось получить ваши заказы");
                    }
                });
    }
}
