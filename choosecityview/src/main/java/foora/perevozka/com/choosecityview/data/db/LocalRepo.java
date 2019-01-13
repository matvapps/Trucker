package foora.perevozka.com.choosecityview.data.db;

import java.util.List;

import foora.perevozka.com.choosecityview.data.db.model.City;
import foora.perevozka.com.choosecityview.data.db.model.Country;
import foora.perevozka.com.choosecityview.data.db.model.Region;
import io.reactivex.Flowable;

/**
 * Created by Alexandr.
 */
public interface LocalRepo {

    Flowable<List<Country>> getCountries();
    Flowable<List<Region>> getRegionsInCountry(int id);
    Flowable<List<City>> getCityInRegion(int id);

}
