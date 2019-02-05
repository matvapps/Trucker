package com.foora.perevozkadev.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexander Matvienko on 05.02.2019.
 */
public class FileResponse {
    @SerializedName("file")
    @Expose
    private String filePath;

    public FileResponse() {
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "FileResponse{" +
                "filePath='" + filePath + '\'' +
                '}';
    }
}
