package com.github.matvapps.setmapplacesview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.matvapps.setmapplacesview.R;
import com.github.matvapps.setmapplacesview.utils.ViewUtils;

/**
 * Created by Alexander Matvienko on 14.12.2018.
 */
public class DrawableRoute extends FrameLayout {

    private final String TAG = DrawableRoute.class.getSimpleName();

    private Type type = Type.BASE_ROUTE;
    private ImageView imageView;
//    private ImageView imageViewDown;
    private LinearLayout linearLayout;
    private LinearLayout linearLayout1;
    private View view;

    public DrawableRoute(@NonNull Context context) {
        super(context);
//        init();
    }

    public DrawableRoute(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getStyleableAttrs(attrs, context);
        init();
    }

    public DrawableRoute(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getStyleableAttrs(attrs, context);
        init();
    }

    void getStyleableAttrs(AttributeSet attrs, Context context) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrawableRoute);

        type = Type.fromId(a.getInt(R.styleable.DrawableRoute_dr_type, 23));

        a.recycle();
    }

    void init() {
        view = inflate(getContext(), R.layout.drawable, this);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        imageView = view.findViewById(R.id.image_view);
//        imageViewDown = view.findViewById(R.id.image_view_down);
        linearLayout = view.findViewById(R.id.linearLayout);
        linearLayout1 = view.findViewById(R.id.linearLayout1);

        Log.d(TAG, "init: " + type.id);

        changeRouteDrawable(type);
    }


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
        changeRouteDrawable(type);
    }

    public void changeRouteDrawable(Type type) {
        switch (type) {
            case BASE_ROUTE:
                linearLayout.setVisibility(VISIBLE);
                imageView.setVisibility(VISIBLE);
                linearLayout1.setVisibility(VISIBLE);

//                imageViewDown.setVisibility(GONE);
                imageView.setImageResource(R.drawable.circle_item_base);
                imageView.getLayoutParams().height = ViewUtils.dpToPx(10);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.requestLayout();
//                lineDotted.setVisibility(VISIBLE);
                break;
            case LAST_ROUTE:
                linearLayout.setVisibility(VISIBLE);
                linearLayout1.setVisibility(INVISIBLE);
                imageView.setVisibility(VISIBLE);
                imageView.setImageResource(R.drawable.ic_place);
                imageView.getLayoutParams().height = ViewUtils.dpToPx(14);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.requestLayout();
                break;
            case FIRST_ROUTE:
                linearLayout1.setVisibility(VISIBLE);
                linearLayout.setVisibility(INVISIBLE);
                imageView.setVisibility(VISIBLE);
                imageView.setImageResource(R.drawable.circle_item_first);
                imageView.getLayoutParams().height = ViewUtils.dpToPx(10);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.requestLayout();
//                lineDotted.setVisibility(VISIBLE);
                break;
        }
    }

    public enum Type {
        FIRST_ROUTE(22), BASE_ROUTE(23), LAST_ROUTE(24);
        int id;

        Type(int id) {
            this.id = id;
        }

        static Type fromId(int id) {
            for (Type f : values()) {
                if (f.id == id) return f;
            }
            throw new IllegalArgumentException();
        }
    }

}
