package com.foora.perevozkadev.ui.choose_transport;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseDialog;
import com.foora.perevozkadev.ui.error.ErrorMvpView;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alexander Matvienko on 24.01.2019.
 */
public class SuccessDialogFragment extends BaseDialog implements ErrorMvpView {

    public static final String TAG = SuccessDialogFragment.class.getSimpleName();

    public static SuccessDialogFragment newInstance() {
        SuccessDialogFragment fragment = new SuccessDialogFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_request_success, container, false);

        setUnBinder(ButterKnife.bind(this, view));

        return view;
    }

    @OnClick(R.id.action_ok)
    void onClick() {
        getActivity().finish();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    protected void setUp(View view) {
        getDialog().setCanceledOnTouchOutside(true);
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @Override
    public void dismissDialog() {
        super.dismissDialog(TAG);
    }

}

