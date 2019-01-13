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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
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

    @BindView(R.id.btn_login)
    Button btnlogin;

    private Callback listener;

    @Override
    protected void setUp(View view) {
        initSpannableTextView();
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


        edtxtPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnlogin.setFocusable(true);
                btnlogin.setFocusableInTouchMode(true);
                btnlogin.requestFocus();
            }
        });

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

    @Override
    protected void onHideKeyboard() {
        super.onHideKeyboard();
        Log.d(TAG, "onHideKeyboard: ");
        if (registerTxtv != null)
            registerTxtv.getHandler().postDelayed(() -> registerTxtv.setVisibility(View.VISIBLE),25);
    }


    @Override
    protected void onShowKeyboard() {
        super.onShowKeyboard();
        Log.d(TAG, "onShowKeyboard: ");
        if (registerTxtv != null)
            registerTxtv.setVisibility(View.GONE);
    }

    // EntryActivity must implement this interface
    public interface Callback extends FragmentCallback {
        void onTryLogin(String login, String password);

        void onCallRegister();
    }


}
