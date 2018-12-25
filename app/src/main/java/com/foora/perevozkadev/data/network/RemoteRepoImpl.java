package com.foora.perevozkadev.data.network;

import com.foora.perevozkadev.data.network.model.ActivateResponse;
import com.foora.perevozkadev.data.network.model.BaseResponse;
import com.foora.perevozkadev.data.network.model.ConfirmLoginResponse;
import com.foora.perevozkadev.data.network.model.GetOrderResponse;
import com.foora.perevozkadev.data.network.model.LoginResponse;
import com.foora.perevozkadev.data.network.model.ProfileResponse;
import com.foora.perevozkadev.data.network.model.RegisterResponse;
import com.foora.perevozkadev.data.network.model.TransportResponse;
import com.foora.perevozkadev.ui.add_order.model.Order;

import io.reactivex.annotations.NonNull;
import retrofit2.Call;

public class RemoteRepoImpl extends BaseRemote implements RemoteRepo {

    @Override
    public Call<RegisterResponse> register(String login, String password, String phone, String userType, String group) {
        return  getApi().register(login, password, phone, userType, group);
    }

    @Override
    public Call<ActivateResponse> activate(int userId, String smsCode) {
        return getApi().activate(userId, smsCode);
    }

    @Override
    public Call<BaseResponse> sendSms(String login, String phone) {
        return getApi().sendSms(login, phone);
    }

    @Override
    public Call<LoginResponse> login(String login, String password) {
        return getApi().login(login, password);
    }

    @Override
    public Call<ConfirmLoginResponse> confirmLogin(String login, String password, String smsCode) {
        return getApi().confirmLogin(login, password, smsCode);
    }

    @Override
    public Call<BaseResponse> addOrder(@NonNull String token, Order order) {
        return getApi().addOrder(token, order);
    }

    @Override
    public Call<GetOrderResponse> getOrders() {
        return getApi().getOrders();
    }

    @Override
    public Call<ProfileResponse> getProfile(String token) {
        return getApi().getProfile(token);
    }

    @Override
    public Call<GetOrderResponse> getUserOrders(String token) {
        return getApi().getUserOrders(token);
    }

    @Override
    public Call<TransportResponse> getUserTransport(String token) {
        return getApi().getTransport(token);
    }
}
