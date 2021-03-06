package com.foora.perevozkadev.ui.profile;

import android.util.Log;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.network.model.BaseResponse;
import com.foora.perevozkadev.data.network.model.GetOrderResponse;
import com.foora.perevozkadev.data.network.model.TransportResponse;
import com.foora.perevozkadev.ui.base.BasePresenter;
import com.foora.perevozkadev.ui.profile.model.Profile;

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
public class ProfilePresenter<V extends ProfileMvpView> extends BasePresenter<V> implements ProfileMvpPresenter<V> {

    public static final String TAG = ProfilePresenter.class.getSimpleName();

    public ProfilePresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void getProfile() {
        if (!isViewAttached()) {
            Log.e(TAG, "getProfile: View isn't attach");
            return;
        }

        getMvpView().showLoading();

        Log.d(TAG, "token " + getDataManager().getUserToken());

        getDataManager().getProfile(getDataManager().getUserToken())
                .enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        getMvpView().hideLoading();

                        Log.d(TAG, "onResponse: getProfile end with code: " + response.code());

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            getMvpView().onGetProfile(response.body());
                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        getMvpView().hideLoading();
                        getMvpView().onError("Не удалось получить ваш профиль");
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });

    }

    @Override
    public void getMyOrders() {
        if (!isViewAttached()) {
            Log.e(TAG, "getMyOrders: view isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager()
                .getUserOrders(getDataManager().getUserToken())
                .enqueue(new Callback<GetOrderResponse>() {
                    @Override
                    public void onResponse(Call<GetOrderResponse> call, Response<GetOrderResponse> response) {
                        getMvpView().hideLoading();

                        Log.d(TAG, "onResponse: getUserOrders end with code: " + response.code());

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body().getOrders().toString());
                            getMvpView().onGetUserOrders(response.body().getOrders());
                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GetOrderResponse> call, Throwable t) {
                        getMvpView().hideLoading();
                        getMvpView().onError("Не удалось получить ваши заказы");
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });
    }

    @Override
    public void getMyTransport() {
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
                            getMvpView().onGetUserTransport(response.body().getTransports());
                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string() );
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
    public void verifyUser(File file) {
        if (!isViewAttached()) {
            Log.e(TAG, "View isn't attach");
            return;
        }

        MultipartBody.Part image;
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        image = MultipartBody.Part.createFormData("photo_1", file.getName(), reqFile);

        getMvpView().showLoading();

        getDataManager().verifyUser(getDataManager().getUserToken(), image)
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        getMvpView().hideLoading();

                        Log.d(TAG, "onResponse: end with code: " + response.code());

                        if (response.isSuccessful()) {
                            getMvpView().showMessage("Отправлено на верификацию");
                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        getMvpView().hideLoading();
                        getMvpView().onError("Не удалось верифицировать пользователя");
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });
    }

    @Override
    public void changeProfile(Profile profile) {
        if (!isViewAttached()) {
            Log.e(TAG, "changeProfile: View isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().changeProfile(getDataManager().getUserToken(), profile)
                .enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        getMvpView().hideLoading();

                        Log.d(TAG, "onResponse: changeProfile end with code: " + response.code());

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            getMvpView().onChangeProfile();
                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        getMvpView().hideLoading();
                        getMvpView().onError("Не удалось обновить профиль");
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });
    }

}
