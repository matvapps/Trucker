package com.foora.perevozkadev.ui.nav;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseActivity;
import com.foora.perevozkadev.ui.base.MvpPresenter;

public abstract class BaseNavPresenterActivity<T extends MvpPresenter> extends BaseActivity {

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

    @Override
    protected void onResume() {
        super.onResume();

    }

    @LayoutRes
    protected abstract int getContentViewLayoutId();

    protected abstract String getTitleStr();

    protected int getElevationDp() {
        return 0;
    }

    protected void setContentFrame() {
        getLayoutInflater().inflate(getContentViewLayoutId(), frameLayout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavDrawerFragment navDrawerFragment = NavDrawerFragment.newInstance();
                navDrawerFragment.show(getSupportFragmentManager());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected int getMenuResource() {
        return R.drawable.ic_action_menu;
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
        actionbar.setHomeAsUpIndicator(getMenuResource());
        actionbar.setTitle(getTitleStr());
        actionbar.setElevation(getElevationDp());

        switch (getMenuResource()) {
            case R.drawable.ic_action_menu: {
                toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.color_app_blue));
                break;
            }
            default:
                toolbar.setTitleTextColor(Color.WHITE);

    }

        setContentFrame();

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
