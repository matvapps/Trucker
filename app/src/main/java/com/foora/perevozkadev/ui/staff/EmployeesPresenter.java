package com.foora.perevozkadev.ui.staff;

import android.util.Log;

import com.foora.perevozkadev.data.DataManager;
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
public class EmployeesPresenter<V extends EmployeesMvpView> extends BasePresenter<V> implements EmployeesMvpPresenter<V> {

    public static final String TAG = EmployeesPresenter.class.getSimpleName();

    public EmployeesPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void getEmployees() {
        if (!isViewAttached()) {
            Log.e(TAG, "getEmployees: view isn't attach");
            return;
        }

        getMvpView().hideLoading();

        getDataManager().getStaff(getDataManager().getUserToken())
                .enqueue(new Callback<List<Profile>>() {
                    @Override
                    public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {

                            Log.d(TAG, "onResponse: " + response.body().toString());
                            getMvpView().onReceiveEmployees(response.body());

                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string() );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            getMvpView().onError("Не удалось получить список сотрудников");
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Profile>> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                        getMvpView().onError("Не удалось получить список сотрудников");
                    }
                });
    }

    @Override
    public void getEmployeesArchive() {
        if (!isViewAttached()) {
            Log.e(TAG, "getEmployeesArchive: view isn't attach");
            return;
        }

        getMvpView().hideLoading();

        getDataManager().getStaffArchive(getDataManager().getUserToken())
                .enqueue(new Callback<List<Profile>>() {
                    @Override
                    public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {

                            Log.d(TAG, "onResponse: " + response.body().toString());
                            getMvpView().onGetEmployeesArchive(response.body());

                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string() );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            getMvpView().onError("Не удалось получить архив сотрудников");
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Profile>> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                        getMvpView().onError("Не удалось получить архив сотрудников");
                    }
                });
    }


    @Override
    public void restoreFromArchive(int profileId) {
        if (!isViewAttached()) {
            Log.e(TAG, "restoreFromArchive: view isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().restoreUserFromArchive(profileId, getDataManager().getUserToken())
                .enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            getMvpView().onRestoreFromArchive();
                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            getMvpView().onError("Ошибка восстановления пользователя");
                        }

                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                        getMvpView().onError("Ошибка восстановления пользователя");
                    }
                });
    }
}
