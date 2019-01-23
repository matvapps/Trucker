package com.foora.perevozkadev.ui.search_order;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.nav.BaseNavPresenterActivity;
import com.foora.perevozkadev.ui.search_order.filter.FilterFragment;
import com.foora.perevozkadev.ui.search_order.filter.model.Filter;
import com.foora.perevozkadev.ui.search_order.filter_dialog.FilterDialogFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SearchOrderActivity extends BaseNavPresenterActivity<SearchOrderMvpPresenter>
        implements SearchOrderMvpView, FilterFragment.Callback {

    public static final String TAG = SearchOrderActivity.class.getSimpleName();

    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    private SearchOrderPagerAdapter searchOrderPagerAdapter;
    private List<FilterJson> savedFilters;
    private Gson gson;

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE
    };

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, SearchOrderActivity.class);
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

        gson = new Gson();
        savedFilters = new ArrayList<>();

        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tablayout);

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
                tabLayout.getTabAt(0).setCustomView(R.layout.custom_tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getPresenter().getFilters();
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
    public void onCreateNewFilter(Filter filter) {
        FilterJson filterJson = new FilterJson();
        filterJson.setJson(gson.toJson(filter));
        getPresenter().addFilter(filterJson);

        savedFilters.add(filterJson);
        searchOrderPagerAdapter.add(filter);
        viewPager.setCurrentItem(searchOrderPagerAdapter.getCount() - 1, true);
    }
}
