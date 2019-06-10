package com.foora.perevozkadev.ui.add_drivers_to_order;

import android.util.Log;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.network.model.BaseResponse;
import com.foora.perevozkadev.data.network.model.DriverResponse;
import com.foora.perevozkadev.data.network.model.GetOrderResponse;
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
public class AddDriversToOrderPresenter<V extends AddDriversToOrderMvpView> extends BasePresenter<V> implements AddDriversToOrderMvpPresenter<V> {

    public static final String TAG = AddDriversToOrderPresenter.class.getSimpleName();

    public AddDriversToOrderPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void getStaff() {
        if (!isViewAttached()) {
            Log.e(TAG, "View isn't attached" );
            return;
        }

        getMvpView().showLoading();

        getDataManager()
                .getStaff(getDataManager().getUserToken())
                .enqueue(new Callback<List<Profile>>() {
                    @Override
                    public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            getMvpView().onGetStaff(response.body());
                        } else {
                            try {
                                Log.d(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Profile>> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    @Override
    public void getMyOrders() {
        if (!isViewAttached()) {
            Log.e(TAG, "View isn't attached" );
            return;
        }

        getMvpView().showLoading();

        getDataManager()
                .getUserOrders(getDataManager().getUserToken())
                .enqueue(new Callback<GetOrderResponse>() {
                    @Override
                    public void onResponse(Call<GetOrderResponse> call, Response<GetOrderResponse> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            getMvpView().onGetMyOrders(response.body().getOrders());
                        } else {
                            try {
                                Log.d(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GetOrderResponse> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
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
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    @Override
    public void addDriversToOrder(int orderId, List<Integer> driverIds) {
        if (!isViewAttached()) {
            Log.e(TAG, "View isn't attached" );
            return;
        }

        getMvpView().showLoading();

        DriverResponse drivers = new DriverResponse();
        drivers.setDrivers(driverIds);

        getDataManager()
                .addDriversToOrder(getDataManager().getUserToken(), orderId, drivers)
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        getMvpView().hideLoading();

                        Log.e(TAG, "onResponse: " + driverIds);
                        Log.d(TAG, "onResponse: " + call.request().toString());
                        Log.d(TAG, "onResponse: " + response.message());
                        Log.e(TAG, "onResponse: " + drivers);

                        if (response.isSuccessful()) {
                            getMvpView().onAddDriversToOrder();

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
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }
}
