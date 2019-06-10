package com.foora.perevozkadev.ui.employee;

import android.util.Log;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.ui.base.BasePresenter;
import com.foora.perevozkadev.ui.profile.model.Profile;

import java.io.IOException;

import io.reactivex.Scheduler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr.
 */
public class EmployeePresenter<V extends EmployeeMvpView> extends BasePresenter<V> implements EmployeeMvpPresenter<V> {

    public static final String TAG = EmployeePresenter.class.getSimpleName();

    public EmployeePresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void removeUser(int userId) {

        if (!isViewAttached()) {
            Log.e(TAG, "removeUser: view isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().deleteUserFromStaff(userId, getDataManager().getUserToken())
                .enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            getMvpView().onRemoveUser();
                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            getMvpView().onError("Ошибка удаления пользователя");
                        }

                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                        getMvpView().onError("Ошибка удаления пользователя");
                    }
                });

    }

    @Override
    public void getProfile(int userId) {
        if (!isViewAttached()) {
            Log.e(TAG, "getProfile: view isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().getProfile(userId, getDataManager().getUserToken())
                .enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            getMvpView().onGetProfile(response.body());
                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            getMvpView().onError("Ошибка получения пользователя");
                        }

                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                        getMvpView().onError("Ошибка получения пользователя");
                    }
                });

    }

}
