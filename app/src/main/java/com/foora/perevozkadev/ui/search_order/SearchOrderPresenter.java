package com.foora.perevozkadev.ui.search_order;

import android.util.Log;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.db.model.FilterJson;
import com.foora.perevozkadev.data.network.model.GetOrderResponse;
import com.foora.perevozkadev.ui.base.BasePresenter;
import com.foora.perevozkadev.ui.profile.model.Profile;

import java.io.IOException;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr.
 */
public class SearchOrderPresenter<V extends SearchOrderMvpView> extends BasePresenter<V> implements SearchOrderMvpPresenter<V> {

    public static final String TAG = SearchOrderPresenter.class.getSimpleName();

    public SearchOrderPresenter(DataManager dataManager, Scheduler scheduler) {
        super(dataManager, scheduler);
    }

    @Override
    public void getOrders(float weightFrom, float weightTo, float volumeFrom, float volumeTo) {
        if (!isViewAttached()) {
            Log.e(TAG, "getOrders: view isn't attach");
            return;
        }

//        getMvpView().showLoading();

        getDataManager()
                .getOrders(getDataManager().getUserToken(), weightFrom, weightTo, volumeFrom, volumeTo)
                .enqueue(new Callback<GetOrderResponse>() {
                    @Override
                    public void onResponse(Call<GetOrderResponse> call, Response<GetOrderResponse> response) {
//                        getMvpView().hideLoading();

                        if (response.isSuccessful()) {
                            getMvpView().onGetOrders(response.body().getOrders());
                        } else {
                            getMvpView().onError("Error getting orders");
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GetOrderResponse> call, Throwable t) {
//                        getMvpView().hideLoading();
                        getMvpView().onError("Error getting orders");
                        Log.e(TAG, "onFailure: " + t.getMessage(), t.getCause());
                    }
                });
    }

    @Override
    public void addFilter(FilterJson filterJson) {
        Completable.fromAction(() ->
                getDataManager().addFilterJson(filterJson))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: add filter");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage(), e);
                    }
                });

    }

    @Override
    public void deleteFilter(FilterJson filterJson) {
        Completable.fromAction(() ->
                getDataManager().deleteFilterJson(filterJson))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete delete filter: ");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage(), e);
                    }
                });

    }

    @Override
    public void updateFilter(FilterJson filterJson) {
        Completable.fromAction(() ->
                getDataManager().updateFilterJson(filterJson))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete update filter: ");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage(), e);
                    }
                });
    }

    @Override
    public void getFilters() {
        getDataManager().getFilters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<List<FilterJson>>() {
                    @Override
                    public void onNext(List<FilterJson> filterJsons) {
                        getMvpView().onGetFilters(filterJsons);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError: " + t.getMessage(), t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    @Override
    public void getProfile() {
        if (!isViewAttached()) {
            Log.e(TAG, "getProfile: View isn't attach");
            return;
        }

//        getMvpView().showLoading();

        Log.d(TAG, "token " + getDataManager().getUserToken());

        getDataManager().getProfile(getDataManager().getUserToken())
                .enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
//                        getMvpView().hideLoading();

                        Log.d(TAG, "onResponse: getProfile end with code: " + response.code());

                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            getMvpView().onGetProfile(response.body());
                        } else {
                            try {
                                Log.e(TAG, "onResponse: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
//                        getMvpView().hideLoading();
                        getMvpView().onError("Не удалось получить ваш профиль");
                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                    }
                });

    }

}
