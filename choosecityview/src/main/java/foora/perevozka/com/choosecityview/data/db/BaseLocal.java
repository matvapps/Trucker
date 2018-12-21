package foora.perevozka.com.choosecityview.data.db;

import android.content.Context;

import com.huma.room_for_asset.RoomAsset;

/**
 * Created by Alexandr.
 */
public class BaseLocal {

    private AppDatabase appDatabase;

    public AppDatabase getDatabase(Context context) {
        appDatabase = RoomAsset
                .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "countries.db")
                .build();
        return appDatabase;
    }

}
