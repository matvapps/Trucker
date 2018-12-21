package com.foora.perevozkadev.ui.sample_mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BasePresenterActivity;

import butterknife.ButterKnife;

public class SampleActivity extends BasePresenterActivity<SampleMvpPresenter> implements SampleMvpView {

    public static final String TAG = SampleActivity.class.getSimpleName();


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, SampleActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setUnBinder(ButterKnife.bind(this));

    }

    @Override
    protected void setUp() {


    }


    @Override
    protected SampleMvpPresenter createPresenter() {
        return null;
    }


}
