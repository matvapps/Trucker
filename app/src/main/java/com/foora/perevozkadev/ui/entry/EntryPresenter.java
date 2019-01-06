package com.foora.perevozkadev.ui.entry;

import android.util.Log;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.network.model.ActivateResponse;
import com.foora.perevozkadev.data.network.model.ConfirmLoginResponse;
import com.foora.perevozkadev.data.network.model.LoginResponse;
import com.foora.perevozkadev.data.network.model.RegisterResponse;
import com.foora.perevozkadev.ui.base.BasePresenter;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.Scheduler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr.
 */
public class EntryPresenter<V extends EntryMvpView> extends BasePresenter<V> implements EntryMvpPresenter<V> {

    public static final String TAG = EntryPresenter.class.getSimpleName();

    public EntryPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void onRegisterClick(String login, String password,
                                String phone, String userType, String group) {

        if (!isViewAttached())
            return;

/*        if (email == null || email.isEmpty()) {
            getMvpView().onError(R.string.empty_email);
            return;
        }*/
//        if (!CommonUtils.isEmailValid(email)) {
//            getMvpView().onError(R.string.invalid_email);
//            return;
//        }
        if (password == null || password.isEmpty()) {
            getMvpView().onError(R.string.empty_password);
            return;
        }
        getMvpView().showLoading();

        getDataManager().register(login, password, phone, userType, group)
                .enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (!isViewAttached())
                            return;
                        getMvpView().hideLoading();

                        Log.d(TAG, "onResponse: register end with code : " + response.code());

                        if (!response.isSuccessful() && response.body() != null) {

                            if (response.body().getPassword() != null) {
                                getMvpView().onError("Слишком слабый пароль");
                                return;
                            }

                            if (response.body().getUsername() != null) {
                                getMvpView().onError("Используйте другой логин");
                                return;
                            }


                            try {
                                getMvpView().onError("Ошибка регистрации");

                                Gson gson = new Gson();
                                RegisterResponse errorResponse = gson.fromJson(response.errorBody().string(), RegisterResponse.class);

                                Log.d(TAG, "onResponse: " + errorResponse.getListOfErrors().toString());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            if (response.body() != null) {
                                Log.d(TAG, "onResponse: " + response.body().getUserId());
                                getMvpView().onReceiveUserId(response.body().getUserId());
                                sendSms(login, phone);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        if (!isViewAttached())
                            return;

                        getMvpView().onError("Ошибка регистрации");

                        call.cancel();
                        getMvpView().hideLoading();
                        Log.d(TAG, "onFailure: " + t.getMessage());

                    }
                });
    }

    @Override
    public void onLoginClick(String login, String password) {
        if (!isViewAttached())
            return;

        getMvpView().showLoading();

        getDataManager().login(login, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (!isViewAttached())
                    return;
                getMvpView().hideLoading();

                Log.d(TAG, "onResponse: login end with code = " + response.code());

                if (!response.isSuccessful()) {
                    try {

                        Log.e(TAG, "onResponse: " + response.errorBody().string());
//                        Gson gson = new Gson();
//                        LoginResponse errorResponse = gson.fromJson(response.errorBody().string(), LoginResponse.class);
//                        Log.d(TAG, gson.toJson(errorResponse));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    if (response.body() == null) {
                        Log.e(TAG, "onResponse: body is null");
                    } else {
                        if (response.body().getPhone() != null) {
                            getMvpView().onConfirmAuth(response.body().getPhone());
                            Log.d(TAG, "onResponse: login: " + login + " phone: " + response.body().getPhone());
                            sendSms(login, response.body().getPhone());
                        }
                        else if (response.body().getToken() != null) {
                            getDataManager().setUserLogged(true);
                            getDataManager().setUserToken(response.body().getToken());
                            getMvpView().openMainActivity();
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (!isViewAttached())
                    return;

                call.cancel();
                getMvpView().hideLoading();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onConfirmLogin(String login, String password, String smsCode) {
        if (!isViewAttached())
            return;

        getMvpView().showLoading();

        getDataManager().confirmLogin(login, password, smsCode).enqueue(new Callback<ConfirmLoginResponse>() {
            @Override
            public void onResponse(Call<ConfirmLoginResponse> call, Response<ConfirmLoginResponse> response) {
                if (!isViewAttached())
                    return;

                getMvpView().hideLoading();

                Log.d(TAG, "onResponse: confirm login end with code = " + response.code());

                if (!response.isSuccessful()) {
                    try {

                        Log.e(TAG, "onResponse: " + response.errorBody().string() );

                        Gson gson = new Gson();
                        ConfirmLoginResponse errorResponse = gson.fromJson(response.errorBody().string(), ConfirmLoginResponse.class);
                        Log.e(TAG, gson.toJson(errorResponse));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    getDataManager().setUserLogged(true);
                    getDataManager().setUserToken(response.body().getToken());
                    getMvpView().openMainActivity();
                }
            }

            @Override
            public void onFailure(Call<ConfirmLoginResponse> call, Throwable t) {
                if (!isViewAttached())
                    return;

                call.cancel();
                getMvpView().hideLoading();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    @Override
    public void activate(int userId, String sms_code) {
        if (!isViewAttached())
            return;

        getMvpView().showLoading();

        getDataManager().activate(userId, sms_code)
                .enqueue(new Callback<ActivateResponse>() {
                    @Override
                    public void onResponse(Call<ActivateResponse> call, Response<ActivateResponse> response) {

                        getMvpView().hideLoading();

                        if (!response.isSuccessful()) {

                            try {
                                Log.d(TAG, "onResponse: " + response.errorBody().string());
//                                Gson gson = new Gson();
////                                ActivateResponse errorResponse = gson.fromJson(response.errorBody().string(), ActivateResponse.class);
////                                Log.d(TAG, gson.toJson(errorResponse));
//
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            getDataManager().setUserLogged(true);
                            getDataManager().setUserToken(response.body().getToken());
                            getMvpView().openMainActivity();

                        }
                    }

                    @Override
                    public void onFailure(Call<ActivateResponse> call, Throwable t) {
                        call.cancel();
                        getMvpView().hideLoading();
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });

    }

    @Override
    public void sendSms(String login, String phone) {
        if (!isViewAttached())
            return;

        Log.d(TAG, "sendSms: ");
        
        getDataManager().sendSms(login, phone).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    getMvpView().onError("Ошибка отправки СМС");
                    try {
                        Log.d(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                getMvpView().onError("Ошибка отправки СМС 2");
                // TODO: ERROR
                getMvpView().onSmsSend();
                Log.e(TAG, "onFailure: " + t.getMessage(), t.getCause());
            }
        });

    }

}
