package foora.perevozka.com.choosecityview.ui;

import java.util.List;

import foora.perevozka.com.choosecityview.data.db.model.City;
import foora.perevozka.com.choosecityview.data.db.model.Country;
import foora.perevozka.com.choosecityview.data.db.model.Region;

/**
 * Created by Alexandr.
 */
public interface ChooseCityMvpView {

    void onGetCountries(List<Country> countries);

    void onGetRegions(List<Region> regions);

    void onGetCities(List<City> cities);

}
