package com.example.wedesign.launcherapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.Scroller;

public class AllAppsRecyclerView extends RecyclerView {
    private static final String TAG = "AllAppsRecyclerView";

    //获取安卓统一的滑动距离,手的移动要大于这个距离才开始移动控件/翻页
    private static int TOUCH_SLOP;
    private static int SCREEN_WIDTH;

    private boolean isScrollMode = false;

    private int currentScreenId = 0;

    private float mDownX;
    private float xVelocity;
    private float yVelocity;

    public AllAppsRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public AllAppsRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AllAppsRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TOUCH_SLOP =  ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        SCREEN_WIDTH = getWidth();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();

        // 还需要考虑多指触控，unfinished.
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isScrollMode = false;
                mDownX = e.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(e.getX() - mDownX) > TOUCH_SLOP) {
                    isScrollMode = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return isScrollMode;
    }






/*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();


        VelocityTracker velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isScrollMode = false;
                mDownX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isScrollMode) {
                    if (Math.abs(event.getX() - mDownX) > TOUCH_SLOP) {
                        isScrollMode = true;
                    }
                }

                if(isScrollMode) { //已进入滚动模式
                    scrollToTargetPosition(mDownX - event.getX());
                }
                break;
            case MotionEvent.ACTION_UP:
                velocityTracker.computeCurrentVelocity(1000);
                xVelocity = velocityTracker.getXVelocity();
                yVelocity = velocityTracker.getYVelocity();


                velocityTracker.clear();
                velocityTracker.recycle();
                break;
        }

        return true; //永远返回true，对否。
    }
    */
    private void scrollToTargetPosition(float offsetX) {
        int x = (int)(currentScreenId * SCREEN_WIDTH + offsetX);
        scrollTo(x, 0);
    }

    public void scrollTo(int x, int y) {
        GestureDetector gestureDetector;
        Scroller s;

    }

}
