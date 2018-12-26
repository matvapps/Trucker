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
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

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
    Call<BaseResponse> sendSms(@NonNull @Field("login") String login,
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

    @Headers({"Accept: application/json"})
    @POST("order/")
    Call<BaseResponse> addOrder(@Header("Authorization") String token,
                                @Body Order order);

    @GET("orders/")
    Call<GetOrderResponse> getOrders();



    @GET("user/")
    Call<ProfileResponse> getProfile(@Header("Authorization") String token);

    @GET("user/order/")
    Call<GetOrderResponse> getUserOrders(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("user/photo/{photo_type}")
    Call<BaseResponse> uploadPhoto(@Path(value = "photo_type", encoded = true) String photoType,
                                   @Header("Authorization") String token,
                                   @Part MultipartBody.Part image);

    @Headers({"Accept: application/json"})
    @POST("transport/")
    Call<Transport> addTransport(@Header("Authorization") String token,
                                    @Body Transport transport);

//    @FormUrlEncoded
    @Multipart
    @POST("transport/{transport_id}/photo/")
    Call<BaseResponse> addTransportPhoto(@Path(value = "transport_id", encoded = true) int transportId,
                                   @Header("Authorization") String token,
                                   @Part MultipartBody.Part image);

    @GET("transport/")
    Call<TransportResponse> getTransport(@Header("Authorization") String token);

//    @FormUrlEncoded
//    @POST("transport/{transport_id}/photo/")
//    Call<BaseResponse> addTransportPhoto(@Path(value = "transport_id", encoded = true) int transportId,
//                                         @Header("Authorization") String token,
//                                         @Part MultipartBody.Part image);



//    @POST("user/sms/send")
//    Call<BaseResponse> resendSms(@Header("token") String token,
//                                 @NonNull @Query("login") String login,
//                                 @NonNull @Query("phone") String phone);


}
