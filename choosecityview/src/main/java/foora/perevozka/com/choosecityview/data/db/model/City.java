package foora.perevozka.com.choosecityview.data.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Alexandr.
 */
@Entity(tableName = "city"
        ,
        foreignKeys = {
                @ForeignKey(entity = Region.class,
                        parentColumns = "id_region",
                        childColumns = "id_region"),
                @ForeignKey(entity = Country.class,
                        parentColumns = "id_country",
                        childColumns = "id_country")
        }
)
public class City {

    @PrimaryKey
    @ColumnInfo(name = "id_city")
    private int id;

    @ColumnInfo(name = "id_region")
    private int regionId;

    @ColumnInfo(name = "id_country")
    private int countryId;

    @ColumnInfo(name = "name")
    private String name;

    public City(int id, int regionId, int countryId, String name) {
        this.id = id;
        this.regionId = regionId;
        this.countryId = countryId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
