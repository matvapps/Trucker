package foora.perevozka.com.choosecityview.data.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Alexandr.
 */
@Entity(tableName = "region"
        ,
        foreignKeys = {
                @ForeignKey(entity = Country.class,
                        parentColumns = "id_country",
                        childColumns = "id_country")}
)
public class Region {

    @PrimaryKey
    @ColumnInfo(name = "id_region")
    private int id;

    @ColumnInfo(name = "id_country")
    private int countryId;

    @ColumnInfo(name = "name")
    private String name;

    public Region(int id, int countryId, String name) {
        this.id = id;
        this.countryId = countryId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
