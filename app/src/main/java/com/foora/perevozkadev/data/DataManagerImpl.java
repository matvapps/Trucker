package com.foora.perevozkadev.data;

import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.model.ActivateResponse;
import com.foora.perevozkadev.data.network.model.BaseResponse;
import com.foora.perevozkadev.data.network.model.ConfirmLoginResponse;
import com.foora.perevozkadev.data.network.model.GetOrderResponse;
import com.foora.perevozkadev.data.network.model.LoginResponse;
import com.foora.perevozkadev.data.network.model.RegisterResponse;
import com.foora.perevozkadev.data.prefs.PreferencesHelper;
import com.foora.perevozkadev.data.prefs.SharedPrefsHelper;
import com.foora.perevozkadev.ui.add_order.model.Order;

import java.util.List;

import io.reactivex.annotations.NonNull;
import retrofit2.Call;

/**
 * Created by Alexandr.
 */
public class DataManagerImpl implements DataManager {

    private RemoteRepo remoteRepo;
    private PreferencesHelper sharedPrefs;

    public DataManagerImpl(RemoteRepo remoteRepo, PreferencesHelper sharedPrefs) {
        this.remoteRepo = remoteRepo;
        this.sharedPrefs = sharedPrefs;
    }

    @Override
    public Call<RegisterResponse> register(String login, String password, String phone, String userType, String group) {
        return remoteRepo.register(login, password, phone, userType, group);
    }

    @Override
    public Call<ActivateResponse> activate(int userId, String smsCode) {
        return remoteRepo.activate(userId, smsCode);
    }

    @Override
    public Call<BaseResponse> sendSms(String login, String phone) {
        return remoteRepo.sendSms(login, phone);
    }

    @Override
    public Call<LoginResponse> login(String login, String password) {
        return remoteRepo.login(login, password);
    }

    @Override
    public Call<ConfirmLoginResponse> confirmLogin(String login, String password, String smsCode) {
        return remoteRepo.confirmLogin(login, password, smsCode);
    }

    @Override
    public Call<BaseResponse> addOrder(@NonNull String token, Order order) {
        return remoteRepo.addOrder(token, order);
    }

    @Override
    public Call<GetOrderResponse> getOrders() {
        return remoteRepo.getOrders();
    }

    @Override
    public boolean getUserLogged() {
        return sharedPrefs.getUserLogged();
    }

    @Override
    public void setUserLogged(boolean logged) {
        sharedPrefs.setUserLogged(logged);
    }

    @Override
    public String getUserToken() {
        return sharedPrefs.getUserToken();
    }

    @Override
    public void setUserToken(String token) {
        sharedPrefs.setUserToken(token);
    }

    @Override
    public void clear() {

    }
}
