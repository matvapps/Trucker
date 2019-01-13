package com.foora.perevozkadev.ui.entry.register;

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
public class RegisterFragment extends BaseFragment {

    public static final String TAG = RegisterFragment.class.getSimpleName();

    @BindView(R.id.login_input)
    AppEditText edtxtLogin;

    @BindView(R.id.password_input)
    AppEditText edtxtPassword;

    @BindView(R.id.call_manager_txtv)
    TextView callManagerTxtv;

    @BindView(R.id.login_txtv)
    TextView loginTxtv;

    @BindView(R.id.txtv_container)
    View txtvContainer;

    private Callback listener;

    public static RegisterFragment newInstance() {
        Bundle args = new Bundle();
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);

        setUnBinder(ButterKnife.bind(this, view));
        initSpannableTextView();

        edtxtPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                registerUser();
                return true;
            }
            return false;
        });

        return view;
    }

    private void initSpannableTextView() {
        String managerTextStr = "Если вы работаете в команде для регистрации\nсвяжитесь с нашим менеджером";
        String loginTextStr = "Войдите если у вас уже есть учетная запись";

        String callManager = "свяжитесь с нашим менеджером";
        String login = "Войдите";

        SpannableString managerSpannable = new SpannableString(managerTextStr);
        SpannableString loginSpannable = new SpannableString(loginTextStr);

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

        ClickableSpan loginSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                listener.onCallLogin();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(ds.linkColor);
                ds.setUnderlineText(false);
            }
        };

        int callManagerStart = managerTextStr.indexOf(callManager);
        int callManagerEnd = callManagerStart + callManager.length();
        managerSpannable.setSpan(callManagerSpan, callManagerStart, callManagerEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int loginStart = loginTextStr.indexOf(login);
        int loginEnd = loginStart + login.length();
        loginSpannable.setSpan(loginSpan, loginStart, loginEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        callManagerTxtv.setText(managerSpannable);
        loginTxtv.setText(loginSpannable);

        callManagerTxtv.setMovementMethod(LinkMovementMethod.getInstance());
        loginTxtv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @OnClick(R.id.btn_register)
    void registerUser() {
        String login = edtxtLogin.getText();
        String password = edtxtPassword.getText();

        if (!login.isEmpty() && !password.isEmpty()) {
            if (password.length() >= 6) {
                listener.onTryRegister(login, password, "owner", "1");
            } else {
                onError("Пароль должен быть не менее 6 символов");
            }
        } else {
            onError("Все поля должны быть заполнены");
        }

    }

    @Override
    protected void onHideKeyboard() {
        super.onHideKeyboard();
        Log.d(TAG, "onHideKeyboard: ");
        if (txtvContainer != null)
            txtvContainer.getHandler().postDelayed(() -> txtvContainer.setVisibility(View.VISIBLE),25);
    }


    @Override
    protected void onShowKeyboard() {
        super.onShowKeyboard();
        Log.d(TAG, "onShowKeyboard: ");
        if (txtvContainer != null)
            txtvContainer.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (Callback) context;
    }

    @Override
    protected void setUp(View view) {

    }

    // EntryActivity must implement this interface
    public interface Callback extends FragmentCallback {
        void onTryRegister(String login, String password, String group, String user_type);

        void onCallLogin();
    }


}
