package com.foora.perevozkadev.ui.map.track;

import android.util.Log;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.network.model.TrackResponse;
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
public class TrackPresenter<V extends TrackMvpView> extends BasePresenter<V> implements TrackMvpPresenter<V> {

    public static final String TAG = TrackPresenter.class.getSimpleName();

    public TrackPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void trackOrder(int orderId) {
        if (!isViewAttached()) {
            Log.e(TAG, "getEmployees: view isn't attach");
            return;
        }

        getMvpView().hideLoading();

        getDataManager().trackOrder(getDataManager().getUserToken(), orderId)
                .enqueue(new Callback<List<TrackResponse>>() {
                    @Override
                    public void onResponse(Call<List<TrackResponse>> call, Response<List<TrackResponse>> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {

//                            Log.d(TAG, "onResponse: " + response.body(());
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            if (response.body().size() > 0)
                                getMvpView().onTrackOrder(response.body().get(0).getLocation());
                            else
                                getMvpView().onError("Не удалось установить местоположение груза");

                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            getMvpView().onError("Не удалось установить местоположение груза");
                        }

                    }

                    @Override
                    public void onFailure(Call<List<TrackResponse>> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                        getMvpView().onError("Не удалось установить местополодение груза");
                    }
                });
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
