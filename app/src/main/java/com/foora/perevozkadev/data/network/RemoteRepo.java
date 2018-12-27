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
import com.foora.perevozkadev.ui.my_transport.model.Transport;

import io.reactivex.annotations.NonNull;
import okhttp3.MultipartBody;
import retrofit2.Call;

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

    Call<Transport> addTransport(@NonNull String token,
                                 @NonNull Transport transport);

    Call<BaseResponse> addTransportPhoto(@NonNull int transportId,
                                         @NonNull String token,
                                         @NonNull MultipartBody.Part image);

    Call<GetOrderResponse> getOrders();

    Call<ProfileResponse> getProfile(@NonNull String token);

    Call<GetOrderResponse> getUserOrders(@NonNull String token);

    Call<TransportResponse> getUserTransport(@NonNull String token);

    Call<Transport> getUserTransport(@NonNull int transportId,
                                     @NonNull String token);
}
