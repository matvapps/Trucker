package com.foora.perevozkadev.ui.add_drivers_to_order.order_drivers;

import android.util.Log;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.network.model.BaseResponse;
import com.foora.perevozkadev.data.network.model.DriverResponse;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.BasePresenter;
import com.foora.perevozkadev.ui.profile.model.Profile;

import java.io.IOException;
import java.util.List;

import io.reactivex.Scheduler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr.
 */
public class OrderDriversPresenter<V extends OrderDriversMvpView> extends BasePresenter<V> implements OrderDriversMvpPresenter<V> {

    public static final String TAG = OrderDriversPresenter.class.getSimpleName();

    public OrderDriversPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }


    @Override
    public void getOrder(int id) {
        if (!isViewAttached()) {
            Log.e(TAG, "View isn't attached" );
            return;
        }

        getMvpView().showLoading();

        getDataManager()
                .getOrderById(getDataManager().getUserToken(), id)
                .enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            getMvpView().onGetOrder(response.body());
                        } else {
                            try {
                                Log.d(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    @Override
    public void removeDriversFromOrder(int orderId, List<Integer> driverIds) {
        if (!isViewAttached()) {
            Log.e(TAG, "View isn't attached" );
            return;
        }

        getMvpView().showLoading();

        DriverResponse drivers = new DriverResponse();
        drivers.setDrivers(driverIds);

        getDataManager()
                .removeDriversFromOrder(getDataManager().getUserToken(), orderId, drivers)
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            getMvpView().onRemoveDriversFromOrder();
                        } else {
                            try {
                                Log.d(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    @Override
    public void getDriversFromOrder(int id) {
        if (!isViewAttached()) {
            Log.e(TAG, "View isn't attached" );
            return;
        }

        getMvpView().showLoading();

        getDataManager()
                .getOrderDrivers(getDataManager().getUserToken(), id)
                .enqueue(new Callback<DriverResponse>() {
                    @Override
                    public void onResponse(Call<DriverResponse> call, Response<DriverResponse> response) {
                        getMvpView().hideLoading();

//                        Log.d(TAG, "onResponse: " + call.request().toString());
//                        Log.d(TAG, "onResponse: " + response.message());
//                        Log.d(TAG, "onResponse: " + response.body().toString());

                        if (response.isSuccessful()) {
                            getMvpView().onGetDriversFromOrder(response.body().getDrivers());
                        } else {
                            try {
                                Log.d(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DriverResponse> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    @Override
    public void getDriverProfile(int userId) {
        if (!isViewAttached()) {
            Log.e(TAG, "getProfile: View isn't attach");
            return;
        }

        getMvpView().showLoading();

        Log.d(TAG, "token " + getDataManager().getUserToken());

        getDataManager().getProfile(userId, getDataManager().getUserToken())
                .enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        getMvpView().hideLoading();

                        Log.d(TAG, "onResponse: getProfile end with code: " + response.code());

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            getMvpView().onGetDriverProfile(response.body());
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
}
