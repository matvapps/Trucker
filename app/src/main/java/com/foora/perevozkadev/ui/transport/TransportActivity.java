package com.foora.perevozkadev.ui.transport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PreferencesHelper;
import com.foora.perevozkadev.data.prefs.SharedPrefsHelper;
import com.foora.perevozkadev.ui.base.BasePresenterActivity;
import com.foora.perevozkadev.ui.my_transport.model.Transport;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class TransportActivity extends BasePresenterActivity<TransportMvpPresenter> implements TransportMvpView {

    public static final String TAG = TransportActivity.class.getSimpleName();

    public static final String TRANSPORT_ID_KEY = "transport_id";

    private int transportId;

    TextView transportCategory;
    TextView tranportType;
    TextView tranportAllowedWeight;
    TextView transportNoLoadMass;
    TextView transportRegisterNum;
    TextView transportVin;
    TextView transportPassportNum;
    TextView transportRegisterPlace;
    TextView transportName;

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
        PreferencesHelper prefs = new SharedPrefsHelper(this);
        DataManager dataManager = new DataManagerImpl(remoteRepo, prefs);

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
    }
}
