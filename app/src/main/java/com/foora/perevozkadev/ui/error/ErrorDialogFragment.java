package com.foora.perevozkadev.ui.error;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alexandr.
 */
public class ErrorDialogFragment extends BaseDialog implements ErrorMvpView {

    public static final String TAG = ErrorDialogFragment.class.getSimpleName();

    public static final String MESSAGE_KEY = "message_key";

    private String message;

    @BindView(R.id.error_message)
    TextView textView;

    public static ErrorDialogFragment newInstance(String message) {
        ErrorDialogFragment fragment = new ErrorDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MESSAGE_KEY, message);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_error, container, false);

        message = getArguments().getString(MESSAGE_KEY);

        setUnBinder(ButterKnife.bind(this, view));

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    protected void setUp(View view) {
        textView.setText(message);
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
