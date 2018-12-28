package com.foora.perevozkadev.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.add_order.AddOrderActivity;
import com.foora.perevozkadev.ui.employees.EmployeesActivity;
import com.foora.perevozkadev.ui.my_orders.MyOrdersActivity;
import com.foora.perevozkadev.ui.my_transport.MyTransportActivity;
import com.foora.perevozkadev.ui.profile.ProfileActivity;
import com.foora.perevozkadev.ui.search_order.SearchOrderActivity;

public abstract class BasePresenterNavActivity<T extends MvpPresenter> extends BaseActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FrameLayout frameLayout;

    private T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_navigation);

        setUp();

    }

    @LayoutRes
    protected abstract int getContentViewLayoutId();

    protected abstract String getTitleStr();

    protected void setContentFrame() {
        getLayoutInflater().inflate(getContentViewLayoutId(), frameLayout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setUp() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        frameLayout = findViewById(R.id.content_view);

//        setUnBinder(ButterKnife.bind(this));


//        setSupportActionBar((Toolbar) LayoutInflater.from(this).inflate(getToolbarLayoutId(), null, false));

        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_action_menu);
        actionbar.setTitle(getTitleStr());

        setContentFrame();

        View headerview = navigationView.getHeaderView(0);
        headerview.setOnClickListener(view -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            ProfileActivity.start(BasePresenterNavActivity.this);
            finish();
        });

        navigationView.setNavigationItemSelectedListener(menuItem -> {

            menuItem.setChecked(true);
            drawerLayout.closeDrawers();
            switch (menuItem.getItemId()) {
                case R.id.create_order:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    AddOrderActivity.start(BasePresenterNavActivity.this);
                    finish();
                    break;
                case R.id.search_orders:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    SearchOrderActivity.start(BasePresenterNavActivity.this);
                    finish();
                    break;
                case R.id.my_transport:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    MyTransportActivity.start(BasePresenterNavActivity.this);
                    finish();
                    break;
                case R.id.staff:
                    drawerLayout.closeDrawer(Gravity.START);
                    EmployeesActivity.start(BasePresenterNavActivity.this);
                    finish();
                    break;
                case R.id.my_orders:
                    drawerLayout.closeDrawer(Gravity.START);
                    MyOrdersActivity.start(BasePresenterNavActivity.this);
                    finish();
                    break;
            }

            return true;
        });
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @NonNull
    protected T getPresenter() {
        if (presenter == null)
            presenter = createPresenter();
        if (presenter == null) {
            throw new IllegalStateException("createPresenter() implementation returns null!");
        }
        return presenter;
    }

    protected abstract T createPresenter();
}
