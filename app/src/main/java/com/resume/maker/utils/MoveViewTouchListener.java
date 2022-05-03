package com.resume.maker.utils;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public class MoveViewTouchListener implements View.OnTouchListener {
    private GestureDetector mGestureDetector;
    private GestureDetector.OnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
        private float mMotionDownX;
        private float mMotionDownY;

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            this.mMotionDownX = motionEvent.getRawX() - MoveViewTouchListener.this.mView.getTranslationX();
            this.mMotionDownY = motionEvent.getRawY() - MoveViewTouchListener.this.mView.getTranslationY();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            MoveViewTouchListener.this.mView.setTranslationX(motionEvent2.getRawX() - this.mMotionDownX);
            MoveViewTouchListener.this.mView.setTranslationY(motionEvent2.getRawY() - this.mMotionDownY);
            return true;
        }
    };
    private View mView;

    public MoveViewTouchListener(View view) {
        this.mGestureDetector = new GestureDetector(view.getContext(), this.mGestureListener);
        this.mView = view;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return this.mGestureDetector.onTouchEvent(motionEvent);
    }
}
