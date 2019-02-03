package com.github.matvapps.setmapplacesview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.github.matvapps.setmapplacesview.R;
import com.github.matvapps.setmapplacesview.utils.ViewUtils;

/**
 * Created by Alexander Matvienko on 14.12.2018.
 */
public class LineDotted extends View {
    Paint fgPaintSel;

    public LineDotted(Context context) {
        super(context);
        init();
    }

    public LineDotted(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LineDotted(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Path baseline;

    private void init() {
        fgPaintSel = new Paint();
        fgPaintSel.setAlpha(255);
        fgPaintSel.setStrokeWidth(ViewUtils.dpToPx(1));
        fgPaintSel.setColor(getResources().getColor(R.color.color_app_blue_light));
        fgPaintSel.setStyle(Paint.Style.FILL);

        int size = ViewUtils.dpToPx(3);
        int gap = ViewUtils.dpToPx(3);

//        baseline = new Path();
//
//        for (int i = 0; i < getHeight() / (size + gap); i += size + gap) {
//            baseline.moveTo(0, i);
//            baseline.lineTo(0, i + size);
//
//        }



            fgPaintSel.setPathEffect(new DashPathEffect(new float[]{size, gap}, 0));
    }

    //
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawPath(baseline, fgPaintSel);
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), fgPaintSel);
    }


}
