package foora.perevozka.com.choosecityview.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import foora.perevozka.com.choosecityview.data.db.model.Region;
import io.reactivex.Flowable;

/**
 * Created by Alexandr.
 */
@Dao
public interface RegionDao {

    @Query("SELECT * FROM region")
    Flowable<List<Region>> getRegions();

    @Query("SELECT * FROM region WHERE id_country = :countryId")
    Flowable<List<Region>> getRegionInCountry(int countryId);

    @Insert
    void insert(Region region);

    @Delete
    void delete(Region region);

    @Update
    void update(Region region);
}
