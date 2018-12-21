package com.foora.perevozkadev.ui.search_order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PreferencesHelper;
import com.foora.perevozkadev.data.prefs.SharedPrefsHelper;
import com.foora.perevozkadev.ui.add_order.AddOrderPresenter;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.BasePresenterNavActivity;
import com.foora.perevozkadev.ui.search_order.filter.model.Filter;
import com.foora.perevozkadev.ui.search_order.filter_dialog.FilterDialogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SearchOrderActivity extends BasePresenterNavActivity<SearchOrderMvpPresenter> implements SearchOrderMvpView {

    public static final String TAG = SearchOrderActivity.class.getSimpleName();

    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    private SearchOrderPagerAdapter searchOrderPagerAdapter;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, SearchOrderActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUnBinder(ButterKnife.bind(this));

    }

    @Override
    protected void setUp() {
        super.setUp();

        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tablayout);

        tabLayout.setupWithViewPager(viewPager);

        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter());
        filters.add(new Filter());

        searchOrderPagerAdapter = new SearchOrderPagerAdapter(getSupportFragmentManager(), filters);
        viewPager.setAdapter(searchOrderPagerAdapter);

        viewPager.setCurrentItem(1, true);

        getPresenter().getOrders();

        //        PreferencesHelper preferencesHelper = new SharedPrefsHelper(this);
//
//        Log.d(TAG, "setUp: " + preferencesHelper.getUserToken());
//        searchOrderPagerAdapter.getItem()
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
        PreferencesHelper preferencesHelper = new SharedPrefsHelper(this);
        DataManager dataManager = new DataManagerImpl(remoteRepo, preferencesHelper);

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
        Log.d(TAG, "onGetOrders: " + orders.toString());


    }
}
