package com.foora.perevozkadev.ui.add_order.contact_info;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseFragment;
import com.github.matvapps.AppEditText;

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
    AppEditText companyContactEdtxt;
    @BindView(R.id.contact_person)
    AppEditText personContactEdtxt;
    @BindView(R.id.contact_email)
    AppEditText emailContactEdtxt;
    @BindView(R.id.contact_phone)
    AppEditText phoneContactEdtxt;
    @BindView(R.id.contact_skype)
    AppEditText skypeContactEdtxt;
    @BindView(R.id.contact_telegram)
    AppEditText telegramContactEdtxt;
    @BindView(R.id.contact_whatsapp)
    AppEditText whatsappContactEdtxt;


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

            if (companyContactEdtxt.getText().isEmpty() ||
                    personContactEdtxt.getText().isEmpty() ||
                    emailContactEdtxt.getText().isEmpty() ||
                    phoneContactEdtxt.getText().isEmpty() ||
                    skypeContactEdtxt.getText().isEmpty() ||
                    telegramContactEdtxt.getText().isEmpty() ||
                    whatsappContactEdtxt.getText().isEmpty()) {

                onError("Заполните все поля");
                return;
            }


            listener.onReceiveContactInfo(
                    companyContactEdtxt.getText(),
                    personContactEdtxt.getText(),
                    emailContactEdtxt.getText(),
                    phoneContactEdtxt.getText(),
                    skypeContactEdtxt.getText(),
                    telegramContactEdtxt.getText(),
                    whatsappContactEdtxt.getText()
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
