package com.foora.perevozkadev.utils;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Alexander Matvienko on 05.02.2019.
 */
public class ImageManager {

    private final String PATH = "/data/data/com.helloandroid.imagedownloader/";  //put the downloaded file here

    private Callback listener;

    public void DownloadFromUrl(String imageURL, String fileName) {  //this is the downloader method
        try {
            URL url = new URL("http://yoursite.com/" + imageURL); //you can write here any link
                    File file = new File(fileName);

            long startTime = System.currentTimeMillis();
            Log.d("ImageManager", "download begining");
            Log.d("ImageManager", "download url:" + url);
            Log.d("ImageManager", "downloaded file name:" + fileName);
            /* Open a connection to that URL. */
            URLConnection ucon = url.openConnection();

            /*
             * Define InputStreams to read from the URLConnection.
             */
            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[50];
            int current = 0;

            /*
             * Read bytes to the Buffer until there is nothing more to read(-1).
             */
            while ((current = bis.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, current);
            }

            /* Convert the Bytes read to a String. */
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(buffer.toByteArray());
            fos.close();
            Log.d("ImageManager", "download ready in"
                    + ((System.currentTimeMillis() - startTime) / 1000)
                    + " sec");

//            if (listener != null)
//                listener.onImageLoaded();

        } catch (IOException e) {
            Log.d("ImageManager", "Error: " + e);
        }

    }

    public interface Callback {
        void onImageLoaded(String fileName);
    }

}
