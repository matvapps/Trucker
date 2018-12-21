package foora.perevozka.com.choosecityview.ui;

/**
 * Created by Alexandr.
 */
public interface ChooseCityMvpPresenter {

    void getCountries();

    void getRegionsInCountry(int countryId);

    void getCitiesInRegions(int regionId);

}
