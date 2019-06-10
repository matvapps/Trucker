package com.foora.perevozkadev.ui.add_employee;

import android.util.Log;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.network.model.BaseResponse;
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
public class AddEmployeePresenter<V extends AddEmployeeMvpView> extends BasePresenter<V> implements AddEmployeeMvpPresenter<V> {

    public static final String TAG = AddEmployeePresenter.class.getSimpleName();

    public AddEmployeePresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void addEmployee(String login, String password,
                            String email, String phone, String userType,
                            String firstName, String lastName, String group) {

        if (!isViewAttached()) {
            Log.e(TAG, "addEmployee: view isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().addUserToStaff(getDataManager().getUserToken(), login, password,
                email, phone, userType, firstName, lastName, group)
                .enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body());
                            getMvpView().onAddEmployee();
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
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                        getMvpView().onError("Ошибка добавления сотрудника");
                    }
                });

    }

    @Override
    public void changeEmployee(Profile profile) {
        if (!isViewAttached()) {
            Log.e(TAG, "changeEmployee: view isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().changeStaffProfile(profile.getUserId(), getDataManager().getUserToken(), profile)
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        getMvpView().hideLoading();

                        Log.e(TAG, "onResponse: " + call.request().toString());

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body());
                            getMvpView().onChangeEmployee();
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
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                        getMvpView().onError("Ошибка редактирования сотрудника");
                    }
                });
    }

}
