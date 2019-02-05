package com.github.matvapps;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;

/**
 * Created by Alexandr.
 */
public class AppEditText extends FrameLayout {

    private static final String TAG = AppEditText.class.getSimpleName();

    private String hint;
    private String text;
    private InputType inputType;
    private ImeOption imeOption;

    private TextInputEditText editText;
    private TextInputLayout textInputLayout;

    private String stringParam;

    public AppEditText(@NonNull Context context) {
        super(context);
        init();
    }

    public AppEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getStyleableAttrs(context, attrs);
        init();
    }

    public AppEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getStyleableAttrs(context, attrs);
        init();
    }

    public void getStyleableAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AppEditText);

        hint = a.getString(R.styleable.AppEditText_aet_hint);
        text = a.getString(R.styleable.AppEditText_aet_text);
        inputType = InputType.fromId(a.getInt(R.styleable.AppEditText_aet_inputType, 3));
        imeOption = ImeOption.fromId(a.getInt(R.styleable.AppEditText_aet_imeOption, 7));
        stringParam = a.getString(R.styleable.AppEditText_aet_postfix);

        a.recycle();
    }

    public void init() {
        View view = inflate(getContext(), R.layout.app_edit_text, this);

        editText = view.findViewById(R.id.edtxt);
        textInputLayout = view.findViewById(R.id.edtxt_input);

        setInputType(inputType);
        setImeOption(imeOption);
        setHint(hint);
        setText(text);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (stringParam != null) {
                    String prefix = stringParam;
                    if (!s.toString().endsWith(prefix)) {
                        String cleanString;
                        String deletedPrefix = prefix.substring(0, prefix.length() - 1);
                        if (s.toString().startsWith(deletedPrefix)) {
                            cleanString = s.toString().replaceAll(deletedPrefix, "");
                        } else {
                            cleanString = s.toString().replaceAll(prefix, "");
                        }
                        editText.setText(String.format("%s%s", cleanString, prefix));
                        editText.setSelection(cleanString.length());
                    }
                }
            }
        });

        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setActivated(true);
                textInputLayout.setDefaultHintTextColor(ContextCompat.getColorStateList(getContext(), R.color.orange_yellow));

                if (!hasFocus && editText.getText().toString().isEmpty()) {
                    setActivated(false);
                    textInputLayout.setDefaultHintTextColor(ContextCompat.getColorStateList(getContext(), R.color.colorTextHint));
                }
            }
        });

    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        editText.setOnTouchListener(l);
    }

//    @Override
//    public void setOnClickListener(@Nullable View.OnClickListener l) {
//        editText.setOnClickListener(l);
//    }

    public void setHint(String hint) {
        this.hint = hint;
        textInputLayout.setHint(hint);
//        editText.setHint(hint);
    }

    public void setText(String text) {
        this.text = text;
        editText.setText(text);
        if (text != null)
            if (!text.isEmpty()) {
                setActivated(true);
                textInputLayout.setDefaultHintTextColor(ContextCompat.getColorStateList(getContext(), R.color.orange_yellow));
            }
    }


    public void setImeOption(ImeOption imeOption) {
        this.imeOption = imeOption;
        switch (imeOption) {
            case ACTION_OK:
                return;
            case ACTION_NEXT:
                editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                break;
            case ACTION_DONE:
                editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                break;
        }
    }

    public void setInputType(InputType inputType) {
        this.inputType = inputType;
        switch (inputType) {
            case TEXT:
                editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
                return;
            case EMAIL:
                editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case PASSWORD:
                editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText.setTransformationMethod(new AsteriskPasswordTransformationMethod());
                break;
            case PHONE:
                editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_CLASS_PHONE);
                break;
            case TEXT_MULTILINE:
                editText.setGravity(Gravity.TOP);
                editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                editText.setSingleLine(false);
                break;
            case NUMBER:
                editText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
                break;
        }
    }

    public String getText() {
        if (stringParam == null)
            return editText.getText().toString();
        else
            return editText.getText().toString().replace(stringParam, "");
    }

    public void setOnEditorActionListener(EditText.OnEditorActionListener listener) {
        editText.setOnEditorActionListener(listener);
    }

    public enum ImeOption {
        ACTION_DONE(5), ACTION_NEXT(6), ACTION_OK(7);
        int id;

        ImeOption(int id) {
            this.id = id;
        }

        static ImeOption fromId(int id) {
            for (ImeOption f : values()) {
                if (f.id == id) return f;
            }
            throw new IllegalArgumentException();
        }
    }

    public enum InputType {
        PASSWORD(0), PHONE(1), EMAIL(2), TEXT(3), TEXT_MULTILINE(4), NUMBER(10);
        int id;

        InputType(int id) {
            this.id = id;
        }

        static InputType fromId(int id) {
            for (InputType f : values()) {
                if (f.id == id) return f;
            }
            throw new IllegalArgumentException();
        }
    }

}
