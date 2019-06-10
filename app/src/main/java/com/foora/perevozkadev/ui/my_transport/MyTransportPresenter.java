package com.foora.perevozkadev.ui.my_transport;

import android.support.annotation.NonNull;
import android.util.Log;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.network.model.BaseResponse;
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
public class MyTransportPresenter<V extends MyTransportMvpView> extends BasePresenter<V> implements MyTransportMvpPresenter<V> {

    public static final String TAG = MyTransportPresenter.class.getSimpleName();

    public MyTransportPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void getUserTransport() {
        if (!isViewAttached()) {
            Log.e(TAG, "getUserTransport: view isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().getUserTransport(getDataManager().getUserToken())
                .enqueue(new Callback<TransportResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TransportResponse> call, @NonNull Response<TransportResponse> response) {
                        getMvpView().hideLoading();

                        Log.d(TAG, "onResponse: getUserTransport response code: " + response.code());

                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                Log.d(TAG, "onResponse: " + response.body().toString());
                                getMvpView().onGetTransports(response.body().getTransports());
                            } else
                                Log.e(TAG, "onResponse: body is null");
                        } else {

                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            getMvpView().onError("Не удалось получить ваш транспорт");

                        }

                    }

                    @Override
                    public void onFailure(Call<TransportResponse> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                        getMvpView().onError("Не удалось получить ваш транспорт");
                    }
                });

    }

    @Override
    public void addTransportToArchive(int transportId) {
        if (!isViewAttached()) {
            Log.e(TAG, "addTransportToArchive: view isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().addTransportToArchive(transportId, getDataManager().getUserToken())
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        getMvpView().hideLoading();

                        Log.d(TAG, "onResponse: addTransportToArchive response code: " + response.code());

                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                Log.d(TAG, "onResponse: " + response.body().toString());
                                getMvpView().onAddTransportToArchive(transportId);
                            } else
                                Log.e(TAG, "onResponse: body is null");
                        } else {

                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            getMvpView().onError("Не удалось добавить транспорт в архив");
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                        getMvpView().onError("Не удалось добавить транспорт в архив");
                    }
                });
    }

    @Override
    public void removeTransportFromArchive(int transportId) {
        if (!isViewAttached()) {
            Log.e(TAG, "removeTransportFromArchive: view isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().removeTransportFromArchive(transportId, getDataManager().getUserToken())
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        getMvpView().hideLoading();

                        Log.d(TAG, "onResponse: removeTransportFromArchive response code: " + response.code());

                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                Log.d(TAG, "onResponse: " + response.body().toString());
                                getMvpView().onRemoveTransportFromArchive(transportId);
                            } else
                                Log.e(TAG, "onResponse: body is null");
                        } else {

                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            getMvpView().onError("Не удалось удалить транспорт из архива");
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                        getMvpView().onError("Не удалось удалить транспорт из архива");
                    }
                });
    }

    @Override
    public void getTransportArchive() {
        if (!isViewAttached()) {
            Log.e(TAG, "getTransportArchive: view isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().getTransportArchive(getDataManager().getUserToken())
                .enqueue(new Callback<TransportResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TransportResponse> call, @NonNull Response<TransportResponse> response) {
                        getMvpView().hideLoading();

                        Log.d(TAG, "onResponse: getTransportArchive response code: " + response.code());

                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                Log.d(TAG, "onResponse: " + response.body().toString());
                                getMvpView().onGetTransportArchive(response.body().getTransports());
                            } else
                                Log.e(TAG, "onResponse: body is null");
                        } else {

                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            getMvpView().onError("Не удалось получить транспорт из архива");

                        }

                    }

                    @Override
                    public void onFailure(Call<TransportResponse> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                        getMvpView().onError("Не удалось получить транспорт из архива");
                    }
                });

    }
}
