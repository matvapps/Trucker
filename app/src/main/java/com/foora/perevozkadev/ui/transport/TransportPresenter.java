package com.foora.perevozkadev.ui.transport;

import android.util.Log;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.ui.base.BasePresenter;
import com.foora.perevozkadev.ui.my_transport.model.Transport;

import java.io.IOException;

import io.reactivex.Scheduler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr.
 */
public class TransportPresenter<V extends TransportMvpView> extends BasePresenter<V> implements TransportMvpPresenter<V> {

    public static final String TAG = TransportPresenter.class.getSimpleName();

    public TransportPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void getTransport(int id) {
        if (!isViewAttached()) {
            Log.e(TAG, "getTransport: view isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().getUserTransport(id, getDataManager().getUserToken())
                .enqueue(new Callback<Transport>() {
                    @Override
                    public void onResponse(Call<Transport> call, Response<Transport> response) {
                        getMvpView().hideLoading();
                        if (response.isSuccessful()) {

                            getMvpView().onGetTransport(response.body());

                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            getMvpView().onError("Не удалось получить данные о транспорте");

                        }
                    }

                    @Override
                    public void onFailure(Call<Transport> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                        getMvpView().onError("Не удалось получить данные о транспорте");
                    }
                });
    }
}
