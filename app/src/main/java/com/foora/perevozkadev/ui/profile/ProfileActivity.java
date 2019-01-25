package com.foora.perevozkadev.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.db.LocalRepo;
import com.foora.perevozkadev.data.db.LocalRepoImpl;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.ui.profile.model.Profile;
import com.foora.perevozkadev.data.prefs.PrefRepo;
import com.foora.perevozkadev.data.prefs.PrefRepoImpl;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.nav.BaseNavPresenterActivity;
import com.foora.perevozkadev.ui.my_transport.model.Transport;
import com.foora.perevozkadev.ui.profile.adapter.PartnersAdapter;
import com.foora.perevozkadev.ui.profile.adapter.ProfileTransportAdapter;
import com.foora.perevozkadev.ui.profile.model.Partner;
import com.foora.perevozkadev.ui.search_order.orders.OrdersAdapter;
import com.foora.perevozkadev.ui.profile.profile_settings.ProfileSettingsActivity;
import com.foora.perevozkadev.ui.transport.TransportActivity;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ProfileActivity extends BaseNavPresenterActivity<ProfileMvpPresenter>
        implements ProfileMvpView, View.OnClickListener, OnMapReadyCallback {

    public static final String TAG = ProfileActivity.class.getSimpleName();

    private final int PLACE_PICKER_REQUEST = 1;

    private NestedScrollView nestedScrollView;

    private ExpandableTextView expandableTextView;
    private RecyclerView partnersListView;
    private RecyclerView transportListView;
    private RecyclerView ordersListView;

    private TextView userNameTxtv;
    private TextView userShortNameTxtv;
    private TextView userTypeTxtv;
    private ImageView userImageView;
    private MapView userLocMapView;

    private Button seeAllPartners;
    private Button seeAllTransport;
    private Button seeAllOrders;

    private PartnersAdapter partnersAdapter;
    private ProfileTransportAdapter transportAdapter;
    private OrdersAdapter ordersAdapter;

    private PrefRepo preferencesHelper;
    private GoogleMap googleMap;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ProfileActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUnBinder(ButterKnife.bind(this));

        userLocMapView = findViewById(R.id.map_image_view);
        userLocMapView.onCreate(savedInstanceState);
        userLocMapView.getMapAsync(this);
    }

    private void openMap() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(ProfileActivity.this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    protected String getTitleStr() {
        return "";
    }


    @Override
    protected void setUp() {
        super.setUp();
        expandableTextView = findViewById(R.id.expand_text_view);
        expandableTextView.setText("Описание");

        seeAllOrders = findViewById(R.id.btn_see_all_orders);
        seeAllPartners = findViewById(R.id.btn_see_all_partners);
        seeAllTransport = findViewById(R.id.btn_see_all_transport);
        nestedScrollView = findViewById(R.id.root_view);

        userNameTxtv = findViewById(R.id.user_name);
        userShortNameTxtv = findViewById(R.id.short_name);
        userTypeTxtv = findViewById(R.id.user_type);
        userImageView = findViewById(R.id.user_image);

        seeAllTransport.setOnClickListener(this);
        seeAllPartners.setOnClickListener(this);
        seeAllOrders.setOnClickListener(this);

        partnersListView = findViewById(R.id.partners_list_view);
        transportListView = findViewById(R.id.transport_list);
        ordersListView = findViewById(R.id.order_list);

        partnersAdapter = new PartnersAdapter();
        transportAdapter = new ProfileTransportAdapter();
        ordersAdapter = new OrdersAdapter();

        partnersListView.setLayoutManager(new LinearLayoutManager(this));
        transportListView.setLayoutManager(new LinearLayoutManager(this));
        ordersListView.setLayoutManager(new LinearLayoutManager(this));

        partnersListView.setNestedScrollingEnabled(false);
        transportListView.setNestedScrollingEnabled(false);
        ordersListView.setNestedScrollingEnabled(false);

        partnersListView.setAdapter(partnersAdapter);
        transportListView.setAdapter(transportAdapter);
        ordersListView.setAdapter(ordersAdapter);

        partnersAdapter.setVisibleCount(3);
        transportAdapter.setVisibleCount(2);
        ordersAdapter.setVisibleCount(2);

        List<Partner> partners = new ArrayList<>();
        partners.add(new Partner());
        partners.add(new Partner());
        partners.add(new Partner());
        partners.add(new Partner());
        partners.add(new Partner());
        partners.add(new Partner());

        partnersAdapter.setItems(partners);

        transportAdapter.setListener((pos, transport) -> TransportActivity.start(ProfileActivity.this, transport.getId()));

        getPresenter().getMyOrders();
        getPresenter().getMyTransport();
        getPresenter().getProfile();

        preferencesHelper = new PrefRepoImpl(this);

    }


    @Override
    protected ProfileMvpPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo preferencesHelper = new PrefRepoImpl(this);
        LocalRepo localRepo = new LocalRepoImpl(this);
        DataManager dataManager = new DataManagerImpl(remoteRepo, preferencesHelper, localRepo);

        ProfilePresenter profilePresenter = new ProfilePresenter(dataManager, AndroidSchedulers.mainThread());
        profilePresenter.onAttach(this);

        return profilePresenter;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_see_all_partners:
                if (partnersAdapter.getItemCount() != partnersAdapter.getVisibleCount())
                    partnersAdapter.collapse();
                else {
                    partnersAdapter.showAll();
                    seeAllPartners.setVisibility(View.GONE);
                    seeAllPartners.setText("Скрыть все");
                }

                break;
            case R.id.btn_see_all_transport:
                if (transportAdapter.getItemCount() != transportAdapter.getVisibleCount())
                    transportAdapter.collapse();
                else {
                    transportAdapter.showAll();
                    seeAllTransport.setVisibility(View.GONE);
                    seeAllTransport.setText("Скрыть все");
                }
                break;
            case R.id.btn_see_all_orders:
                if (ordersAdapter.getItemCount() != ordersAdapter.getVisibleCount())
                    ordersAdapter.collapse();
                else {
                    ordersAdapter.showAll();
                    seeAllOrders.setVisibility(View.GONE);
                    seeAllOrders.setText("Скрыть все");
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getProfile();
        userLocMapView.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        userLocMapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        userLocMapView.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                ProfileSettingsActivity.start(this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMinZoomPreference(12);
        LatLng ny = new LatLng(40.7143528, -74.0059731);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ny));
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        googleMap.setOnMapClickListener(latLng -> openMap());
    }

    @Override
    public void onGetProfile(Profile profile) {
        if (profile.getFirstName() != null && !profile.getFirstName().equals("")) {
            String userName = String.format("%s %s", profile.getFirstName(), profile.getLastName());

            if (!userName.equals(" ")) {
                userNameTxtv.setText(userName);

                preferencesHelper.setUserName(userNameTxtv.getText().toString());

                StringBuilder shortName = new StringBuilder();

                String[] names = userName.split(" ");
                for (int i = 0; i < names.length; i++) {
                    if (i == 2) break;

                    if (names[i].length() > 1)
                        shortName.append(names[i].substring(0, 1));
                }

                if (!shortName.toString().equals("")) {
                    userShortNameTxtv.setText(shortName.toString());
                }

            }
        }
        if (profile.getDescription() != null)
            expandableTextView.setText(profile.getDescription());
    }

    @Override
    public void onGetUserOrders(List<Order> orderList) {
        if (orderList.size() <= 2)
            seeAllOrders.setVisibility(View.GONE);
        ordersAdapter.setItems(orderList);
    }

    @Override
    public void onGetUserTransport(List<Transport> transports) {
        if (transports.size() <= 2)
            seeAllTransport.setVisibility(View.GONE);
        transportAdapter.setItems(transports);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(place.getLatLng());
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                googleMap.addMarker(markerOptions);

            }
        }
    }
}
