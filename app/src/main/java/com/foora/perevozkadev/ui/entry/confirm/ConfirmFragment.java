package com.foora.perevozkadev.ui.entry.confirm;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseFragment;
import com.github.matvapps.AppEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alexandr.
 */
public class ConfirmFragment extends BaseFragment {

    public static final String TAG = ConfirmFragment.class.getSimpleName();

    private Callback listener;

    @BindView(R.id.phone)
    AppEditText phoneEdtxt;

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

        phoneEdtxt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onClick();
                return true;
            }
            return false;
        });

        return view;
    }

    @Override
    protected void setUp(View view) {

    }

    @OnClick(R.id.btn_confirm)
    void onClick() {
        String phone = phoneEdtxt.getText();

        if (phone.isEmpty()) {
            onError("Нужно ввести номер телефона");
        } else {
            if (listener != null)
                listener.onReceivePhone(phone);
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
