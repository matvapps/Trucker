package com.foora.perevozkadev.data.network;

import com.foora.perevozkadev.data.network.model.ActivateResponse;
import com.foora.perevozkadev.data.network.model.BaseResponse;
import com.foora.perevozkadev.data.network.model.ConfirmLoginResponse;
import com.foora.perevozkadev.data.network.model.GetOrderResponse;
import com.foora.perevozkadev.data.network.model.LoginResponse;
import com.foora.perevozkadev.data.network.model.OrderRequest;
import com.foora.perevozkadev.data.network.model.RequestBody;
import com.foora.perevozkadev.ui.profile.model.Profile;
import com.foora.perevozkadev.data.network.model.RegisterResponse;
import com.foora.perevozkadev.data.network.model.TransportResponse;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.my_transport.model.Transport;

import java.util.List;

import io.reactivex.annotations.NonNull;
import okhttp3.MultipartBody;
import retrofit2.Call;

public class RemoteRepoImpl extends BaseRemote implements RemoteRepo {

    @Override
    public Call<RegisterResponse> register(String login, String password, String phone, String userType, String group) {
        return getApi().register(login, password, phone, userType, group);
    }

    @Override
    public Call<ActivateResponse> activate(int userId, String smsCode) {
        return getApi().activate(userId, smsCode);
    }

    @Override
    public Call<String> sendSms(String login, String phone) {
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
    public Call<Transport> addTransport(String token, Transport transport) {
        return getApi().addTransport(token, transport);
    }

    @Override
    public Call<BaseResponse> addTransportPhoto(int transportId, String token, MultipartBody.Part image) {
        return getApi().addTransportPhoto(transportId, token, image);
    }

//    @Override
//    public Call<GetOrderResponse> getOrders() {
//        return getApi().getOrders();
//    }

    @Override
    public Call<GetOrderResponse> getOrders(String token, float weightFrom, float weightTo, float volumeFrom, float volumeTo) {
        return getApi().getOrders(token, weightFrom, weightTo, volumeFrom, volumeTo);
    }

    @Override
    public Call<Order> getOrderById(String token, int orderId) {
        return getApi().getOrderById(token, orderId);
    }

    @Override
    public Call<Profile> getProfile(String token) {
        return getApi().getProfile(token);
    }

    @Override
    public Call<Profile> changeProfile(String token, Profile profile) {
        return getApi().changeProfile(token, profile);
    }

    @Override
    public Call<String> changePassword(String login, String password, String newPassword) {
        return getApi().changePassword(login, password, newPassword);
    }

    @Override
    public Call<String> changePhone(String login, String password,
                                    String phone, String newPhone, String smsCode) {
        return getApi().changePhone(login, password, phone, newPhone, smsCode);
    }

    @Override
    public Call<GetOrderResponse> getUserOrders(String token) {
        return getApi().getUserOrders(token);
    }

    @Override
    public Call<TransportResponse> getUserTransport(String token) {
        return getApi().getTransport(token);
    }

    @Override
    public Call<Transport> getUserTransport(int transportId, String token) {
        return getApi().getTransport(transportId, token);
    }

    @Override
    public Call<List<Profile>> getStaff(String token) {
        return getApi().getStaff(token);
    }

    @Override
    public Call<Profile> addUserToStaff(String token, String login, String password,
                                        String email, String phone, String userType,
                                        String firstName, String lastName, String group) {
        return getApi().addUserToStaff(token, login, password,
                email, phone, userType,
                firstName, lastName, group);
    }

    @Override
    public Call<String> deleteUserFromStaff(int userId, String token) {
        return getApi().deleteUserFromStaff(userId, token);
    }

    @Override
    public Call<OrderRequest> sendRequest(String token, int orderId, RequestBody requestBody) {
        return getApi().sendRequest(token, orderId, requestBody);
    }

    @Override
    public Call<List<OrderRequest>> getUserRequests(String token) {
        return getApi().getUserRequests(token);
    }


}
