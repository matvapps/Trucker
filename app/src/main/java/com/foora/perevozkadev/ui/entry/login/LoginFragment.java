package com.foora.perevozkadev.ui.entry.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ScrollView;
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

    @BindView(R.id.login_input)
    AppEditText edtxtLogin;

    @BindView(R.id.password_input)
    AppEditText edtxtPassword;

    @BindView(R.id.register_txtv)
    TextView registerTxtv;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    private Callback listener;

    @Override
    protected void setUp(View view) {
        initSpannableTextView();
        scrollView.setEnabled(false);
        edtxtPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginUser();
                return true;
            }
            return false;
        });
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
        String spanString = "Создайте учетную запись если у Вас её ещё нет или же свяжитесь с тех. поддержкой";
        String createHereStr = "Создайте учетную запись";
        String callManagerStr = "свяжитесь с тех. поддержкой";

        SpannableString registerSpannable = new SpannableString(spanString);

        ClickableSpan createAccountSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                listener.onCallRegister();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(ds.linkColor);
                ds.setUnderlineText(false);
            }
        };

        ClickableSpan callManagerSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                listener.onCallManager();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(ds.linkColor);
                ds.setUnderlineText(false);
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
        String login = edtxtLogin.getText();
        String password = edtxtPassword.getText();

        if (!login.isEmpty() && !password.isEmpty()) {
            if (password.length() >= 6) {
                listener.onTryLogin(login, password);
            } else {
                onError("Пароль не может быть менее 6 символов");
            }
        } else {
            onError("Все поля должны быть заполнены");
        }

    }

    // EntryActivity must implement this interface
    public interface Callback extends FragmentCallback {
        void onTryLogin(String login, String password);

        void onCallRegister();
    }


}
