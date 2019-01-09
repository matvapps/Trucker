package com.foora.perevozkadev.utils.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
        TypedArray a = context.obtainStyledAttributes(attrs, com.github.matvapps.R.styleable.AppEditText);

        hint = a.getString(com.github.matvapps.R.styleable.AppEditText_aet_hint);
        text = a.getString(com.github.matvapps.R.styleable.AppEditText_aet_text);
        inputType = InputType.fromId(a.getInt(com.github.matvapps.R.styleable.AppEditText_aet_inputType, 3));
        singleLine = a.getBoolean(com.github.matvapps.R.styleable.AppEditText_aet_singleLine, true);
        lines = a.getInteger(com.github.matvapps.R.styleable.AppEditText_aet_lines, 1);
        minLines = a.getInteger(com.github.matvapps.R.styleable.AppEditText_aet_minLines, 1);
        imeOption = ImeOption.fromId(a.getInt(com.github.matvapps.R.styleable.AppEditText_aet_imeOption, 7));

        a.recycle();
    }

    public void init() {
        View view = inflate(getContext(), com.github.matvapps.R.layout.edit_text_layout, this);

        editText = view.findViewById(com.github.matvapps.R.id.edtxt);

        setSingleLine(singleLine);
        setInputType(inputType);
        setImeOption(imeOption);
        setMinLines(minLines);
        setLines(lines);
        setHint(hint);
        setText(text);


        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setActivated(true);

                if (!hasFocus && editText.getText().toString().isEmpty()) {
                    setActivated(false);
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
        editText.setHint(hint);
    }

    public void setText(String text) {
        this.text = text;
        editText.setText(text);
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
                return;
            case EMAIL:
                editText.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case PASSWORD:
                editText.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case PHONE:
                editText.setInputType(android.text.InputType.TYPE_CLASS_PHONE);
                break;
            case TEXT_MULTILINE:
                editText.setInputType(android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                editText.setGravity(Gravity.TOP | Gravity.START);
                break;
        }
    }

    public String getText() {
        return text;
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
        PASSWORD(0), PHONE(1), EMAIL(2), TEXT(3), TEXT_MULTILINE(4);
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
