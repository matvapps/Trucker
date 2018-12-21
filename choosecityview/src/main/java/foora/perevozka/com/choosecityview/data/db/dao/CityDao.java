package foora.perevozka.com.choosecityview.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import foora.perevozka.com.choosecityview.data.db.model.City;
import io.reactivex.Flowable;

/**
 * Created by Alexandr.
 */

@Dao
public interface CityDao {

    @Query("SELECT * FROM city")
    Flowable<List<City>> getCities();

    @Query("SELECT * FROM city WHERE id_country = :countryId")
    Flowable<List<City>> getCitiesInCountry(int countryId);

    @Query("SELECT * FROM city WHERE id_region = :regionId")
    Flowable<List<City>> getCitiesInRegion(int regionId);

    @Insert
    void insert(City city);

    @Delete
    void delete(City city);

    @Update
    void update(City city);


}
