package com.foora.perevozkadev.ui.docs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BasePresenterActivity;

import butterknife.ButterKnife;

public class DocsActivity extends BasePresenterActivity<DocsMvpPresenter> implements DocsMvpView {

    public static final String TAG = DocsActivity.class.getSimpleName();

    private static final int TAKE_IMAGE_REQUEST = 0;
    private static final int TAKE_IMAGE_FROM_CAMERA_REQUEST = 1;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, DocsActivity.class);
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
//        takeImage();
    }

    private void takeImage() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, TAKE_IMAGE_FROM_CAMERA_REQUEST);//zero can be replaced with any action code
    }

    private void takePhoto() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, TAKE_IMAGE_REQUEST);//one can be replaced with any action code
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
                    Uri selectedImage = imageReturnedIntent.getData();
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
