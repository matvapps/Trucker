package com.foora.perevozkadev.utils.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by Alexandr.
 */
public class LocableScrollView extends ScrollView {

    private boolean mScrollable = true;

    public LocableScrollView(Context context) {
        super(context);
    }

    public LocableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LocableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollingEnabled(boolean enabled) {
        mScrollable = enabled;
    }

    public boolean isScrollable() {
        return mScrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mScrollable && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mScrollable && super.onInterceptTouchEvent(ev);
    }

}
