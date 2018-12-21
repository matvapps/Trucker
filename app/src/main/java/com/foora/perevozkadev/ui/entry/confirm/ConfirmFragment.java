package com.foora.perevozkadev.ui.entry.confirm;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alexandr.
 */
public class ConfirmFragment extends BaseFragment {

    public static final String TAG = ConfirmFragment.class.getSimpleName();

    private Callback listener;

    @BindView(R.id.edtxt_phone)
    EditText phoneEdtxt;
    @BindView(R.id.edtxt_country_code)
    EditText countryCodeEdtxt;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    public static ConfirmFragment newInstance() {
        Bundle args = new Bundle();
        ConfirmFragment fragment = new ConfirmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);

        setUnBinder(ButterKnife.bind(this, view));

        return view;
    }

    @Override
    protected void setUp(View view) {

    }

    @OnClick(R.id.btn_confirm)
    void onClick() {
        String phone = phoneEdtxt.getText().toString();
        String phoneCode = countryCodeEdtxt.getText().toString();

        if (phone.isEmpty() || phoneCode.isEmpty()) {
            onError("Заполните все поля");
        } else {
            if (listener != null)
                listener.onReceivePhone(phoneCode + phone);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (Callback) context;
    }

    public Callback getListener() {
        return listener;
    }

    public void setListener(Callback listener) {
        this.listener = listener;
    }

    public interface Callback {
        void onReceivePhone(String phone);
    }
}
