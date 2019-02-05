package com.foora.perevozkadev.ui.docs;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.db.LocalRepo;
import com.foora.perevozkadev.data.db.LocalRepoImpl;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.network.model.FileResponse;
import com.foora.perevozkadev.data.prefs.PrefRepo;
import com.foora.perevozkadev.data.prefs.PrefRepoImpl;
import com.foora.perevozkadev.ui.base.BasePresenterActivity;
import com.foora.perevozkadev.utils.ViewUtils;
import com.foora.perevozkadev.utils.custom.GridSpacingItemDecoration;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class DocsActivity extends BasePresenterActivity<DocsMvpPresenter> implements DocsMvpView {

    public static final String TAG = DocsActivity.class.getSimpleName();

    private static final int TAKE_IMAGE_REQUEST = 0;
    private static final int TAKE_IMAGE_FROM_CAMERA_REQUEST = 1;

    private static final String KEY_ORDER_ID = "order_id";

    private RecyclerView docsList;
    private FloatingActionButton btnFromGallery;
    private FloatingActionButton btnFromCamera;

    private DocsAdapter docsAdapter;

    private String imageFilePath;

    private int orderId;

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

        orderId = getIntent().getIntExtra(KEY_ORDER_ID, -1);

        setUp();
    }

    @Override
    protected void setUp() {

        docsList = findViewById(R.id.docs_list);
        btnFromGallery = findViewById(R.id.btn_from_gallery);
        btnFromCamera = findViewById(R.id.btn_from_camera);
        View btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(v -> finish());
        btnFromCamera.setOnClickListener(v -> takeImage());
        btnFromGallery.setOnClickListener(v -> takePhoto());

        docsAdapter = new DocsAdapter();
        docsList.setLayoutManager(new GridLayoutManager(this, 2));
        docsList.addItemDecoration(new GridSpacingItemDecoration(2, ViewUtils.dpToPx(8), true));
//        docsList.setLayoutManager(new LinearLayoutManager(this));
        docsList.setAdapter(docsAdapter);

        getPresenter().getOrderFiles(orderId);
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
        switch (requestCode) {
            case TAKE_IMAGE_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    PhotoPreviewActivity.startForResult(this, selectedImage.toString());
                }

                break;
            case TAKE_IMAGE_FROM_CAMERA_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = Uri.fromFile(new File(imageFilePath));
                    PhotoPreviewActivity.startForResult(this, selectedImage.toString());

                }
                break;
            case PhotoPreviewActivity.PHOTO_PREVIEW_CODE:
                if (imageReturnedIntent != null) {
                    String imageUriStr = imageReturnedIntent.getStringExtra(PhotoPreviewActivity.IMAGE_URI_RESULT);
                    Log.d(TAG, "onActivityResult: " + imageReturnedIntent.getStringExtra(PhotoPreviewActivity.IMAGE_URI_RESULT));
                    Uri uri = Uri.parse(imageUriStr);
                    getPresenter().addFileToOrder(orderId, new File(getRealPathFromURI(uri)));
                } else {
                    showMessage("Отменено пользователем");
                }
                break;
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    protected DocsMvpPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo preferencesHelper = new PrefRepoImpl(this);
        LocalRepo localRepo = new LocalRepoImpl(this);
        DataManager dataManager = new DataManagerImpl(remoteRepo, preferencesHelper, localRepo);

        DocsMvpPresenter docsPresenter = new DocsPresenter(dataManager, AndroidSchedulers.mainThread());
        docsPresenter.onAttach(this);

        return docsPresenter;
    }


    @Override
    public void onGetOrderFiles(List<FileResponse> files) {
        docsAdapter.setItems(files);
        Log.d(TAG, "onGetOrderFiles: " + files.toString());
        docsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAddFileToOrder() {
        getPresenter().getOrderFiles(orderId);
    }
}
