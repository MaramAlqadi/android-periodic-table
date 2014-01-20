package com.frozendevs.periodic.table.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

public class ZoomView extends FrameLayout implements ViewTreeObserver.OnGlobalLayoutListener,
        ScaleGestureDetector.OnScaleGestureListener {

    private float MAX_ZOOM = 1.0f;
    private float MIN_ZOOM = 0;
    private float zoom, zoomX, zoomY;
    private int contentWidth, contentHeight;
    private ScaleGestureDetector scaleDetector;

    public ZoomView(Context context) {
        super(context);
        init(context);
    }

    public ZoomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ZoomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        getViewTreeObserver().addOnGlobalLayoutListener(this);
        scaleDetector = new ScaleGestureDetector(context, this);
    }

    @Override
    public void onGlobalLayout() {
        for(int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            contentWidth = Math.max(contentWidth, child.getMeasuredWidth());
            contentHeight = Math.max(contentHeight, child.getMeasuredHeight());
        }

        if(contentWidth > contentHeight)
            MIN_ZOOM = (float)getWidth() / (float)contentWidth;
        else
            MIN_ZOOM = (float)getHeight() / (float)contentHeight;

        zoom = MIN_ZOOM = Math.min(MIN_ZOOM, MAX_ZOOM);

        for(int i = 0; i < getChildCount(); i++)
            scaleView(getChildAt(i));
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        zoom = Math.min(MAX_ZOOM, Math.max(zoom * detector.getScaleFactor(), MIN_ZOOM));
        zoomX = detector.getFocusX();
        zoomY = detector.getFocusY();

        for(int i = 0; i < getChildCount(); i++)
            scaleView(getChildAt(i));

        return zoom == MIN_ZOOM || zoom == MAX_ZOOM;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(scaleDetector.onTouchEvent(event))
            return true;

        return super.dispatchTouchEvent(event);
    }

    private void scaleView(View view) {
        view.setScaleX(zoom);
        view.setScaleY(zoom);
        int left = (getWidth() - view.getMeasuredWidth()) / 2;
        int top = (getHeight() - view.getMeasuredHeight()) / 2;
        view.layout(left, top, left + view.getMeasuredWidth(), top + view.getMeasuredHeight());
    }
}
