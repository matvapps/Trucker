package com.foora.perevozkadev.ui.transport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.foora.perevozkadev.ui.my_transport.model.Transport;
import com.github.matvapps.gallery.GalleryView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class TransportActivity extends BasePresenterActivity<TransportMvpPresenter> implements TransportMvpView {

    public static final String TAG = TransportActivity.class.getSimpleName();

    public static final String TRANSPORT_ID_KEY = "transport_id";

    private int transportId;

    private TextView transportCategory;
    private TextView tranportType;
    private TextView tranportAllowedWeight;
    private TextView transportNoLoadMass;
    private TextView transportRegisterNum;
    private TextView transportVin;
    private TextView transportPassportNum;
    private TextView transportRegisterPlace;
    private TextView transportName;
    private GalleryView galleryView;

    public static void start(Activity activity, int transportId) {
        Intent intent = new Intent(activity, TransportActivity.class);
        intent.putExtra(TRANSPORT_ID_KEY, transportId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);

        transportId = getIntent().getIntExtra(TRANSPORT_ID_KEY, -1);

        setUnBinder(ButterKnife.bind(this));

        transportCategory = findViewById(R.id.transport_category);
        tranportType = findViewById(R.id.transport_type);
        tranportAllowedWeight = findViewById(R.id.transport_allowed_weight);
        transportNoLoadMass = findViewById(R.id.transport_no_load_mass);
        transportRegisterNum = findViewById(R.id.transport_register_number);
        transportVin = findViewById(R.id.transport_vin);
        transportPassportNum = findViewById(R.id.transport_passport_num);
        transportRegisterPlace = findViewById(R.id.transport_register_place);
        transportName = findViewById(R.id.name);
        galleryView = findViewById(R.id.galleryView);

        View btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        getPresenter().getTransport(transportId);

    }

    @Override
    protected void setUp() {


    }


    @Override
    protected TransportMvpPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo prefs = new PrefRepoImpl(this);
        LocalRepo localRepo = new LocalRepoImpl(this);
        DataManager dataManager = new DataManagerImpl(remoteRepo, prefs, localRepo);

        TransportMvpPresenter mvpPresenter = new TransportPresenter(dataManager, AndroidSchedulers.mainThread());
        mvpPresenter.onAttach(this);

        return mvpPresenter;
    }


    @Override
    public void onGetTransport(Transport transport) {
        transportCategory.setText(transport.getCategory());
        tranportType.setText(transport.getType());
        tranportAllowedWeight.setText(String.valueOf(transport.getAllowedWeight()));
        transportNoLoadMass.setText(String.valueOf(transport.getNoLoadMass()));
        transportRegisterNum.setText(transport.getRegistrationNum());
        transportVin.setText(transport.getVin());
        transportPassportNum.setText(transport.getPassportNum());
        transportRegisterPlace.setText(transport.getRegistrationPlace());
        transportName.setText(transport.getModel());

        galleryView.setMaxItems(3);

        List<String> photos = new ArrayList<>();
        for (String item : transport.getPhotos()) {
            photos.add("http://dev.perevozka.me/api/" + item);
        }



        galleryView.setLinks(photos);

        Log.d(TAG, "onGetTransport: " + photos);
    }
}
