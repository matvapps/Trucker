package com.foora.perevozkadev.ui.choose_transport;

import android.util.Log;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.network.model.OrderRequest;
import com.foora.perevozkadev.data.network.model.RequestBody;
import com.foora.perevozkadev.data.network.model.TransportResponse;
import com.foora.perevozkadev.ui.base.BasePresenter;

import java.io.IOException;

import io.reactivex.Scheduler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr.
 */
public class ChooseTransportPresenter<V extends ChooseTransportMvpView> extends BasePresenter<V> implements ChooseTransportMvpPresenter<V> {

    public static final String TAG = ChooseTransportPresenter.class.getSimpleName();

    public ChooseTransportPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void getTransport() {
        if (!isViewAttached()) {
            Log.e(TAG, "getMyTransport: view isn't attach");
            return;
        }

        getMvpView().showLoading();

        Log.d(TAG, "getMyTransport: " + getDataManager().getUserToken());

        getDataManager()
                .getUserTransport(getDataManager().getUserToken())
                .enqueue(new Callback<TransportResponse>() {
                    @Override
                    public void onResponse(Call<TransportResponse> call, Response<TransportResponse> response) {
                        getMvpView().hideLoading();

                        Log.d(TAG, "onResponse: getUserTransport end with code: " + response.code());

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            getMvpView().onGetUserTransports(response.body().getTransports());
//                            getMvpView().onGetUserTransport(response.body().getTransports());
                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<TransportResponse> call, Throwable t) {
                        getMvpView().hideLoading();
                        getMvpView().onError("Не удалось получить ваш транспорт");
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });

    }

    @Override
    public void sendRequest(int orderId, RequestBody requestBody) {
        if (!isViewAttached()) {
            Log.e(TAG, "sendRequest: view isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager()
                .sendRequest(getDataManager().getUserToken(), orderId, requestBody)
                .enqueue(new Callback<OrderRequest>() {
                    @Override
                    public void onResponse(Call<OrderRequest> call, Response<OrderRequest> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            getMvpView().onRequestSuccess();
                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<OrderRequest> call, Throwable t) {
                        getMvpView().hideLoading();
                        getMvpView().onError("Ошибка отправления запроса");
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });
    }
}
