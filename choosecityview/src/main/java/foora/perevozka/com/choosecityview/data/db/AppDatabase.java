package foora.perevozka.com.choosecityview.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import foora.perevozka.com.choosecityview.data.db.dao.CityDao;
import foora.perevozka.com.choosecityview.data.db.dao.CountryDao;
import foora.perevozka.com.choosecityview.data.db.dao.RegionDao;
import foora.perevozka.com.choosecityview.data.db.model.City;
import foora.perevozka.com.choosecityview.data.db.model.Country;
import foora.perevozka.com.choosecityview.data.db.model.Region;

/**
 * Created by Alexandr.
 */
@Database(entities = {Country.class, Region.class, City.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CountryDao countryDao();

    public abstract RegionDao regionDao();

    public abstract CityDao cityDao();

}
