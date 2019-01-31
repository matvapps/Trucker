package com.foora.perevozkadev.ui.choose_transport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.foora.perevozkadev.ui.my_transport.model.Transport;
import com.foora.perevozkadev.ui.profile.ProfilePresenter;
import com.foora.perevozkadev.ui.profile.adapter.ProfileTransportAdapter;
import com.foora.perevozkadev.utils.ViewUtils;
import com.foora.perevozkadev.utils.custom.ItemSpacingDecoration;

import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ChooseTransportActivity extends BasePresenterActivity<ChooseTransportMvpPresenter> implements ChooseTransportMvpView {

    public static final String TAG = ChooseTransportActivity.class.getSimpleName();

    private static final String KEY_REQUIRED_CAR = "required_car";
    private static final String KEY_ORDER_ID = "order_id";


    private RecyclerView transportListView;
    private TextView requiredCarQuant;
    private Button btnSendRequest;
    private View btnBack;

    private ProfileTransportAdapter transportAdapter;

    private int requiredCar;
    private String orderId;

    public static void start(Activity activity, int requiredCar, String orderId) {
        Intent intent = new Intent(activity, ChooseTransportActivity.class);
        intent.putExtra(KEY_REQUIRED_CAR, requiredCar);
        intent.putExtra(KEY_ORDER_ID, orderId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requiredCar = getIntent().getIntExtra(KEY_REQUIRED_CAR, 0);
        orderId = getIntent().getStringExtra(KEY_ORDER_ID);

        setContentView(R.layout.activity_choose_transport);

        setUnBinder(ButterKnife.bind(this));

        setUp();

    }

    @Override
    protected void setUp() {

        transportListView = findViewById(R.id.transport_list);
        requiredCarQuant = findViewById(R.id.required_car_amount);
        btnSendRequest = findViewById(R.id.btn_send_request);
        btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(v -> finish());

        transportListView.setLayoutManager(new LinearLayoutManager(this));

        transportAdapter = new ProfileTransportAdapter();
        transportAdapter.setUseSelection(true);

        transportListView.setAdapter(transportAdapter);
        transportListView.addItemDecoration(new ItemSpacingDecoration(ViewUtils.dpToPx(8),0, ViewUtils.dpToPx(8), ViewUtils.dpToPx(5)));

        requiredCarQuant.setText(String.format(Locale.getDefault(), "Для заказа нужно %d автомобиля", requiredCar));
        transportAdapter.setMaxSelectedItems(requiredCar);

        getPresenter().getTransport();
    }


    @Override
    protected ChooseTransportMvpPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo preferencesHelper = new PrefRepoImpl(this);
        LocalRepo localRepo = new LocalRepoImpl(this);
        DataManager dataManager = new DataManagerImpl(remoteRepo, preferencesHelper, localRepo);

        ChooseTransportMvpPresenter profilePresenter = new ChooseTransportPresenter(dataManager, AndroidSchedulers.mainThread());
        profilePresenter.onAttach(this);

        return profilePresenter;
    }


    @Override
    public void onGetUserTransports(List<Transport> transports) {
        transportAdapter.setItems(transports);
    }
}
