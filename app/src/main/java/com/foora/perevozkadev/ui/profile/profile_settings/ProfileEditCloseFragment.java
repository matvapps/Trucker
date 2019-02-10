package com.foora.perevozkadev.ui.profile.profile_settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.add_transport.SuccessDialogFragment;
import com.foora.perevozkadev.ui.base.BaseDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alexandr.
 */
public class ProfileEditCloseFragment extends BaseDialog {
    public static final String TAG = SuccessDialogFragment.class.getSimpleName();

    private Callback listener;

    public static ProfileEditCloseFragment newInstance() {
        ProfileEditCloseFragment fragment = new ProfileEditCloseFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_edit_close, container, false);

        setUnBinder(ButterKnife.bind(this, view));

        return view;
    }

    @OnClick(R.id.action_ok)
    void onClick() {
        listener.onSaveAndExit();
    }

    @OnClick(R.id.action_cancel)
    void onCancelClick() {
        listener.onDiscardAndExit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (Callback) context;

    }

    @Override
    protected void setUp(View view) {
        getDialog().setCanceledOnTouchOutside(true);
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    public void dismissDialog() {
        super.dismissDialog(TAG);
    }

    public interface Callback {
        void onSaveAndExit();
        void onDiscardAndExit();
    }
}
