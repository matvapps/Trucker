package com.foora.perevozkadev.ui.add_transport;

import android.util.Log;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.network.model.BaseResponse;
import com.foora.perevozkadev.ui.base.BasePresenter;
import com.foora.perevozkadev.ui.my_transport.model.Transport;

import java.io.File;
import java.io.IOException;

import io.reactivex.Scheduler;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr.
 */
public class AddTransportPresenter<V extends AddTransportMvpView> extends BasePresenter<V> implements AddTransportMvpPresenter<V> {

    public static final String TAG = AddTransportPresenter.class.getSimpleName();

    public AddTransportPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void addTransport(Transport transport) {
        if (!isViewAttached()) {
            Log.e(TAG, "addTransport: view isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().addTransport(getDataManager().getUserToken(), transport)
                .enqueue(new Callback<Transport>() {
                    @Override
                    public void onResponse(Call<Transport> call, Response<Transport> response) {
                        getMvpView().hideLoading();

                        Log.d(TAG, "onResponse: " + response.code());

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: received: " + response.body().toString());
                            getMvpView().onAddTransport(response.body());
                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            getMvpView().showErrorMessage("Не удалось добавить новый транспорт");
//                            getMvpView().onError("Не удалось добавить новый транспорт");
                        }
                    }

                    @Override
                    public void onFailure(Call<Transport> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                        getMvpView().showErrorMessage("Не удалось добавить новый транспорт");
                    }
                });

    }

    @Override
    public void addPhotoToTransport(File file, int transportId) {
        if (!isViewAttached()) {
            Log.e(TAG, "addPhotoToTransport: view isn't attach");
            return;
        }

        MultipartBody.Part image;
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        image = MultipartBody.Part.createFormData("img", file.getName(), reqFile);

        getMvpView().showLoading();

        getDataManager().addTransportPhoto(transportId,
                getDataManager().getUserToken(),
                image).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                getMvpView().hideLoading();

                if (response.isSuccessful()) {
//                    getMvpView().showMessage("Фото " + file.getName() + " добавлено");
                    getMvpView().onAddPhoto();
                } else {
                    try {
                        Log.e(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    getMvpView().onError("Ошибка добавления фото: " + file.getName());
                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                getMvpView().hideLoading();
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                getMvpView().onError("Ошибка добавления фото: " + file.getName());
            }
        });

    }

    @Override
    public void changeTransport(int transportId, Transport transport) {
        if (!isViewAttached()) {
            Log.e(TAG, "changeTransport(): view isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().changeTransport(transportId, getDataManager().getUserToken(), transport)
                .enqueue(new Callback<Transport>() {
                    @Override
                    public void onResponse(Call<Transport> call, Response<Transport> response) {
                        getMvpView().hideLoading();

                        Log.d(TAG, "onResponse: " + response.code());

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: received: " + response.body().toString());
                            getMvpView().onChangeTransport(response.body());
                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            getMvpView().showErrorMessage("Не удалось редактировать транспорт");
//                            getMvpView().onError("Не удалось редактировать транспорт");
                        }
                    }

                    @Override
                    public void onFailure(Call<Transport> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                        getMvpView().showErrorMessage("Не удалось редактировать транспорт");
                    }
                });
    }
}
