package com.foora.perevozkadev.ui.search_order;

import android.util.Log;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.network.model.GetOrderResponse;
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
public class SearchOrderPresenter<V extends SearchOrderMvpView> extends BasePresenter<V> implements SearchOrderMvpPresenter<V> {

    public static final String TAG = SearchOrderPresenter.class.getSimpleName();

    public SearchOrderPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void getOrders() {
        if (!isViewAttached()) {
            Log.e(TAG, "getOrders: view isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager()
                .getOrders()
                .enqueue(new Callback<GetOrderResponse>() {
                    @Override
                    public void onResponse(Call<GetOrderResponse> call, Response<GetOrderResponse> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            getMvpView().onGetOrders(response.body().getOrders());
                        } else {
                            getMvpView().onError("Error getting orders");
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string() );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GetOrderResponse> call, Throwable t) {
                        getMvpView().hideLoading();
                        getMvpView().onError("Error getting orders");
                        Log.e(TAG, "onFailure: " + t.getMessage(), t.getCause());
                    }
                });
    }
}
