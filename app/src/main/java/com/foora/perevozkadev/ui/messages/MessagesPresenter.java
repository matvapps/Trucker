package com.foora.perevozkadev.ui.messages;

import android.util.Log;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.network.model.OrderRequest;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.BasePresenter;

import java.io.IOException;
import java.util.List;

import io.reactivex.Scheduler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr.
 */
public class MessagesPresenter<V extends MessagesMvpView> extends BasePresenter<V> implements MessagesMvpPresenter<V> {

    public static final String TAG = MessagesPresenter.class.getSimpleName();

    public MessagesPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void getUserRequests() {
        if (getMvpView() == null)
            Log.e(TAG, "getUserRequests: MessageMvpView not attached");

        getMvpView().showLoading();

        getDataManager()
                .getUserRequests(getDataManager().getUserToken())
                .enqueue(new Callback<List<OrderRequest>>() {
                    @Override
                    public void onResponse(Call<List<OrderRequest>> call, Response<List<OrderRequest>> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            getMvpView().onGetUserRequests(response.body());
                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<List<OrderRequest>> call, Throwable t) {
                        getMvpView().hideLoading();
                        getMvpView().onError("Не удалось получить запросы");
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });

    }

    @Override
    public void getOrderByRequest(OrderRequest orderRequest) {
        if (getMvpView() == null)
            Log.e(TAG, "getOrderByRequest: MessageMvpView not attached");

        getMvpView().showLoading();

        getDataManager()
                .getOrderById(getDataManager().getUserToken(), orderRequest.getOrderId())
                .enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            getMvpView().onGetOrderByRequest(orderRequest, response.body());
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
//                        getMvpView().onError("");
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });
    }
}
