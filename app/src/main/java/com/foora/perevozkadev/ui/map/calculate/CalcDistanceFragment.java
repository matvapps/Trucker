package com.foora.perevozkadev.ui.map.calculate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BasePresenterFragment;
import com.foora.perevozkadev.ui.messages.MessagesFragment;

public class CalcDistanceFragment extends BasePresenterFragment<CalcDistanceMvpPresenter> implements CalcDistanceMvpView {

    public static final String TAG = CalcDistanceFragment.class.getSimpleName();


    public static CalcDistanceFragment newInstance() {
        Bundle args = new Bundle();
        CalcDistanceFragment fragment = new CalcDistanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calc_distance, container, false);



        return rootView;

    }

    @Override
    protected CalcDistanceMvpPresenter createPresenter() {
        return null;
    }


}
