package com.foora.perevozkadev.ui.add_order.contact_info;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseFragment;
import com.foora.perevozkadev.utils.custom.CustomEditTextWithTitle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alexandr.
 */
public class ContactInfoFragment extends BaseFragment {

    public static final String TAG = ContactInfoFragment.class.getSimpleName();

    private Callback listener;

    @BindView(R.id.contact_company)
    EditText companyContactEdtxt;
    @BindView(R.id.contact_person)
    EditText personContactEdtxt;
    @BindView(R.id.contact_email)
    EditText emailContactEdtxt;
    @BindView(R.id.contact_phone)
    EditText phoneContactEdtxt;
    @BindView(R.id.contact_skype)
    EditText skypeContactEdtxt;
    @BindView(R.id.contact_telegram)
    EditText telegramContactEdtxt;
    @BindView(R.id.contact_whatsapp)
    EditText whatsappContactEdtxt;


    public static ContactInfoFragment newInstance() {
        Bundle args = new Bundle();
        ContactInfoFragment fragment = new ContactInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact_info, container, false);
        setUnBinder(ButterKnife.bind(this, rootView));
        return rootView;
    }

    @OnClick(R.id.btn_main)
    void onClick() {
        if (listener != null) {
            listener.onReceiveContactInfo(
                    companyContactEdtxt.getText().toString(),
                    personContactEdtxt.getText().toString(),
                    emailContactEdtxt.getText().toString(),
                    phoneContactEdtxt.getText().toString(),
                    skypeContactEdtxt.getText().toString(),
                    telegramContactEdtxt.getText().toString(),
                    whatsappContactEdtxt.getText().toString()
            );
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG, "onHiddenChanged: ");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Callback) context;
    }

    @Override
    protected void setUp(View view) {

    }

    public Callback getListener() {
        return listener;
    }

    public void setListener(Callback listener) {
        this.listener = listener;
    }

    public interface Callback {
        void onReceiveContactInfo(String company, String person,
                                  String email, String phone,
                                  String skype, String telegram,
                                  String whatsapp);
    }
}
