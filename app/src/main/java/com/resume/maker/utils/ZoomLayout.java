package com.resume.maker.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.RelativeLayout;


public class ZoomLayout extends RelativeLayout implements ScaleGestureDetector.OnScaleGestureListener {
    private static final float MAX_ZOOM = 4.0f;
    private static final float MIN_ZOOM = 1.0f;
    private static final String TAG = "ZoomLayout";
    private Mode mode = Mode.NONE;
    private float scale = 1.0f;
    private float lastScaleFactor = 0.0f;
    private float startX = 0.0f;
    private float startY = 0.0f;
    private float dx = 0.0f;
    private float dy = 0.0f;
    private float prevDx = 0.0f;
    private float prevDy = 0.0f;


    private enum Mode {
        NONE,
        DRAG,
        ZOOM
    }

    public ZoomLayout(Context context) {
        super(context);
        init(context);
    }

    public ZoomLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public ZoomLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        final ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction() & 255;
                if (action == 0) {
                    Log.i("ZoomLayout", "DOWN");
                    if (ZoomLayout.this.scale > 1.0f) {
                        ZoomLayout.this.mode = Mode.DRAG;
                        ZoomLayout.this.startX = motionEvent.getX() - ZoomLayout.this.prevDx;
                        ZoomLayout.this.startY = motionEvent.getY() - ZoomLayout.this.prevDy;
                    }
                } else if (action == 1) {
                    Log.i("ZoomLayout", "UP");
                    ZoomLayout.this.mode = Mode.NONE;
                    ZoomLayout zoomLayout = ZoomLayout.this;
                    zoomLayout.prevDx = zoomLayout.dx;
                    ZoomLayout zoomLayout2 = ZoomLayout.this;
                    zoomLayout2.prevDy = zoomLayout2.dy;
                } else if (action != 2) {
                    if (action == 5) {
                        ZoomLayout.this.mode = Mode.ZOOM;
                    } else if (action == 6) {
                        ZoomLayout.this.mode = Mode.DRAG;
                    }
                } else if (ZoomLayout.this.mode == Mode.DRAG) {
                    ZoomLayout.this.dx = motionEvent.getX() - ZoomLayout.this.startX;
                    ZoomLayout.this.dy = motionEvent.getY() - ZoomLayout.this.startY;
                }
                scaleGestureDetector.onTouchEvent(motionEvent);
                if ((ZoomLayout.this.mode == Mode.DRAG && ZoomLayout.this.scale >= 1.0f) || ZoomLayout.this.mode == Mode.ZOOM) {
                    ZoomLayout.this.getParent().requestDisallowInterceptTouchEvent(true);
                    float width = ((((float) ZoomLayout.this.child().getWidth()) - (((float) ZoomLayout.this.child().getWidth()) / ZoomLayout.this.scale)) / 2.0f) * ZoomLayout.this.scale;
                    float height = ((((float) ZoomLayout.this.child().getHeight()) - (((float) ZoomLayout.this.child().getHeight()) / ZoomLayout.this.scale)) / 2.0f) * ZoomLayout.this.scale;
                    ZoomLayout zoomLayout3 = ZoomLayout.this;
                    zoomLayout3.dx = Math.min(Math.max(zoomLayout3.dx, -width), width);
                    ZoomLayout zoomLayout4 = ZoomLayout.this;
                    zoomLayout4.dy = Math.min(Math.max(zoomLayout4.dy, -height), height);
                    Log.i("ZoomLayout", "Width: " + ZoomLayout.this.child().getWidth() + ", scale " + ZoomLayout.this.scale + ", dx " + ZoomLayout.this.dx + ", max " + width);
                    ZoomLayout.this.applyScaleAndTranslation();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        Log.i("ZoomLayout", "onScaleBegin");
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        float scaleFactor = scaleGestureDetector.getScaleFactor();
        Log.i("ZoomLayout", "onScale" + scaleFactor);
        if (this.lastScaleFactor == 0.0f || Math.signum(scaleFactor) == Math.signum(this.lastScaleFactor)) {
            this.scale *= scaleFactor;
            this.scale = Math.max(1.0f, Math.min(this.scale, (float) MAX_ZOOM));
            this.lastScaleFactor = scaleFactor;
            return true;
        }
        this.lastScaleFactor = 0.0f;
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        Log.i("ZoomLayout", "onScaleEnd");
    }


    public void applyScaleAndTranslation() {
        child().setScaleX(this.scale);
        child().setScaleY(this.scale);
        child().setTranslationX(this.dx);
        child().setTranslationY(this.dy);
    }


    public View child() {
        return getChildAt(0);
    }
}
