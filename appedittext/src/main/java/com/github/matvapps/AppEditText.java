package com.github.matvapps;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
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
    private boolean singleLine;
    private int minLines;
    private int lines;

    private TextInputEditText editText;
    private TextInputLayout textInputLayout;

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
        singleLine = a.getBoolean(R.styleable.AppEditText_aet_singleLine, true);
        lines = a.getInteger(R.styleable.AppEditText_aet_lines, 1);
        minLines = a.getInteger(R.styleable.AppEditText_aet_minLines, 1);
        imeOption = ImeOption.fromId(a.getInt(R.styleable.AppEditText_aet_imeOption, 7));

        a.recycle();
    }

    public void init() {
        View view = inflate(getContext(), R.layout.app_edit_text, this);

        editText = view.findViewById(R.id.edtxt);
        textInputLayout = view.findViewById(R.id.edtxt_input);

//        setSingleLine(singleLine);
        setInputType(inputType);
        setImeOption(imeOption);
//        setMinLines(minLines);
//        setLines(lines);
        setHint(hint);
        setText(text);

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

    public void setMinLines(int minLines) {
        this.minLines = minLines;
        editText.setMinLines(minLines);
    }

    public void setLines(int lines) {
        this.lines = lines;
        editText.setLines(lines);
    }


    public void setHint(String hint) {
        this.hint = hint;
        textInputLayout.setHint(hint);
//        editText.setHint(hint);
    }

    public void setText(String text) {
        this.text = text;
        editText.setText(text);
        if (text != null)
            if (!text.isEmpty())
                setActivated(true);
    }

    public void setSingleLine(boolean singleLine) {
        this.singleLine = singleLine;
        editText.setSingleLine(singleLine);
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
                editText.setSingleLine(true);
                return;
            case EMAIL:
                editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                editText.setSingleLine(true);
                break;
            case PASSWORD:
                editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText.setTransformationMethod(new AsteriskPasswordTransformationMethod());
                editText.setSingleLine(true);
                break;
            case PHONE:
                editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_CLASS_PHONE);
                editText.setSingleLine(true);
                break;
            case TEXT_MULTILINE:
                editText.setGravity(Gravity.TOP);
                editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT |android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                editText.setSingleLine(false);
                break;
            case NUMBER:
                editText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
                editText.setSingleLine(true);
                break;
        }
    }

    public String getText() {
        return editText.getText().toString();
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
