package com.foora.perevozkadev.ui.add_transport.register_info;

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
public class FragmentRegisterInfo extends BaseFragment {

    public static FragmentRegisterInfo newInstance() {
        Bundle args = new Bundle();
        FragmentRegisterInfo fragment = new FragmentRegisterInfo();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transport_register_info, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    protected void setUp(View view) {

    }
}
