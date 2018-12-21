package com.foora.perevozkadev.ui.add_order;

import android.util.Log;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.network.model.BaseResponse;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.BasePresenter;

import java.io.IOException;

import io.reactivex.Scheduler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr.
 */
public class AddOrderPresenter <V extends AddOrderMvpView> extends BasePresenter<V> implements AddOrderMvpPresenter<V> {

    public final String TAG = AddOrderPresenter.class.getSimpleName();

    public AddOrderPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void addOrder(Order order) {
        if (!isViewAttached()) {
            Log.e(TAG, "addOrder: View isn't attached" );
            return;
        }

        getMvpView().showLoading();

        getDataManager()
                .addOrder("token 069a0d70c5be4879e65c877da252e07f0db56a3d", order)
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            getMvpView().showMessage("Adding order success");
                        } else {
                            getMvpView().onError("Adding order error");
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
                        getMvpView().onError("Adding order error");
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }
}
