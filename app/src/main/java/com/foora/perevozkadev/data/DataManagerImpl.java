package com.foora.perevozkadev.data;

import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.model.ActivateResponse;
import com.foora.perevozkadev.data.network.model.BaseResponse;
import com.foora.perevozkadev.data.network.model.ConfirmLoginResponse;
import com.foora.perevozkadev.data.network.model.GetOrderResponse;
import com.foora.perevozkadev.data.network.model.LoginResponse;
import com.foora.perevozkadev.data.network.model.ProfileResponse;
import com.foora.perevozkadev.data.network.model.RegisterResponse;
import com.foora.perevozkadev.data.network.model.TransportResponse;
import com.foora.perevozkadev.data.prefs.PreferencesHelper;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.my_transport.model.Transport;

import io.reactivex.annotations.NonNull;
import okhttp3.MultipartBody;
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
    public Call<Transport> addTransport(String token, Transport transport) {
        return remoteRepo.addTransport(token, transport);
    }

    @Override
    public Call<BaseResponse> addTransportPhoto(int transportId, String token, MultipartBody.Part image) {
        return remoteRepo.addTransportPhoto(transportId, token, image);
    }

    @Override
    public Call<GetOrderResponse> getOrders() {
        return remoteRepo.getOrders();
    }

    @Override
    public Call<ProfileResponse> getProfile(String token) {
        return remoteRepo.getProfile(token);
    }

    @Override
    public Call<GetOrderResponse> getUserOrders(String token) {
        return remoteRepo.getUserOrders(token);
    }

    @Override
    public Call<TransportResponse> getUserTransport(String token) {
        return remoteRepo.getUserTransport(token);
    }

    @Override
    public Call<Transport> getUserTransport(int transportId, String token) {
        return remoteRepo.getUserTransport(transportId, token);
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
