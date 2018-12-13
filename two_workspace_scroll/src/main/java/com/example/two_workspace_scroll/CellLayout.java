package com.example.two_workspace_scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;


/*
* The layout_width and layout_height attributes are not valid. No matter what value is set, the parent container always considers it to be match_parent.
*
*
* */
public class CellLayout extends RelativeLayout {
    public CellLayout(Context context) {
        this(context, null);
    }

    public CellLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CellLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CellLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

/*
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        if ((action == MotionEvent.ACTION_MOVE) &&
                (mTouchState != 0)) {     //本父类滑动，将事件拦截下来。
            return true;
        }

            switch (action) {
            case MotionEvent.ACTION_MOVE:
                final int xDiff = (int)Math.abs(mLastMotionX-x);
                if (xDiff>slideDistance) {
                    Log.e(TAG, "onInterceptTouchEvent-xDiff>"+slideDistance);
                    mTouchState = 1;
                }
                break;
                case MotionEvent.ACTION_MOVE:
                    int deltaX = (int)(mLastMotionX - x);
                    mLastMotionX = x;

                    scrollBy(deltaX, 0);
                case MotionEvent.ACTION_DOWN:
                    mTouchState = mScroller.isFinished()? 0 : 1;
                    mLastMotionX = x;
                    break;

                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    mTouchState = 0;
                    break;
            }

        return mTouchState != 0;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mVelocityTracker == null) { //初始化
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
...
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "event : up");
// if (mTouchState == TOUCH_STATE_SCROLLING) {
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(600);
                int velocityX = (int) velocityTracker.getXVelocity();

                Log.e(TAG, "velocityX:"+velocityX);

                if (velocityX > 600 && BaseApp.widgetCurrentpage > 0) { //滑动到上一页
// Fling enough to move left
                    Log.e(TAG, "snap left");
                    snapToScreen(BaseApp.widgetCurrentpage - 1);
                } else if (velocityX < -600
                        && BaseApp.widgetCurrentpage < getChildCount() - 1) { //滑动到下一页
// Fling enough to move right
                    Log.e(TAG, "snap right");
                    snapToScreen(BaseApp.widgetCurrentpage + 1);
                } else { //根据位置判断滑动到哪一页
                    snapToDestination();
                }

                mTouchState = 0;     //清除滑动状态

                if (mVelocityTracker != null) { //清除
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                mTouchState = 0;
                break;
        }

        return true;
    }


    public void snapToScreen(int whichScreen) {
        if(whichScreen >= BaseApp.widget_totalpages){
            whichScreen = BaseApp.widget_totalpages-1;
        }else if(whichScreen<0){
            whichScreen = 0;
        }

        if(BaseApp.currentTpye == BaseApp.WIDGET){
            pageListener.page(whichScreen);//接口回掉改变指示器
        }

        mScroller.startScroll(getScrollX(), 0,
                delta, 0, Math.abs(delta)*2);
        invalidate(); // Redraw the layout
...
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }*/

/*    private float mLastMotionX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        final float lastMotionX = mLastMotionX;
        mLastMotionX = event.getX();

        switch (action) {
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) (lastMotionX - mLastMotionX);
                scrollBy(deltaX, 0);
                break;
        }
        return true;
    }*/

GestureDetector f;

}
