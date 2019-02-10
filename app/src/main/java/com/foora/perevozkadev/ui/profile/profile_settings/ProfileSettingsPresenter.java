package com.foora.perevozkadev.ui.profile.profile_settings;

import android.util.Log;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.network.model.BaseResponse;
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
public class ProfileSettingsPresenter<V extends ProfileSettingsMvpView> extends BasePresenter<V> implements ProfileSettingsMvpPresenter<V> {

    public static final String TAG = ProfileSettingsPresenter.class.getSimpleName();

    public ProfileSettingsPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void getProfile() {
        if (!isViewAttached()) {
            Log.e(TAG, "getProfile: View isn't attach");
            return;
        }

        getMvpView().showLoading();

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

    @Override
    public void changePassword(String login, String password, String newPassword) {
        if (!isViewAttached()) {
            Log.e(TAG, "changePassword: View isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().changePassword(login, password, newPassword)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            getMvpView().onChangePassword(response.body());
                        } else {

                            try {
                                getMvpView().onError(response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        getMvpView().hideLoading();
                        getMvpView().onError("Не удалось сменить пароль");
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
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
                } else {
                    getMvpView().onSmsSend();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t.getCause());
            }
        });

    }

    @Override
    public void changePhone(String login, String password,
                            String phone, String newPhone, String smsCode) {
        if (!isViewAttached()) {
            Log.e(TAG, "changePhone: View isn't attach");
            return;
        }

        getMvpView().showLoading();

        getDataManager().changePhone(login, password, phone, newPhone, smsCode)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body());
                            getMvpView().onChangePhone();
                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string() );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            getMvpView().onError("Не удалось сменить пароль");
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        getMvpView().hideLoading();
                        getMvpView().onError("Не удалось сменить пароль");
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });
    }

    @Override
    public void uploadPhoto(PhotoType type, File file) {
        if (!isViewAttached()) {
            Log.e(TAG, "uploadPhoto: View isn't attach");
            return;
        }

        getMvpView().showLoading();

        MultipartBody.Part image;
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        image = MultipartBody.Part.createFormData("img", file.getName(), reqFile);

        getDataManager().uploadPhoto(type.toString(), getDataManager().getUserToken(), image)
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.toString());
                            getMvpView().onFileUploaded(type, file);
                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string() );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        getMvpView().hideLoading();
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });

    }

    public enum PhotoType {

        INTERNATIONAL_PASSPORT("international-passport"),
        REGISTRATION_CERTIFICATE("registration-certificate"),
        TRANSPORTAION_LICENSE("transportation-license");

        private final String name;

        PhotoType(String s) {
            name = s;
        }

        public boolean equalsName(String otherName) {
            // (otherName == null) check is not needed because name.equals(null) returns false
            return name.equals(otherName);
        }

        public String toString() {
            return this.name;
        }
    }
}
