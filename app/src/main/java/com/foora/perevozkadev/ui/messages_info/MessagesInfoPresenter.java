package com.foora.perevozkadev.ui.messages_info;

import android.util.Log;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.network.model.OrderRequest;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.BasePresenter;
import com.foora.perevozkadev.ui.my_transport.model.Transport;
import com.foora.perevozkadev.ui.profile.model.Profile;

import java.io.IOException;

import io.reactivex.Scheduler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr.
 */
public class MessagesInfoPresenter<V extends MessagesInfoMvpView> extends BasePresenter<V> implements MessagesInfoMvpPresenter<V> {

    public static final String TAG = MessagesInfoPresenter.class.getSimpleName();

    public MessagesInfoPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void getRequestInfo(int requestId) {
        if (!isViewAttached()) {
            Log.e(TAG, "getRequestInfo: View isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager()
                .getRequestInfo(getDataManager().getUserToken(), requestId)
                .enqueue(new Callback<OrderRequest>() {
                    @Override
                    public void onResponse(Call<OrderRequest> call, Response<OrderRequest> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            getMvpView().onGetRequestInfo(response.body());
                        } else {
                            try {
                                Log.e(TAG, "onResponse:  " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<OrderRequest> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });

    }

    @Override
    public void getTransport(int transportId) {
        if (!isViewAttached()) {
            Log.e(TAG, "getTransport: View isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().getUserTransport(transportId, getDataManager().getUserToken())
                .enqueue(new Callback<Transport>() {
                    @Override
                    public void onResponse(Call<Transport> call, Response<Transport> response) {
                        getMvpView().hideLoading();
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            getMvpView().onGetTransport(response.body());
                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Transport> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });

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
    public void rejectRequest(int requestId) {
        if (!isViewAttached()) {
            Log.e(TAG, "rejectRequest: View isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().rejectRequest(getDataManager().getUserToken(), requestId)
                .enqueue(new Callback<OrderRequest>() {
                    @Override
                    public void onResponse(Call<OrderRequest> call, Response<OrderRequest> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body());
                            getMvpView().onRejectRequest();
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
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });
    }

    @Override
    public void confirmRequest(int requestId) {
        if (!isViewAttached()) {
            Log.e(TAG, "confirmRequest: View isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().confirmRequest(getDataManager().getUserToken(), requestId)
                .enqueue(new Callback<OrderRequest>() {
                    @Override
                    public void onResponse(Call<OrderRequest> call, Response<OrderRequest> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.toString());
                            getMvpView().onConfirmRequest();
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
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
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
