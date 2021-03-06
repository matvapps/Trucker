package com.foora.perevozkadev.data;

import com.foora.perevozkadev.data.db.LocalRepo;
import com.foora.perevozkadev.data.db.model.FilterJson;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.model.ActivateResponse;
import com.foora.perevozkadev.data.network.model.AddFileResponse;
import com.foora.perevozkadev.data.network.model.BaseResponse;
import com.foora.perevozkadev.data.network.model.ConfirmLoginResponse;
import com.foora.perevozkadev.data.network.model.DriverResponse;
import com.foora.perevozkadev.data.network.model.FileResponse;
import com.foora.perevozkadev.data.network.model.GetOrderResponse;
import com.foora.perevozkadev.data.network.model.Location;
import com.foora.perevozkadev.data.network.model.LoginResponse;
import com.foora.perevozkadev.data.network.model.OrderRequest;
import com.foora.perevozkadev.data.network.model.RegisterResponse;
import com.foora.perevozkadev.data.network.model.RequestBody;
import com.foora.perevozkadev.data.network.model.SosResponse;
import com.foora.perevozkadev.data.network.model.StatusResponse;
import com.foora.perevozkadev.data.network.model.TrackResponse;
import com.foora.perevozkadev.data.network.model.TransportResponse;
import com.foora.perevozkadev.data.prefs.PrefRepo;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.my_transport.model.Transport;
import com.foora.perevozkadev.ui.profile.model.Profile;
import com.foora.perevozkadev.utils.custom.tnvd.Cargo;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import okhttp3.MultipartBody;
import retrofit2.Call;

/**
 * Created by Alexandr.
 */
public class DataManagerImpl implements DataManager {

    private RemoteRepo remoteRepo;
    private PrefRepo sharedPrefs;
    private LocalRepo localRepo;

    public DataManagerImpl(RemoteRepo remoteRepo, PrefRepo sharedPrefs, LocalRepo localRepo) {
        this.remoteRepo = remoteRepo;
        this.sharedPrefs = sharedPrefs;
        this.localRepo = localRepo;
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
    public Call<String> sendSms(String login, String phone) {
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
    public Call<GetOrderResponse> getOrders(String token, float weightFrom, float weightTo, float volumeFrom, float volumeTo) {
        return remoteRepo.getOrders(token, weightFrom, weightTo, volumeFrom, volumeTo);
    }

    @Override
    public Call<Order> getOrderById(String token, int orderId) {
        return remoteRepo.getOrderById(token, orderId);
    }

//    @Override
//    public Call<GetOrderResponse> getOrders() {
//        return remoteRepo.getOrders();
//    }

    @Override
    public Call<Profile> getProfile(String token) {
        return remoteRepo.getProfile(token);
    }

    @Override
    public Call<Profile> getProfile(int userId, String token) {
        return remoteRepo.getProfile(userId, token);
    }

    @Override
    public Call<Profile> changeProfile(String token, Profile profile) {
        return remoteRepo.changeProfile(token, profile);
    }

    @Override
    public Call<String> changePassword(String login, String password, String newPassword) {
        return remoteRepo.changePassword(login, password, newPassword);
    }

    @Override
    public Call<String> changePhone(String login, String password, String phone, String newPhone, String smsCode) {
        return remoteRepo.changePhone(login, password, phone, newPhone, smsCode);
    }

    @Override
    public Call<BaseResponse> setUse2Fa(String token, int use_2Fa) {
        return remoteRepo.setUse2Fa(token, use_2Fa);
    }

    @Override
    public Call<BaseResponse> uploadPhoto(String photoType, String token, MultipartBody.Part image) {
        return remoteRepo.uploadPhoto(photoType, token, image);
    }

    @Override
    public Call<BaseResponse> verifyUser(String token, MultipartBody.Part image) {
        return remoteRepo.verifyUser(token, image);
    }

    @Override
    public Call<GetOrderResponse> getUserOrders(String token) {
        return remoteRepo.getUserOrders(token);
    }

    @Override
    public Call<GetOrderResponse> getExecutorOrders(String token) {
        return remoteRepo.getExecutorOrders(token);
    }

    @Override
    public Call<TransportResponse> getUserTransport(String token) {
        return remoteRepo.getUserTransport(token);
    }

    @Override
    public Call<TransportResponse> getTransportArchive(String token) {
        return remoteRepo.getTransportArchive(token);
    }

    @Override
    public Call<Transport> getUserTransport(int transportId, String token) {
        return remoteRepo.getUserTransport(transportId, token);
    }

    @Override
    public Call<Transport> changeTransport(int transportId, String token, Transport transport) {
        return remoteRepo.changeTransport(transportId, token, transport);
    }

    @Override
    public Call<BaseResponse> addTransportToArchive(int transportId, String token) {
        return remoteRepo.addTransportToArchive(transportId, token);
    }

    @Override
    public Call<BaseResponse> removeTransportFromArchive(int transportId, String token) {
        return remoteRepo.removeTransportFromArchive(transportId, token);
    }

    @Override
    public Call<List<Profile>> getStaff(String token) {
        return remoteRepo.getStaff(token);
    }

    @Override
    public Call<Profile> addUserToStaff(String token, String login, String password,
                                        String email, String phone, String userType,
                                        String firstName, String lastName, String group) {
        return remoteRepo.addUserToStaff(token, login, password,
                email, phone, userType,
                firstName, lastName, group);
    }

    @Override
    public Call<Profile> deleteUserFromStaff(int userId, String token) {
        return remoteRepo.deleteUserFromStaff(userId, token);
    }

    @Override
    public Call<Profile> restoreUserFromArchive(int userId, String token) {
        return remoteRepo.restoreUserFromArchive(userId, token);
    }

    @Override
    public Call<List<Profile>> getStaffArchive(String token) {
        return remoteRepo.getStaffArchive(token);
    }

    @Override
    public Call<BaseResponse> changeStaffProfile(int userId, String token, Profile profile) {
        return remoteRepo.changeStaffProfile(userId, token, profile);
    }

    @Override
    public Call<OrderRequest> sendRequest(String token, int orderId, RequestBody requestBody) {
        return remoteRepo.sendRequest(token, orderId, requestBody);
    }

    @Override
    public Call<List<OrderRequest>> getUserRequests(String token) {
        return remoteRepo.getUserRequests(token);
    }

    @Override
    public Call<List<OrderRequest>> getToUserRequests(String token) {
        return remoteRepo.getToUserRequests(token);
    }

    @Override
    public Call<OrderRequest> getRequestInfo(String token, int requestId) {
        return remoteRepo.getRequestInfo(token, requestId);
    }

    @Override
    public Call<OrderRequest> rejectRequest(String token, int requestId) {
        return remoteRepo.rejectRequest(token, requestId);
    }

    @Override
    public Call<OrderRequest> confirmRequest(String token, int requestId) {
        return remoteRepo.confirmRequest(token, requestId);
    }

    @Override
    public Call<AddFileResponse> addFileToOrder(String token, int orderId, MultipartBody.Part file) {
        return remoteRepo.addFileToOrder(token, orderId, file);
    }

    @Override
    public Call<List<FileResponse>> getOrderFiles(String token, int orderId) {
        return remoteRepo.getOrderFiles(token, orderId);
    }

    @Override
    public Call<StatusResponse> changeOrderStatus(String token, int orderId, String status) {
        return remoteRepo.changeOrderStatus(token, orderId, status);
    }

    @Override
    public Call<List<Cargo>> searchCargoTypes(String query) {
        return remoteRepo.searchCargoTypes(query);
    }

    @Override
    public Call<DriverResponse> getOrderDrivers(String token, int orderId) {
        return remoteRepo.getOrderDrivers(token, orderId);
    }

    @Override
    public Call<BaseResponse> addDriversToOrder(String token, int orderId, DriverResponse drivers) {
        return remoteRepo.addDriversToOrder(token, orderId, drivers);
    }

    @Override
    public Call<BaseResponse> removeDriversFromOrder(String token, int orderId, DriverResponse drivers) {
        return remoteRepo.removeDriversFromOrder(token, orderId, drivers);
    }

    @Override
    public Call<BaseResponse> addUserLocation(String token, Location location) {
        return remoteRepo.addUserLocation(token, location);
    }

    @Override
    public Call<List<TrackResponse>> trackOrder(String token, int orderId) {
        return remoteRepo.trackOrder(token, orderId);
    }

    @Override
    public Call<SosResponse> callSos(String token, double latitude, double longitude) {
        return remoteRepo.callSos(token, latitude, longitude);
    }

    @Override
    public Call<SosResponse> acceptSos(String token, int sos_id) {
        return remoteRepo.acceptSos(token, sos_id);
    }

    @Override
    public Call<SosResponse> rejectSos(String token, int sos_id) {
        return remoteRepo.rejectSos(token, sos_id);
    }

    @Override
    public Call<List<TrackResponse>> getLastUserLoc(String token, int userId) {
        return remoteRepo.getLastUserLoc(token, userId);
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
    public void setUserName(String name) {
        sharedPrefs.setUserName(name);
    }

    @Override
    public String getUserName() {
        return sharedPrefs.getUserName();
    }

    @Override
    public void setUserRole(String role) {
        sharedPrefs.setUserRole(role);
    }

    @Override
    public String getUserRole() {
        return sharedPrefs.getUserRole();
    }

    @Override
    public boolean getIs2FaEnabled() {
        return sharedPrefs.getIs2FaEnabled();
    }

    @Override
    public void set2FaEnabled(boolean enabled) {
        sharedPrefs.set2FaEnabled(enabled);
    }

    @Override
    public Flowable<List<FilterJson>> getFilters() {
        return localRepo.getFilters();
    }

    public void addFilterJson(FilterJson filterJson) {
        localRepo.addFilterJson(filterJson);
    }

    public void deleteFilterJson(FilterJson filterJson) {
        localRepo.deleteFilterJson(filterJson);
    }

    public void updateFilterJson(FilterJson filterJson) {
        localRepo.updateFilterJson(filterJson);
    }

    @Override
    public void clear() {

    }


}
