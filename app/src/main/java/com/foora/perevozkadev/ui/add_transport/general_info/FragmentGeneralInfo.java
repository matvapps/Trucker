package com.foora.perevozkadev.ui.add_transport.general_info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by Alexandr.
 */
public class FragmentGeneralInfo extends BaseFragment {

    public static FragmentGeneralInfo newInstance() {
        Bundle args = new Bundle();
        FragmentGeneralInfo fragment = new FragmentGeneralInfo();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transport_general_info, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    protected void setUp(View view) {

    }
}
