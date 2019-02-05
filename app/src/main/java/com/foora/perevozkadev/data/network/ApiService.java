package com.foora.perevozkadev.data.network;


import com.foora.perevozkadev.data.network.model.ActivateResponse;
import com.foora.perevozkadev.data.network.model.AddFileResponse;
import com.foora.perevozkadev.data.network.model.BaseResponse;
import com.foora.perevozkadev.data.network.model.ConfirmLoginResponse;
import com.foora.perevozkadev.data.network.model.FileResponse;
import com.foora.perevozkadev.data.network.model.GetOrderResponse;
import com.foora.perevozkadev.data.network.model.LoginResponse;
import com.foora.perevozkadev.data.network.model.OrderRequest;
import com.foora.perevozkadev.data.network.model.RegisterResponse;
import com.foora.perevozkadev.data.network.model.RequestBody;
import com.foora.perevozkadev.data.network.model.StatusResponse;
import com.foora.perevozkadev.data.network.model.TransportResponse;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.my_transport.model.Transport;
import com.foora.perevozkadev.ui.profile.model.Profile;

import java.util.List;

import io.reactivex.annotations.NonNull;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {


    // Registration
    @FormUrlEncoded
    @POST("user/registration/")
    Call<RegisterResponse> register(@NonNull @Field("username") String login,
                                    @NonNull @Field("password") String password,
                                    @NonNull @Field("phone") String phone,
                                    @NonNull @Field("user_type") String userType,
                                    @NonNull @Field("group") String group);

    @FormUrlEncoded
    @POST("user/activate/")
    Call<ActivateResponse> activate(@NonNull @Field("user_id") int userId,
                                    @NonNull @Field("sms_code") String smsCode);


    @FormUrlEncoded
    @POST("user/sms/send/")
    Call<String> sendSms(@NonNull @Field("login") String login,
                         @NonNull @Field("phone") String phone);

    @FormUrlEncoded
    @POST("auth/")
    Call<LoginResponse> login(@NonNull @Field("login") String login,
                              @NonNull @Field("password") String password);

    @FormUrlEncoded
    @POST("auth/")
    Call<ConfirmLoginResponse> confirmLogin(@NonNull @Field("login") String login,
                                            @NonNull @Field("password") String password,
                                            @NonNull @Field("sms_code") String sms_code);

    // ----------------------------------------------------------------------------------------


    // All for user profile
    @GET("user/")
    Call<Profile> getProfile(@Header("Authorization") String token);


    @Headers({"Accept: application/json"})
    @PATCH("user/")
    Call<Profile> changeProfile(@Header("Authorization") String token,
                                @Body Profile profile);

    @FormUrlEncoded
    @POST("user/password/change/")
    Call<String> changePassword(@NonNull @Field("login") String login,
                                @NonNull @Field("password") String password,
                                @NonNull @Field("new_password") String newPassword);

    @FormUrlEncoded
    @POST("user/phone/change/")
    Call<String> changePhone(@NonNull @Field("login") String login,
                             @NonNull @Field("password") String password,
                             @NonNull @Field("phone") String phone,
                             @NonNull @Field("new_phone") String newPhone,
                             @NonNull @Field("sms_code") String smsCode);

    @FormUrlEncoded
    @POST("user/photo/{photo_type}")
    Call<BaseResponse> uploadPhoto(@Path(value = "photo_type", encoded = true) String photoType,
                                   @Header("Authorization") String token,
                                   @Part MultipartBody.Part image);
    // ----------------------------------------------------------------------------------------


    // User orders
    @GET("user/order/")
    Call<GetOrderResponse> getUserOrders(@Header("Authorization") String token);

    @Headers({"Accept: application/json"})
    @POST("user/order/")
    Call<BaseResponse> addOrder(@Header("Authorization") String token,
                                @Body Order order);

    @GET("orders/")
    Call<GetOrderResponse> getOrders(@Header("Authorization") String token,
                                     @Query("weight_from") float weightFrom,
                                     @Query("weight_to") float weightTo,
                                     @Query("volume_from") float volumeFrom,
                                     @Query("volume_to") float volumeTo);

    @GET("order/{order_id}/")
    Call<Order> getOrderById(@Header("Authorization") String token,
                             @Path("order_id") int orderId);
    // ----------------------------------------------------------------------------------------

    // Employees
    @GET("user/staff/")
    Call<List<Profile>> getStaff(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("user/staff/add/")
    Call<Profile> addUserToStaff(@Header("Authorization") String token,
                                 @NonNull @Field("username") String login,
                                 @NonNull @Field("password") String password,
                                 @NonNull @Field("email") String email,
                                 @NonNull @Field("phone") String phone,
                                 @NonNull @Field("user_type") String userType,
                                 @NonNull @Field("first_name") String firstName,
                                 @NonNull @Field("last_name") String lastName,
                                 @NonNull @Field("group") String group);


    @DELETE("user/staff/{user_id}/delete/")
    Call<String> deleteUserFromStaff(@Path(value = "user_id", encoded = true) int userId,
                                     @Header("Authorization") String token);

    // ----------------------------------------------------------------------------------------


    // All for transport
    @Headers({"Accept: application/json"})
    @POST("transport/")
    Call<Transport> addTransport(@Header("Authorization") String token,
                                 @Body Transport transport);

    @Multipart
    @POST("transport/{transport_id}/photo/")
    Call<BaseResponse> addTransportPhoto(@Path(value = "transport_id", encoded = true) int transportId,
                                         @Header("Authorization") String token,
                                         @Part MultipartBody.Part image);

    @GET("transport/")
    Call<TransportResponse> getTransport(@Header("Authorization") String token);

    @GET("transport/{transport_id}/")
    Call<Transport> getTransport(@Path(value = "transport_id", encoded = true) int transportId,
                                 @Header("Authorization") String token);
    // ----------------------------------------------------------------------------------------


    // Requests
    @Headers({"Accept: application/json"})
    @POST("order/{order_id}/requests/")
    Call<OrderRequest> sendRequest(@Header("Authorization") String token,
                                   @Path(value = "order_id", encoded = true) int orderId,
                                   @Body RequestBody requestBody);

    @GET("user/my-requests/")
    Call<List<OrderRequest>> getUserRequests(@Header("Authorization") String token);

    @GET("user/requests-to-my-orders/")
    Call<List<OrderRequest>> getToUserRequests(@Header("Authorization") String token);

    @GET("order/request/{request_id}/info/")
    Call<OrderRequest> getRequestInfo(@Header("Authorization") String token,
                                      @Path(value = "request_id", encoded = true) int requestId);

    @GET("order/request/{request_id}/reject/")
    Call<OrderRequest> rejectRequest(@Header("Authorization") String token,
                                           @Path(value = "request_id", encoded = true) int requestId);

    @PATCH("order/request/{request_id}/confirm/")
    Call<OrderRequest> confirmRequest(@Header("Authorization") String token,
                                            @Path(value = "request_id", encoded = true) int requestId);
    // ----------------------------------------------------------------------------------------


    // Order files
    @Multipart
    @POST("order/{order_id}/files/")
    Call<AddFileResponse> addFileToOrder(@Header("Authorization") String token,
                                         @Path(value = "order_id", encoded = true) int orderId,
                                         @Part MultipartBody.Part file);

    @GET("order/{order_id}/files/")
    Call<List<FileResponse>> getOrderFiles(@Header("Authorization") String token,
                                     @Path(value = "order_id", encoded = true) int orderId);

    // ----------------------------------------------------------------------------------------

    // Change status
    @FormUrlEncoded
    @POST("order/{order_id}/status/")
    Call<StatusResponse> changeOrderStatus(@Header("Authorization") String token,
                                           @Path(value = "order_id", encoded = true) int orderId,
                                           @NonNull @Field("status") String status);

    // ----------------------------------------------------------------------------------------



}
