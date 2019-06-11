package com.foora.perevozkadev.ui.sos;

import android.util.Log;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.network.model.SosResponse;
import com.foora.perevozkadev.ui.base.BasePresenter;

import java.io.IOException;

import io.reactivex.Scheduler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr.
 */
public class SosPresenter<V extends SosMvpView> extends BasePresenter<V> implements SosMvpPresenter<V> {

    public static final String TAG = SosPresenter.class.getSimpleName();

    public SosPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void acceptSos(int sos_id) {
        if (!isViewAttached()) {
            Log.e(TAG, "view isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().acceptSos(getDataManager().getUserToken(), sos_id)
                .enqueue(new Callback<SosResponse>() {
                    @Override
                    public void onResponse(Call<SosResponse> call, Response<SosResponse> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body().toString());

                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<SosResponse> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });
    }
}
