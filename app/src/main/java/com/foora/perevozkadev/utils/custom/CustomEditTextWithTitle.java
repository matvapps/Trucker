package com.foora.perevozkadev.utils.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Alexander Matvienko on 11.12.2018.
 */
public class CustomEditTextWithTitle extends FrameLayout {

    private final String TAG = CustomEditTextWithTitle.class.getSimpleName();

    private String title;
    private String hint;

    @BindView(R.id.title)
    TextView titleTextView;

    @BindView(R.id.edittext)
    EditText contentEditText;

    public CustomEditTextWithTitle(@NonNull Context context) {
        super(context);
        init();
    }

    public CustomEditTextWithTitle(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getSyleableAttrs(context, attrs);
        init();
    }

    public CustomEditTextWithTitle(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getSyleableAttrs(context, attrs);
        init();
    }

    private void getSyleableAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomEditTextWithTitle);

        hint = typedArray.getString(
                R.styleable.CustomEditTextWithTitle_cetwt_hint);
        title = typedArray.getString(
                R.styleable.CustomEditTextWithTitle_cetwt_title);

        typedArray.recycle();
    }

    private void init() {
        inflate(getContext(), R.layout.custom_editext_with_title, this);

        ButterKnife.bind(this);

        titleTextView.setText(title);
        contentEditText.setHint(hint);
        contentEditText.setMaxLines(1);
        contentEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
    }

    public String getText() {
        return contentEditText.getText().toString();
    }

    public EditText getContentEditText() {
        return contentEditText;
    }
}
