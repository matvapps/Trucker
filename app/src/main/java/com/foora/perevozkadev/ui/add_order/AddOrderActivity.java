package com.foora.perevozkadev.ui.add_order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.db.LocalRepo;
import com.foora.perevozkadev.data.db.LocalRepoImpl;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PrefRepo;
import com.foora.perevozkadev.data.prefs.PrefRepoImpl;
import com.foora.perevozkadev.ui.add_order.cargo_info.CargoInfoFragment;
import com.foora.perevozkadev.ui.add_order.contact_info.ContactInfoFragment;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.add_order.model.Place;
import com.foora.perevozkadev.ui.add_order.route.RouteFragment;
import com.foora.perevozkadev.ui.nav.BaseNavPresenterActivity;
import com.foora.perevozkadev.ui.search_order.SearchOrderActivity;
import com.foora.perevozkadev.utils.custom.ViewPagerNoScroll;

import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AddOrderActivity extends BaseNavPresenterActivity<AddOrderMvpPresenter> implements AddOrderMvpView,
        RouteFragment.Callback, ContactInfoFragment.Callback, CargoInfoFragment.Callback {

    private ViewPagerNoScroll viewPager;
    private TabLayout tabLayout;

    private Order order;
    private AddOrderPagerAdapter addOrderPagerAdapter;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, AddOrderActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUnBinder(ButterKnife.bind(this));
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_add_order;
    }

    @Override
    protected String getTitleStr() {
        return "Новый заказ";
    }

    @Override
    protected int getElevationDp() {
        return 3;
    }

    @Override
    protected AddOrderMvpPresenter createPresenter() {
//        LocalRepo localRepo = new LocalRepoImpl(this);
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo preferencesHelper = new PrefRepoImpl(this);
        LocalRepo localRepo = new LocalRepoImpl(this);
        DataManager dataManager = new DataManagerImpl(remoteRepo, preferencesHelper, localRepo);

        AddOrderPresenter addOrderPresenter = new AddOrderPresenter(dataManager, AndroidSchedulers.mainThread());
        addOrderPresenter.onAttach(this);

        getToolbar().setTitleTextColor(ContextCompat.getColor(this, R.color.color_app_blue));

        return addOrderPresenter;
    }

    @Override
    protected void setUp() {
        super.setUp();
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        order = new Order();

        addOrderPagerAdapter = new AddOrderPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(addOrderPagerAdapter);
        viewPager.setPagingEnabled(false);

        LinearLayout tabStrip = (LinearLayout) tabLayout.getChildAt(0);
        tabStrip.setEnabled(false);
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener((v, event) -> true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_order_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cancel:
                SearchOrderActivity.start(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onReceiveRoutes(List<Place> loadingPlaces, List<Place> unloadingPlaces) {
        viewPager.setCurrentItem(1, true);

        order.setLoadingPlaces(loadingPlaces);
        order.setUnloadingPlaces(unloadingPlaces);

//        Gson gson = new Gson();
//
//        String jsonStr = "{\n" +
//                "    \"loading_places\": [\n" +
//                "        {\n" +
//                "            \"id\": 3,\n" +
//                "            \"name\": \"place1\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"id\": 4,\n" +
//                "            \"name\": \"place2\"\n" +
//                "        }\n" +
//                "    ],\n" +
//                "    \"unloading_places\": [\n" +
//                "        {\n" +
//                "            \"id\": 3,\n" +
//                "            \"name\": \"place1\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"id\": 4,\n" +
//                "            \"name\": \"place2\"\n" +
//                "        }\n" +
//                "    ],\n" +
//                "    \"status\": \"started\",\n" +
//                "    \"loading_date\": \"2018-01-02\",\n" +
//                "    \"unloading_date\": \"2018-01-01\",\n" +
//                "    \"cargo\": \"13\",\n" +
//                "    \"weight\": 231,\n" +
//                "    \"volume\": 123,\n" +
//                "    \"transport_type\": \"231\",\n" +
//                "    \"car_quantity\": 123,\n" +
//                "    \"cost\": 43,\n" +
//                "    \"currency\": \"123\",\n" +
//                "    \"distance\": 123,\n" +
//                "    \"size\": \"123\",\n" +
//                "    \"payment_type_1\": \"123\",\n" +
//                "    \"payment_type_2\": \"123\",\n" +
//                "    \"company_name\": \"123\",\n" +
//                "    \"contact_person\": \"213\",\n" +
//                "    \"email\": \"diaforetikus@gmail.com\",\n" +
//                "    \"docs\": \"123\",\n" +
//                "    \"loading\": \"231\",\n" +
//                "    \"conditions\": \"12321\",\n" +
//                "    \"additionally\": \"123\",\n" +
//                "    \"skype\": \"123\",\n" +
//                "    \"phone\": \"9263043081\",\n" +
//                "    \"telegram\": \"123\",\n" +
//                "    \"whatsapp\": \"123123\"\n" +
//                "}";
//
//        Order order = gson.fromJson(jsonStr, Order.class);
//        Log.d(TAG, "onReceiveRoutes: " + order.toString());
//
//        getPresenter().addOrder(order);

    }

    @Override
    public void onReceiveContactInfo(String company, String person, String email, String phone, String skype, String telegram, String whatsapp) {
        order.setCompanyName(company);
        order.setContactPerson(person);
        order.setEmail(email);
        order.setPhone(phone);
        order.setSkype(skype);
        order.setTelegram(telegram);
        order.setWhatsapp(telegram);

        getPresenter().addOrder(order);

        Log.d(TAG, "onReceiveContactInfo: " + order.toString());
    }

    @Override
    public void onReceiveCargoInfo(String dateStart, String dateEnd,
                                   float massFrom, float massTo,
                                   float volumeFrom, float volumeTo,
                                   List<String> transportTypes,
                                   float cost, String currency,
                                   int carQuant, float width,
                                   float height, float depth, String paymentType) {
        viewPager.setCurrentItem(2, true);

        Log.d(TAG, "onReceiveCargoInfo: " + dateStart + " " + dateEnd);
        Log.d(TAG, "onReceiveCargoInfo: " + massFrom + " " + massTo);
        Log.d(TAG, "onReceiveCargoInfo: " + volumeFrom + " " + volumeTo);

        String transportType = "";
        for (int i = 0; i < transportTypes.size(); i++) {
            if (i != transportTypes.size() - 1)
                transportType = transportTypes.get(i) + ",";
            else
                transportType = transportTypes.get(i);

            Log.d(TAG, "onReceiveCargoInfo: " + transportTypes.get(i));
        }

        Log.d(TAG, "onReceiveCargoInfo: " + cost + " " + currency);


        order.setLoadingDate(dateStart);
        order.setUnloadingDate(dateEnd);
        order.setWeightTo((double) massTo);
        order.setWeightFrom((double) massFrom);
        order.setVolumeTo((double) volumeTo);
        order.setVolumeFrom((double) volumeFrom);
        order.setTransportType(transportType);
        order.setCost((double) cost);
        order.setCurrency(currency);
        order.setCarQuantity(carQuant);
        order.setSize(String.format("%sx%sx%s", width, height, depth));
        // TODO: remove hardcoded
        order.setPaymentType1(paymentType);
        order.setPaymentType2("123");
        order.setDistance(0d);
        order.setCargo("Cargo");
        order.setStatus("0");


    }


    @Override
    public void onOrderAdd() {
        showMessage("Заказ успешно добавлен");
        SearchOrderActivity.start(this);
        finish();
    }
}
