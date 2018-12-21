package com.foora.perevozkadev.ui.entry.otp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseDialog;
import com.foora.perevozkadev.ui.entry.FragmentCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alexandr.
 */
public class OtpCodeDialogFragment extends BaseDialog implements OtpDialogMvpView {

    public static final String TAG = OtpCodeDialogFragment.class.getSimpleName();

    public static final String TYPE_KEY = "type_key";

    public static final String REGISTER = "register";
    public static final String LOGIN = "login";

    @BindView(R.id.pin_view)
    PinView pinView;

    @BindView(R.id.txtv_no_code)
    TextView textView;

    private String confirmType;
    private Callback listener;

    public static OtpCodeDialogFragment newInstance(String type) {
        OtpCodeDialogFragment fragment = new OtpCodeDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE_KEY, type);
        fragment.setArguments(bundle);
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_otp_code, container, false);

        if (getArguments() != null)
            confirmType = getArguments().getString(TYPE_KEY);

        setUnBinder(ButterKnife.bind(this, view));

        return view;
    }

    private void initSpannableTextView() {
        String managerTextStr = "Если код не приходит отправьте еще раз или свяжитесь с нашей тех. поддержкой";

        String resendCode = "отправьте еще раз";
        String callManager = "нашей тех. поддержкой";

        SpannableString managerSpannable = new SpannableString(managerTextStr);

        ClickableSpan callManagerSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                listener.onCallManager();
            }
        };

        ClickableSpan resendCodeSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {

            }
        };

        int callManagerStart = managerTextStr.indexOf(callManager);
        int callManagerEnd = callManagerStart + callManager.length();
        managerSpannable.setSpan(callManagerSpan, callManagerStart, callManagerEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int resendCodeStart = managerTextStr.indexOf(resendCode);
        int resendCodeEnd = resendCodeStart + resendCode.length();
        managerSpannable.setSpan(resendCodeSpan, resendCodeStart, resendCodeEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(managerSpannable);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }


    @Override
    protected void setUp(View view) {
        initSpannableTextView();
        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 4)
                    listener.onReceiveSmsCode(confirmType, editable.toString());
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (Callback) context;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @Override
    public void dismissDialog() {
        super.dismissDialog(TAG);
    }

    public interface Callback extends FragmentCallback {
        void onReceiveSmsCode(String type, String smsCode);
    }
}
