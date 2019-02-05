package com.foora.perevozkadev.ui.docs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BasePresenterActivity;
import com.github.clans.fab.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;

public class DocsActivity extends BasePresenterActivity<DocsMvpPresenter> implements DocsMvpView {

    public static final String TAG = DocsActivity.class.getSimpleName();

    private static final int TAKE_IMAGE_REQUEST = 0;
    private static final int TAKE_IMAGE_FROM_CAMERA_REQUEST = 1;

    private static final String KEY_ORDER_ID = "order_id";

    private FloatingActionButton btnFromGallery;
    private FloatingActionButton btnFromCamera;


    private String imageFilePath;

    public static void start(Activity activity, int orderId) {
        Intent intent = new Intent(activity, DocsActivity.class);
        intent.putExtra(KEY_ORDER_ID, orderId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docs);

        setUnBinder(ButterKnife.bind(this));

        setUp();
    }

    @Override
    protected void setUp() {

        btnFromGallery = findViewById(R.id.btn_from_gallery);
        btnFromCamera = findViewById(R.id.btn_from_camera);

        btnFromCamera.setOnClickListener(v -> takeImage());
        btnFromGallery.setOnClickListener(v -> takePhoto());
    }

    private void takeImage() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
        }
        if (photoFile != null) {

            // TODO: change applicationId to actual
            Uri photoURI = FileProvider.getUriForFile(this, "com.perevozka.foora.perevozkadev.provider", photoFile);
            takePicture.putExtra(MediaStore.EXTRA_OUTPUT,
                    photoURI);
            startActivityForResult(takePicture, TAKE_IMAGE_FROM_CAMERA_REQUEST);//zero can be replaced with any action code
        }
    }

    private void takePhoto() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, TAKE_IMAGE_REQUEST);//one can be replaced with any action code
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case TAKE_IMAGE_REQUEST:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    PhotoPreviewActivity.startForResult(this, selectedImage.toString());
                }

                break;
            case TAKE_IMAGE_FROM_CAMERA_REQUEST:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = Uri.fromFile(new File(imageFilePath));
                    PhotoPreviewActivity.startForResult(this, selectedImage.toString());

                }
                break;
            case PhotoPreviewActivity.PHOTO_PREVIEW_CODE:
                if (imageReturnedIntent != null) {
                    showMessage("Photo is choose");
                } else {
                    showMessage("Photo not choose");
                }

                break;
        }
    }

    @Override
    protected DocsMvpPresenter createPresenter() {
        return null;
    }


}
