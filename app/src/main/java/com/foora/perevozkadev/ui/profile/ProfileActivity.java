package com.foora.perevozkadev.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.network.model.ProfileResponse;
import com.foora.perevozkadev.data.prefs.PreferencesHelper;
import com.foora.perevozkadev.data.prefs.SharedPrefsHelper;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.BasePresenterNavActivity;
import com.foora.perevozkadev.ui.my_transport.model.Transport;
import com.foora.perevozkadev.ui.profile.adapter.PartnersAdapter;
import com.foora.perevozkadev.ui.profile.adapter.TransportAdapter;
import com.foora.perevozkadev.ui.profile.model.Partner;
import com.foora.perevozkadev.ui.search_order.orders.OrdersAdapter;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ProfileActivity extends BasePresenterNavActivity<ProfileMvpPresenter>
        implements ProfileMvpView, View.OnClickListener {

    public static final String TAG = ProfileActivity.class.getSimpleName();


    private ExpandableTextView expandableTextView;
    private RecyclerView partnersListView;
    private RecyclerView transportListView;
    private RecyclerView ordersListView;

    private PartnersAdapter partnersAdapter;
    private TransportAdapter transportAdapter;
    private OrdersAdapter ordersAdapter;

    private Button seeAllPartners;
    private Button seeAllTransport;
    private Button seeAllOrders;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ProfileActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUnBinder(ButterKnife.bind(this));

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
        expandableTextView.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

        seeAllOrders = findViewById(R.id.btn_see_all_orders);
        seeAllPartners = findViewById(R.id.btn_see_all_partners);
        seeAllTransport = findViewById(R.id.btn_see_all_transport);

        seeAllTransport.setOnClickListener(this);
        seeAllPartners.setOnClickListener(this);
        seeAllOrders.setOnClickListener(this);

        partnersListView = findViewById(R.id.partners_list_view);
        transportListView = findViewById(R.id.transport_list);
        ordersListView = findViewById(R.id.order_list);

        partnersAdapter = new PartnersAdapter();
        transportAdapter = new TransportAdapter();
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

        List<Transport> transports = new ArrayList<>();
        transports.add(new Transport());
        transports.add(new Transport());
        transports.add(new Transport());

        List<Order> orders = new ArrayList<>();

        orders.add(new Order());
        orders.add(new Order());

        partnersAdapter.setItems(partners);
        transportAdapter.setItems(transports);
        ordersAdapter.setItems(orders);

        getPresenter().getMyOrders();
        getPresenter().getMyTransport();
        getPresenter().getProfile();

    }


    @Override
    protected ProfileMvpPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PreferencesHelper preferencesHelper = new SharedPrefsHelper(this);

        DataManager dataManager = new DataManagerImpl(remoteRepo, preferencesHelper);

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
    public void onGetProfile(ProfileResponse profile) {

    }

    @Override
    public void onGetUserOrders(List<Order> orderList) {

    }

    @Override
    public void onGetUserTransport(List<Transport> transports) {

    }
}
