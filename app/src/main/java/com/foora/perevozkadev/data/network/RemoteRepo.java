package com.foora.perevozkadev.data.network;


import com.foora.perevozkadev.data.network.model.ActivateResponse;
import com.foora.perevozkadev.data.network.model.AddFileResponse;
import com.foora.perevozkadev.data.network.model.BaseResponse;
import com.foora.perevozkadev.data.network.model.ConfirmLoginResponse;
import com.foora.perevozkadev.data.network.model.FileResponse;
import com.foora.perevozkadev.data.network.model.GetOrderResponse;
import com.foora.perevozkadev.data.network.model.LoginResponse;
import com.foora.perevozkadev.data.network.model.NullResponse;
import com.foora.perevozkadev.data.network.model.OrderRequest;
import com.foora.perevozkadev.data.network.model.RegisterResponse;
import com.foora.perevozkadev.data.network.model.RequestBody;
import com.foora.perevozkadev.data.network.model.TransportResponse;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.my_transport.model.Transport;
import com.foora.perevozkadev.ui.profile.model.Profile;

import java.util.List;

import io.reactivex.annotations.NonNull;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RemoteRepo {
    Call<RegisterResponse> register(@NonNull String login,
                                    @NonNull String password,
                                    @NonNull String phone,
                                    @NonNull String userType,
                                    @NonNull String group);

    Call<ActivateResponse> activate(@NonNull int userId,
                                    @NonNull String smsCode);

    Call<String> sendSms(@NonNull String login,
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

//    Call<GetOrderResponse> getOrders();

    Call<GetOrderResponse> getOrders(@NonNull String token,
                                     @NonNull float weightFrom,
                                     @NonNull float weightTo,
                                     @NonNull float volumeFrom,
                                     @NonNull float volumeTo);

    Call<Order> getOrderById(@NonNull String token,
                             @NonNull int orderId);

    Call<Profile> getProfile(@NonNull String token);

    Call<Profile> changeProfile(@NonNull String token, Profile profile);

    Call<String> changePassword(@NonNull String login,
                                @NonNull String password,
                                @NonNull String newPassword);

    Call<String> changePhone(@NonNull String login,
                             @NonNull String password,
                             @NonNull String phone,
                             @NonNull String newPhone,
                             @NonNull String smsCode);

    Call<GetOrderResponse> getUserOrders(@NonNull String token);

    Call<TransportResponse> getUserTransport(@NonNull String token);

    Call<Transport> getUserTransport(@NonNull int transportId,
                                     @NonNull String token);

    Call<List<Profile>> getStaff(@NonNull String token);

    Call<Profile> addUserToStaff(@NonNull String token,
                                 @NonNull String login,
                                 @NonNull String password,
                                 @NonNull String email,
                                 @NonNull String phone,
                                 @NonNull String userType,
                                 @NonNull String firstName,
                                 @NonNull String lastName,
                                 @NonNull String group);

    Call<String> deleteUserFromStaff(@NonNull int userId,
                                     @NonNull String token);


    Call<OrderRequest> sendRequest(@NonNull String token,
                                   @NonNull int orderId,
                                   @NonNull RequestBody requestBody);

    Call<List<OrderRequest>> getUserRequests(@NonNull String token);

    Call<List<OrderRequest>> getToUserRequests(@NonNull String token);

    Call<OrderRequest> getRequestInfo(@NonNull String token,
                                      @NonNull int requestId);

    Call<List<NullResponse>> rejectRequest(@NonNull String token,
                                           @NonNull int requestId);

    Call<List<NullResponse>> confirmRequest(@NonNull String token,
                                @NonNull int requestId);

    Call<AddFileResponse> addFileToOrder(@NonNull String token,
                                         @NonNull int orderId,
                                         @NonNull MultipartBody.Part file);

    Call<List<FileResponse>> getOrderFiles(@NonNull String token,
                                     @NonNull int orderId);

}
