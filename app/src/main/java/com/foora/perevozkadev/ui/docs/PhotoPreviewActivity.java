package com.foora.perevozkadev.ui.docs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.foora.foora.perevozkadev.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

/**
 * Created by Alexander Matvienko on 04.02.2019.
 */
public class PhotoPreviewActivity extends AppCompatActivity {

    public static final String TAG = PhotoPreviewActivity.class.getSimpleName();
    public static final int PHOTO_PREVIEW_CODE = 32;
    public static final String KEY_IMAGE_URI = "image_uri";

    private String imageUri;

    private PhotoView photoView;
    private AppCompatButton button;
    private View btnBack;

    public static void startForResult(Activity activity, String imageUri) {
        Intent intent = new Intent(activity, PhotoPreviewActivity.class);
        intent.putExtra(KEY_IMAGE_URI, imageUri);
        activity.startActivityForResult(intent, PHOTO_PREVIEW_CODE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);

        imageUri = getIntent().getStringExtra(KEY_IMAGE_URI);

        photoView = findViewById(R.id.photo_view);
        button = findViewById(R.id.btn_add);
        btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(v -> {
            setResult(PHOTO_PREVIEW_CODE, null);
            finish();
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(KEY_IMAGE_URI, imageUri);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        Uri uri = Uri.parse(imageUri);
        Picasso.get()
                .load(uri)
                .into(photoView);



    }
}
