package com.foora.perevozkadev.utils.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;

import java.lang.reflect.Field;

/**
 * Created by Alexandr.
 */
public class ExpandableTextView extends FrameLayout {

    private static final String TAG = ExpandableTextView.class.getSimpleName();
    private static final String mEllipsis = "\u2026";
    private static final String MAXIMUM_VAR_NAME = "mMaximum";

    private TextView mTxtDescription;
    /** if this is true, show the imgExpand ImageView even if the text fits in the textview */
    private boolean mAlwaysShowingImgExpand;

    private boolean mIsExpanded;
    private Integer mMaxLine;
    private CharSequence mOriginalText;
    private ViewTreeObserver mViewTreeObserver;
    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener;

    /**
     * @param context
     */
    public ExpandableTextView(Context context) {
        super(context);
        init(context, null, 0);
    }

    /**
     * @param context
     * @param attrs
     */
    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public ExpandableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    /**
     * @return is expanded or not
     */
    public Boolean isExpanded() {
        return mIsExpanded;
    }

    public void collapse() {
        mIsExpanded = false;
        mTxtDescription.setMaxLines(mMaxLine);
        postInvalidate();
        //The actual change is in onMeasure
    }

    public void expand() {
        mIsExpanded = true;
        if (mMaxLine == null) {
            storeMaxLine();
        }

        mTxtDescription.setEllipsize(null);
        mTxtDescription.setMaxLines(Integer.MAX_VALUE);
        mTxtDescription.setText(mOriginalText);
        postInvalidate();
    }

    public void setText(CharSequence text) {
        setText(text, false);
    }

    public void setText(CharSequence text, final boolean alwaysShowImgExpand) {
        mTxtDescription.setText(text);
        mAlwaysShowingImgExpand = alwaysShowImgExpand;
        if (TextUtils.isEmpty(text)) {
            mTxtDescription.setVisibility(View.GONE);
            mOriginalText = "";
        } else {
            mTxtDescription.setMaxLines(mMaxLine);
            mIsExpanded = false;
            mOriginalText = text;
        }
        hideArrow();
        mTxtDescription.setText(mOriginalText);
        mTxtDescription.requestLayout();
        mTxtDescription.setOnClickListener(null);
        //we need onMeasure() to be called
        requestLayout();
        layoutHelper();
    }

    public void setImgOnClickListener() {
        mTxtDescription.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
    }

    private void showArrow(boolean expand) {
        mTxtDescription.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, (expand ? R.drawable.ic_expand_more : R.drawable.ic_expand_less));
    }

    private void hideArrow() {
        mTxtDescription.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView, defStyle, 0);

        //get the value of "etw_maxLines" attribute if it has been specified
        int maxLines = 2;
        try {
            maxLines = a.getInt(R.styleable.ExpandableTextView_etw_maxLines, maxLines);
        } finally {
            a.recycle();
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_view_expandable_text, this);
        mTxtDescription = view.findViewById(R.id.textView);
        mTxtDescription.setMaxLines(maxLines);

        if (mViewTreeObserver == null) {
            mViewTreeObserver = this.mTxtDescription.getViewTreeObserver();
            mViewTreeObserver.addOnGlobalLayoutListener(mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

                @Override
                public void onGlobalLayout() {
                    layoutHelper();
                }
            });
        }


        expand();
        collapse();
    }

    private void layoutHelper() {
        if (mTxtDescription.getLineCount() > mMaxLine && !isExpanded()) {
            int lineEndIndex = mTxtDescription.getLayout().getLineEnd(mMaxLine - 1);
            String text = mTxtDescription.getText().subSequence(0, lineEndIndex - 3) + mEllipsis;
            mTxtDescription.setText(text);
            showArrow(true);
            setImgOnClickListener();
        }
        if (mTxtDescription.getLineCount() < mMaxLine) {
            hideArrow();
            mTxtDescription.setOnClickListener(null);

        }
    }

    /**
     * Toggle if it is expanded or not
     */
    public final void toggle() {
        if (isExpanded()) {
            showArrow(true);
            collapse();
        } else {
            showArrow(false);
            expand();
        }
    }

    /**
     * Extract private maxLine from super class
     */
    private void storeMaxLine() {
        Field f;
        try {
            f = mTxtDescription.getClass().getDeclaredField(MAXIMUM_VAR_NAME);
            f.setAccessible(true);
            mMaxLine = f.getInt(mTxtDescription);

            f.setAccessible(false);
        } catch (SecurityException e) {
            Log.e(TAG, e.getMessage(), e);
        } catch (NoSuchFieldException e) {
            Log.e(TAG, e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e.getMessage(), e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public TextView getTxtDescription() {
        return mTxtDescription;
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mTxtDescription != null) {
            ViewTreeObserver obs = mTxtDescription.getViewTreeObserver();
            if (mGlobalLayoutListener != null) {
                obs.removeGlobalOnLayoutListener(mGlobalLayoutListener);
            }
        }
        super.onDetachedFromWindow();
    }
}