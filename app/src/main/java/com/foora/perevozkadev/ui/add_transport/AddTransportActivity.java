package com.foora.perevozkadev.ui.add_transport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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
import com.foora.perevozkadev.ui.add_transport.general_info.FragmentGeneralInfo;
import com.foora.perevozkadev.ui.add_transport.register_info.FragmentRegisterInfo;
import com.foora.perevozkadev.ui.base.BasePresenterActivity;
import com.foora.perevozkadev.ui.my_transport.model.Transport;
import com.foora.perevozkadev.utils.custom.ViewPagerNoScroll;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AddTransportActivity extends BasePresenterActivity<AddTransportMvpPresenter>
        implements AddTransportMvpView, FragmentGeneralInfo.Callback, FragmentRegisterInfo.Callback {

    public static final String TAG = AddTransportActivity.class.getSimpleName();

    private static final String TRANSPORT_KEY = "order_key";

    private ViewPagerNoScroll viewPager;
    private TabLayout tabLayout;
    private TextView titleTxtv;

    private AddTransportPagerAdapter addTransportPagerAdapter;

    private Transport transportForEdit;
    private Transport transport;
    private int transportId;

    private List<File> transportPhotos;
    private List<File> passportPhotos;

    private int sentPhotoCount = 0;

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    };


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, AddTransportActivity.class);
        activity.startActivity(intent);
    }

    public static void start(Activity activity, Transport transport) {
        Intent intent = new Intent(activity, AddTransportActivity.class);
        Gson gson = new Gson();
        intent.putExtra(TRANSPORT_KEY, gson.toJson(transport));
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transport);

        setUnBinder(ButterKnife.bind(this));

        transportForEdit = new Gson().fromJson(getIntent().getStringExtra(TRANSPORT_KEY), Transport.class);


        transport = new Transport();

        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        titleTxtv = findViewById(R.id.title);

        View btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        addTransportPagerAdapter = new AddTransportPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(addTransportPagerAdapter);
        viewPager.setPagingEnabled(false);

        LinearLayout tabStrip = (LinearLayout) tabLayout.getChildAt(0);
        tabStrip.setEnabled(false);
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener((v, event) -> true);
        }

        requestPermissionsSafely(PERMISSIONS, PERMISSION_ALL);

        if (transportForEdit != null)
            titleTxtv.setText("Редактировать транспорт");
    }

    public Transport getTransportForEdit() {
        Log.d(TAG, "onCreate: TransportForEdit: " + transportForEdit);
        return transportForEdit;
    }

    @Override
    protected void setUp() {

    }


    @Override
    protected AddTransportMvpPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo prefs = new PrefRepoImpl(this);
        LocalRepo localRepo = new LocalRepoImpl(this);
        DataManager dataManager = new DataManagerImpl(remoteRepo, prefs, localRepo);

        AddTransportPresenter addTransportPresenter = new AddTransportPresenter(dataManager, AndroidSchedulers.mainThread());
        addTransportPresenter.onAttach(this);

        return addTransportPresenter;
    }


    @Override
    public void onReceiveGeneralInfo(String model, String category, String type,
                                     float massPlaced, float noLoadMass, List<File> photos) {
        viewPager.setCurrentItem(1, true);

        transport.setModel(model);
        transport.setCategory(category);
        transport.setType(type);
        transport.setAllowedWeight(massPlaced);
        transport.setNoLoadMass(noLoadMass);
        transportPhotos = photos;
    }

    @Override
    public void onReceiveRegisterInfo(String regNum, String vin, String carNum,
                                      String regPlace, String regDate, List<File> photos) {
        transport.setRegistrationNum(regNum);
        transport.setVin(vin);
        transport.setPassportNum(carNum);
        transport.setRegistrationPlace(regPlace);
        transport.setRegistrationDate(regDate);
        transport.setVehicleCondition("good");
        transport.setLocation("Current location");
        passportPhotos = photos;

        if (transportForEdit != null) {
            getPresenter().changeTransport(transportForEdit.getId(), transport);
        } else {
            getPresenter().addTransport(transport);
        }

//        finish();
    }

    @Override
    public void onAddTransport(Transport transport) {
        transportId = transport.getId();

        for (File file : transportPhotos) {
            getPresenter().addPhotoToTransport(file, transportId);
        }

//        finish();
    }

    @Override
    public void onChangeTransport(Transport transport) {
        finish();
    }

    @Override
    public void onAddPhoto() {
        sentPhotoCount++;

        if (sentPhotoCount == transportPhotos.size()) {
            for (File file : passportPhotos) {
                getPresenter().addPhotoToTransport(file, transportId);
            }
        }

        if (sentPhotoCount == (transportPhotos.size() + passportPhotos.size())) {
//            showMessage("Транспорт успешно добавлен");
            SuccessDialogFragment.newInstance().show(getSupportFragmentManager());
//            finish();
        }
    }
}
