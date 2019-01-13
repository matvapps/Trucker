package com.foora.perevozkadev.data.db.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Alexandr.
 */
@Entity(tableName = "filter")
public class FilterJson {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String json;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
