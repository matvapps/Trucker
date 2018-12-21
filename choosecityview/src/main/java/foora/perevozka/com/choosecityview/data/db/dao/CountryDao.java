package foora.perevozka.com.choosecityview.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import foora.perevozka.com.choosecityview.data.db.model.Country;
import io.reactivex.Flowable;

/**
 * Created by Alexandr.
 */
@Dao
public interface CountryDao {

    @Query("SELECT * FROM country")
    Flowable<List<Country>> getCountries();

    @Insert
    void insert(Country country);

    @Delete
    void delete(Country country);

    @Update
    void update(Country country);
}
