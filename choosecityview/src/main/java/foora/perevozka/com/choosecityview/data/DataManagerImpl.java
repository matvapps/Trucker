package foora.perevozka.com.choosecityview.data;

import java.util.List;

import foora.perevozka.com.choosecityview.data.db.LocalRepo;
import foora.perevozka.com.choosecityview.data.db.model.City;
import foora.perevozka.com.choosecityview.data.db.model.Country;
import foora.perevozka.com.choosecityview.data.db.model.Region;
import io.reactivex.Flowable;

/**
 * Created by Alexandr.
 */
public class DataManagerImpl implements DataManager {

    LocalRepo localRepo;

    public DataManagerImpl(LocalRepo localRepo) {
        this.localRepo = localRepo;
    }

    @Override
    public Flowable<List<Country>> getCountries() {
        return localRepo.getCountries();
    }

    @Override
    public Flowable<List<Region>> getRegionsInCountry(int id) {
        return localRepo.getRegionsInCountry(id);
    }

    @Override
    public Flowable<List<City>> getCityInRegion(int id) {
        return localRepo.getCityInRegion(id);
    }
}
