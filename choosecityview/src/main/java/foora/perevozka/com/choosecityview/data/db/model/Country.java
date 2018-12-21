package foora.perevozka.com.choosecityview.data.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Alexandr.
 */
@Entity(tableName = "country")
public class Country {

    @ColumnInfo(name = "name")
    private String name;

    @PrimaryKey
    @ColumnInfo(name = "id_country")
    private int id;


	public Country(int id, String name) {
		this.id = id;
		this.name = name;
	}

    public int getId() {
		return id;
	}

    public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

    public void setName(String name) {
		this.name = name;
	}
}
