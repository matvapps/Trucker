package com.foora.perevozkadev.ui.entry.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseFragment;
import com.foora.perevozkadev.ui.entry.FragmentCallback;
import com.github.matvapps.AppEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alexandr.
 */
public class LoginFragment extends BaseFragment {

    public static final String TAG = LoginFragment.class.getSimpleName();

    @BindView(R.id.login)
    TextInputEditText edtxtLogin;

    @BindView(R.id.password_input)
    AppEditText edtxtPassword;

    @BindView(R.id.register_txtv)
    TextView registerTxtv;

    private Callback listener;

    @Override
    protected void setUp(View view) {
        initSpannableTextView();
    }

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        setUnBinder(ButterKnife.bind(this, view));
        return view;
    }

    private void initSpannableTextView() {
        String spanString = "Если у вас еще нет учетной записи создайте ее тут или свяжитесь с тех. поддержкой";
        String createHereStr = "создайте ее тут";
        String callManagerStr = "свяжитесь с тех. поддержкой";

        SpannableString registerSpannable = new SpannableString(spanString);

        ClickableSpan createAccountSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                listener.onCallRegister();
            }
        };

        ClickableSpan callManagerSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                showMessage("call manager");
                listener.onCallManager();
            }
        };

        int createHereStart = spanString.indexOf(createHereStr);
        int createHereEnd = createHereStart + createHereStr.length();

        registerSpannable.setSpan(createAccountSpan, createHereStart, createHereEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int callManagerStart = spanString.indexOf(callManagerStr);
        int callManagerEnd = callManagerStart + callManagerStr.length();

        registerSpannable.setSpan(callManagerSpan, callManagerStart, callManagerEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        registerTxtv.setText(registerSpannable);
        registerTxtv.setMovementMethod(LinkMovementMethod.getInstance());

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (Callback) context;
    }

    @OnClick(R.id.btn_login)
    void loginUser() {
        String login = edtxtLogin.getText().toString();
        String password = edtxtPassword.getText().toString();

        listener.onTryLogin(login, password);
    }

    // EntryActivity must implement this interface
    public interface Callback extends FragmentCallback {
        void onTryLogin(String login, String password);
        void onCallRegister();
    }


}
