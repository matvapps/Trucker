package com.foora.perevozkadev.ui.search_order;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.db.LocalRepo;
import com.foora.perevozkadev.data.db.LocalRepoImpl;
import com.foora.perevozkadev.data.db.model.FilterJson;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PrefRepo;
import com.foora.perevozkadev.data.prefs.PrefRepoImpl;
import com.foora.perevozkadev.service.location.DriverLocationService;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.nav.BaseNavPresenterActivity;
import com.foora.perevozkadev.ui.profile.model.Profile;
import com.foora.perevozkadev.ui.search_order.filter.FilterFragment;
import com.foora.perevozkadev.ui.search_order.filter.model.Filter;
import com.foora.perevozkadev.ui.search_order.filter_dialog.FilterDialogFragment;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchOrderActivity extends BaseNavPresenterActivity<SearchOrderMvpPresenter>
        implements SearchOrderMvpView, FilterFragment.Callback {

    public static final String TAG = SearchOrderActivity.class.getSimpleName();

    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    private View firstTab;

    private SearchOrderPagerAdapter searchOrderPagerAdapter;
    private List<FilterJson> savedFilters;
    public DriverLocationService gpsService;
    private Gson gson;

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.ACCESS_NETWORK_STATE
    };

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, SearchOrderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestPermissionsSafely(PERMISSIONS, PERMISSION_ALL);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setUnBinder(ButterKnife.bind(this));
    }

    @Override
    protected int getMenuResource() {
        return R.drawable.ic_menu;
    }

    @Override
    protected void setUp() {
        super.setUp();

        getPresenter().getProfile();

        gson = new Gson();
        savedFilters = new ArrayList<>();

        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tablayout);
        firstTab = getLayoutInflater().inflate(R.layout.custom_tab, null);

        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter());

        searchOrderPagerAdapter = new SearchOrderPagerAdapter(getSupportFragmentManager(), filters);
        viewPager.setAdapter(searchOrderPagerAdapter);

        viewPager.setCurrentItem(1, true);
        tabLayout.setupWithViewPager(viewPager);
//        View v = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabLayout.getTabAt(0).setCustomView(R.layout.custom_tab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    ((ImageView) firstTab.findViewById(R.id.image))
                            .setColorFilter(ContextCompat.getColor(SearchOrderActivity.this, R.color.colorAccent));
                    ((ImageView) firstTab.findViewById(R.id.image)).setAlpha(1f);
                    if (tabLayout.getRootView().findViewById(R.id.image) != null) {
                        tabLayout.getTabAt(0).setCustomView(firstTab);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    ((ImageView) firstTab.findViewById(R.id.image)).setColorFilter(Color.WHITE);
                    ((ImageView) firstTab.findViewById(R.id.image)).setAlpha(0.54f);
                    tabLayout.getTabAt(0).setCustomView(firstTab);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getPresenter().getFilters();
        hideLoading();
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected String getTitleStr() {
        return "Поиск заказов";
    }

    @Override
    protected SearchOrderMvpPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo preferencesHelper = new PrefRepoImpl(this);
        LocalRepo localRepo = new LocalRepoImpl(this);
        DataManager dataManager = new DataManagerImpl(remoteRepo, preferencesHelper, localRepo);

        SearchOrderPresenter searchOrderPresenter = new SearchOrderPresenter<>(dataManager, AndroidSchedulers.mainThread());
        searchOrderPresenter.onAttach(this);

        Log.d(TAG, "createPresenter: token " + preferencesHelper.getUserToken());

        return searchOrderPresenter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_order_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cancel:
                if (viewPager.getCurrentItem() != 0) {
                    searchOrderPagerAdapter.remove(viewPager.getCurrentItem());

                    if (viewPager.getCurrentItem() == 0) {
                        ((ImageView) firstTab.findViewById(R.id.image))
                                .setColorFilter(ContextCompat.getColor(SearchOrderActivity.this, R.color.colorAccent));
                        ((ImageView) firstTab.findViewById(R.id.image)).setAlpha(1f);
                        tabLayout.getTabAt(0).setCustomView(firstTab);
                    } else {
                        ((ImageView) firstTab.findViewById(R.id.image)).setColorFilter(Color.WHITE);
                        ((ImageView) firstTab.findViewById(R.id.image)).setAlpha(0.54f);
                        tabLayout.getTabAt(0).setCustomView(firstTab);
                    }


                    Filter filter = searchOrderPagerAdapter.getItem(viewPager.getCurrentItem());
                    FilterJson filterJson = new FilterJson();
                    filterJson.setJson(gson.toJson(filter));
                    filterJson.setId(savedFilters.get(viewPager.getCurrentItem()).getId());
                    getPresenter().deleteFilter(filterJson);
                }
                break;
            case R.id.map:

                break;
            case R.id.filter:
                FilterDialogFragment filterDialogFragment = FilterDialogFragment.newInstance();
                filterDialogFragment.show(getSupportFragmentManager());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetOrders(List<Order> orders) {


    }

    @Override
    public void onGetFilters(List<FilterJson> filters) {
        savedFilters.clear();
        searchOrderPagerAdapter.clear();
        savedFilters.addAll(filters);


        searchOrderPagerAdapter.add(new Filter());

        for (FilterJson item : filters) {
            Filter filter = gson.fromJson(item.getJson(), Filter.class);
            searchOrderPagerAdapter.add(filter);
        }

        if (savedFilters.isEmpty()) {
            onCreateNewFilter(new Filter());
        } else {
            viewPager.setCurrentItem(1, true);
        }

    }

    @Override
    public void onGetProfile(Profile profile) {

        subscribeToNotifications(profile.getUserId());

        if (profile.getGroups().contains("driver")) {
            final Intent intent = new Intent(this.getApplication(), DriverLocationService.class);
            this.getApplication().startService(intent);
//        this.getApplication().startForegroundService(intent);
            this.getApplication().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            String name = className.getClassName();
            if (name.endsWith("DriverLocationService")) {
//                gpsService = ((DriverLocationService.LocationServiceBinder) service).getService();
//                gpsService.startTracking();


//                btnStartTracking.setEnabled(true);
//                txtStatus.setText("GPS Ready");
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            if (className.getClassName().equals("DriverLocationService")) {
                gpsService = null;
            }
        }
    };

    @Override
    public void onCreateNewFilter(Filter filter) {
        FilterJson filterJson = new FilterJson();
        filterJson.setJson(gson.toJson(filter));
        getPresenter().addFilter(filterJson);

        savedFilters.add(filterJson);
        searchOrderPagerAdapter.add(filter);
        viewPager.setCurrentItem(searchOrderPagerAdapter.getCount() - 1, true);
    }

    public void subscribeToNotifications(int userId) {
        OkHttpClient okHttpClient = new OkHttpClient();

        // Create okhttp3 form body builder.
        FormBody.Builder formBodyBuilder = new FormBody.Builder();

// Add form parameters
        formBodyBuilder.add("app_id", "a92ce8f9-6f05-435b-a7bb-5253c607dfdd");
        formBodyBuilder.add("device_type", "1");
;       formBodyBuilder.add("external_user_id", String.valueOf(userId));

// Build form body.
        FormBody formBody = formBodyBuilder.build();

// Create a http request object.
        Request.Builder builder = new Request.Builder();
        builder = builder.url("https://onesignal.com/api/v1/players");
        builder = builder.post(formBody);
        Request request = builder.build();

// Create a new Call object with post method.
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse subscribeToNotification: " + response.toString() + "\n" + call.request().toString());
                Log.d(TAG, "onResponse subscribeToNotification: " + call.request().headers() + "\n" );

            }
        });
    }

}
