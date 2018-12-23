package com.foora.perevozkadev.data.network;


import com.foora.perevozkadev.data.network.model.ActivateResponse;
import com.foora.perevozkadev.data.network.model.BaseResponse;
import com.foora.perevozkadev.data.network.model.ConfirmLoginResponse;
import com.foora.perevozkadev.data.network.model.GetOrderResponse;
import com.foora.perevozkadev.data.network.model.LoginResponse;
import com.foora.perevozkadev.data.network.model.ProfileResponse;
import com.foora.perevozkadev.data.network.model.RegisterResponse;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.my_transport.model.Transport;

import java.util.List;

import io.reactivex.annotations.NonNull;
import retrofit2.Call;
import retrofit2.http.Header;

public interface RemoteRepo {
    Call<RegisterResponse> register(@NonNull String login,
                                    @NonNull String password,
                                    @NonNull String phone,
                                    @NonNull String userType,
                                    @NonNull String group);

    Call<ActivateResponse> activate(@NonNull int userId,
                                    @NonNull String smsCode);

    Call<BaseResponse> sendSms(@NonNull String login,
                               @NonNull String phone);

    Call<LoginResponse> login(@NonNull String login,
                              @NonNull String password);

    Call<ConfirmLoginResponse> confirmLogin(@NonNull String login,
                                            @NonNull String password,
                                            @NonNull String smsCode);

    Call<BaseResponse> addOrder(@NonNull String token,
                                @NonNull Order order);

    Call<GetOrderResponse> getOrders();

    Call<ProfileResponse> getProfile(@NonNull String token);

    Call<GetOrderResponse> getUserOrders(@NonNull String token);

    Call<List<Transport>> getUserTransport(@NonNull String token);
}
