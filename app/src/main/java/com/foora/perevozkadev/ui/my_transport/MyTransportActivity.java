package com.foora.perevozkadev.ui.my_transport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BasePresenterActivity;
import com.foora.perevozkadev.ui.base.BasePresenterNavActivity;

import butterknife.ButterKnife;

public class MyTransportActivity extends BasePresenterNavActivity<MyTransportMvpPresenter> implements MyTransportMvpView {

    public static final String TAG = MyTransportActivity.class.getSimpleName();


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, MyTransportActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUnBinder(ButterKnife.bind(this));

    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_my_transport;
    }

    @Override
    protected String getTitleStr() {
        return "Мой транспорт";
    }

    @Override
    protected void setUp() {


    }


    @Override
    protected MyTransportMvpPresenter createPresenter() {
        return null;
    }


}
