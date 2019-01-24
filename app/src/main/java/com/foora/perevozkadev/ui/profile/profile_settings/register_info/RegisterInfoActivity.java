package com.foora.perevozkadev.ui.profile.profile_settings.register_info;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.db.LocalRepo;
import com.foora.perevozkadev.data.db.LocalRepoImpl;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PrefRepo;
import com.foora.perevozkadev.data.prefs.PrefRepoImpl;
import com.foora.perevozkadev.ui.base.BasePresenterActivity;
import com.foora.perevozkadev.ui.profile.model.Profile;
import com.foora.perevozkadev.ui.profile.profile_settings.ProfileSettingsMvpPresenter;
import com.foora.perevozkadev.ui.profile.profile_settings.ProfileSettingsMvpView;
import com.foora.perevozkadev.ui.profile.profile_settings.ProfileSettingsPresenter;
import com.foora.perevozkadev.utils.custom.MyDatePickerFragment;
import com.github.matvapps.AppEditText;
import com.github.matvapps.gallery.GalleryView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class RegisterInfoActivity extends BasePresenterActivity<ProfileSettingsMvpPresenter> implements ProfileSettingsMvpView, View.OnClickListener {

    public static final String TAG = RegisterInfoActivity.class.getSimpleName();

    private final int IMAGE_CERTIFICATE_CODE = 33;
    private final int IMAGE_LICENSE_CODE = 34;

    private View btnBack;
    private View btnDone;

    private AppEditText certificateNum;
    private AppEditText countryRegister;
    private AppEditText licenseNum;
    private Button addCertificatePhotoBtn;
    private Button addLicensePhotoBtn;
    private GalleryView photoCertificateGallery;
    private GalleryView photoLicenseGallery;
    private AppEditText dateTxtv;
    private View dateContainer;

    private Profile profile;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, RegisterInfoActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);

        certificateNum = findViewById(R.id.certificate_num);
        countryRegister = findViewById(R.id.country_register);
        licenseNum = findViewById(R.id.license_num);
        dateTxtv = findViewById(R.id.date);
        dateContainer = findViewById(R.id.date_container);
        addCertificatePhotoBtn = findViewById(R.id.btn_add_certificate);
        addLicensePhotoBtn = findViewById(R.id.btn_add_license);
        photoCertificateGallery = findViewById(R.id.photo_certificate);
        photoLicenseGallery = findViewById(R.id.photo_license);

        btnBack = findViewById(R.id.btn_back);
        btnDone = findViewById(R.id.action_done);

        btnBack.setOnClickListener(v -> finish());
        btnDone.setOnClickListener(this);
        dateContainer.setOnClickListener(this);
        dateTxtv.setOnClickListener(this);
        addCertificatePhotoBtn.setOnClickListener(this);
        addLicensePhotoBtn.setOnClickListener(this);
        dateTxtv.setClickable(false);
        getPresenter().getProfile();
    }

    void onDateContainerClick() {

        MyDatePickerFragment datePickerFragment = new MyDatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "DATE");
        datePickerFragment.setDateChangeListener(datePicker -> {


            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, datePicker.getYear());
            calendar.set(Calendar.MONTH, datePicker.getMonth());
            calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

            SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd", Locale.getDefault());
            String dateStr = format.format(calendar.getTime());
            dateTxtv.setText(dateStr);
        });
    }

    void done() {
        String certificate = certificateNum.getText();
        String countryreg = countryRegister.getText();
        String license = licenseNum.getText();
        String licenseExpDate = dateTxtv.getText().toString();

        if (!certificate.isEmpty())
            profile.setRegCertificateNum(certificate);
        if (!countryreg.isEmpty())
            profile.setRegisterCountry(countryreg);
        if (!license.isEmpty())
            profile.setLicenseNum(license);
        if (!licenseExpDate.isEmpty())
            profile.setLicenseExpirationDate(licenseExpDate);

        getPresenter().changeProfile(profile);

    }

    private void getImageFromGallery(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        startActivityForResult(Intent.createChooser(intent, "Выберите фото"), requestCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_container:
                onDateContainerClick();
                break;
            case R.id.action_done:
                done();
                break;
            case R.id.btn_add_certificate:
                getImageFromGallery(IMAGE_CERTIFICATE_CODE);
                break;
            case R.id.btn_add_license:
                getImageFromGallery(IMAGE_LICENSE_CODE);
                break;
        }
    }

    @Override
    protected ProfileSettingsMvpPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo prefs = new PrefRepoImpl(this);
        LocalRepo localRepo = new LocalRepoImpl(this);
        DataManager dataManager = new DataManagerImpl(remoteRepo, prefs, localRepo);

        ProfileSettingsMvpPresenter presenter = new ProfileSettingsPresenter(dataManager, AndroidSchedulers.mainThread());
        presenter.onAttach(this);

        return presenter;
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void onGetProfile(Profile profile) {
        certificateNum.setText(profile.getRegCertificateNum());
        countryRegister.setText(profile.getRegisterCountry());
        licenseNum.setText(profile.getLicenseNum());
        dateTxtv.setText(profile.getLicenseExpirationDate());

        profile.setUserType(null);

        this.profile = profile;

    }

    @Override
    public void onChangeProfile() {
        showMessage("Профиль успешно изменен");
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_CERTIFICATE_CODE) {
                String imagePath = getPathFromUri(data.getData());
                photoCertificateGallery.addLink(imagePath);
            } else if (requestCode == IMAGE_LICENSE_CODE) {
                String imagePath = getPathFromUri(data.getData());
                photoLicenseGallery.addLink(imagePath);
            }
        }

    }

    private String getPathFromUri(Uri contentUri) {
//        final Uri selectedImage = data.getData();

        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, filePathColumn, null, null, null);
        if (cursor == null)
            return "";

        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();

        Log.d(TAG, "getPathFromUri: " + filePath);
        return "file:" + filePath;
    }

    @Override
    public void onChangePassword(String response) {

    }

    @Override
    public void onSmsSend() {

    }

    @Override
    public void onChangePhone() {

    }


}
