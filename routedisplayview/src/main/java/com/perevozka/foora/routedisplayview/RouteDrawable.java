package com.perevozka.foora.routedisplayview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by Alexander Matvienko on 14.12.2018.
 */
public class RouteDrawable extends FrameLayout {

    private final String TAG = RouteDrawable.class.getSimpleName();

    private Type type = Type.BASE_ROUTE;
    private ImageView imageView;
    private DottedLine dottedLine;

    public RouteDrawable(@NonNull Context context) {
        super(context);
//        init();
    }

    public RouteDrawable(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getStyleableAttrs(attrs, context);
        init();
    }

    public RouteDrawable(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getStyleableAttrs(attrs, context);
        init();
    }

    void getStyleableAttrs(AttributeSet attrs, Context context) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RouteDrawable);


        type = Type.fromId(a.getInt(R.styleable.RouteDrawable_rd_type, 23));

        Log.d(TAG, "getStyleableAttrs: " + type.id);

        a.recycle();
    }

    void init() {
        View view = inflate(getContext(), R.layout.custom_drawable, this);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        imageView = view.findViewById(R.id.image_view);
        dottedLine = view.findViewById(R.id.dotted_line);

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
                imageView.setImageResource(R.drawable.circle_outline_gray);
                imageView.getLayoutParams().height = ViewUtils.dpToPx(10);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.requestLayout();
                dottedLine.setVisibility(VISIBLE);
                break;
            case LAST_ROUTE:
                imageView.setImageResource(R.drawable.ic_place);
                imageView.getLayoutParams().height = ViewUtils.dpToPx(14);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.requestLayout();
                dottedLine.setVisibility(INVISIBLE);
                break;
            case FIRST_ROUTE:
                imageView.setImageResource(R.drawable.circle_outline_gold);
                imageView.getLayoutParams().height = ViewUtils.dpToPx(10);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.requestLayout();
                dottedLine.setVisibility(VISIBLE);
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
