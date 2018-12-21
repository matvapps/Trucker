package foora.perevozka.com.choosecityview.data.db;

import android.content.Context;

import java.util.List;

import foora.perevozka.com.choosecityview.data.db.model.City;
import foora.perevozka.com.choosecityview.data.db.model.Country;
import foora.perevozka.com.choosecityview.data.db.model.Region;
import io.reactivex.Flowable;

/**
 * Created by Alexandr.
 */
public class LocalRepoImpl extends BaseLocal implements LocalRepo{

    private Context context;

    public LocalRepoImpl(Context context) {
        this.context = context;
    }

    @Override
    public Flowable<List<Country>> getCountries() {
        return getDatabase(context).countryDao().getCountries();
    }

    @Override
    public Flowable<List<Region>> getRegionsInCountry(int id) {
        return getDatabase(context).regionDao().getRegionInCountry(id);
    }

    @Override
    public Flowable<List<City>> getCityInRegion(int id) {
        return getDatabase(context).cityDao().getCitiesInRegion(id);
    }
}
