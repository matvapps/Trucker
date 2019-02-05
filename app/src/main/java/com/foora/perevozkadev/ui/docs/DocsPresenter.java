package com.foora.perevozkadev.ui.docs;

import android.util.Log;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.network.model.AddFileResponse;
import com.foora.perevozkadev.data.network.model.FileResponse;
import com.foora.perevozkadev.ui.base.BasePresenter;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
public class DocsPresenter<V extends DocsMvpView> extends BasePresenter<V> implements DocsMvpPresenter<V> {

    public static final String TAG = DocsPresenter.class.getSimpleName();

    public DocsPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void addFileToOrder(int orderId, File file) {
        if (!isViewAttached()) {
            Log.e(TAG, "addFileToOrder: DocsMvpView isn't attach");
            return;
        }

        getMvpView().showLoading();

        MultipartBody.Part image;
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        image = MultipartBody.Part.createFormData("file", file.getName(), reqFile);

        getDataManager().addFileToOrder(getDataManager().getUserToken(), orderId, image)
                .enqueue(new Callback<AddFileResponse>() {
                    @Override
                    public void onResponse(Call<AddFileResponse> call, Response<AddFileResponse> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            getMvpView().onAddFileToOrder();
                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<AddFileResponse> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });
    }

    @Override
    public void getOrderFiles(int orderId) {
        if (!isViewAttached()) {
            Log.e(TAG, "getOrderFiles: DocsMvpView isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().getOrderFiles(getDataManager().getUserToken(), orderId)
                .enqueue(new Callback<List<FileResponse>>() {
                    @Override
                    public void onResponse(Call<List<FileResponse>> call, Response<List<FileResponse>> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            getMvpView().onGetOrderFiles(response.body());
                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<List<FileResponse>> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });

    }
}
