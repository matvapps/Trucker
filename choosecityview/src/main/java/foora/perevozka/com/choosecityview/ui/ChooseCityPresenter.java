package foora.perevozka.com.choosecityview.ui;

import android.content.Context;
import android.util.Log;

import java.util.List;

import foora.perevozka.com.choosecityview.data.DataManager;
import foora.perevozka.com.choosecityview.data.DataManagerImpl;
import foora.perevozka.com.choosecityview.data.db.LocalRepo;
import foora.perevozka.com.choosecityview.data.db.LocalRepoImpl;
import foora.perevozka.com.choosecityview.data.db.model.City;
import foora.perevozka.com.choosecityview.data.db.model.Country;
import foora.perevozka.com.choosecityview.data.db.model.Region;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by Alexandr.
 */
public class ChooseCityPresenter implements ChooseCityMvpPresenter {

    public static final String TAG = ChooseCityPresenter.class.getSimpleName();

    private ChooseCityMvpView mvpView;
    private DataManager dataManager;

    public ChooseCityPresenter(Context context) {
        LocalRepo localRepo = new LocalRepoImpl(context);
        dataManager = new DataManagerImpl(localRepo);
    }

    public void onAttach(ChooseCityMvpView view) {
        this.mvpView = view;
    }

    public boolean isViewAttached() {
        return mvpView != null;
    }

    @Override
    public void getCountries() {
        if (!isViewAttached())
            return;

        Log.d(TAG, "getCountries: view attached ");

        dataManager.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<List<Country>>() {
                    @Override
                    public void onNext(List<Country> countries) {
                        mvpView.onGetCountries(countries);
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
    public void getRegionsInCountry(int countryId) {
        if (!isViewAttached())
            return;

        dataManager.getRegionsInCountry(countryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<List<Region>>() {
                    @Override
                    public void onNext(List<Region> regions) {
                        mvpView.onGetRegions(regions);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getCitiesInRegions(final int regionId) {
        if (!isViewAttached())
            return;

        dataManager.getCityInRegion(regionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<List<City>>() {
                    @Override
                    public void onNext(List<City> cities) {
                        mvpView.onGetCities(cities);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
