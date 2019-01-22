package com.wedesign.launcher3;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//public class Workspace extends LinearLayout {
/*
 * 未考虑到 自身控件宽为wrap_content, 自身控件padding 属性， 未考虑到 子控件margin属性。
 *
 * */
public class Workspace extends ViewGroup {
    private static final String TAG = "Workspace";

    //获取安卓统一的滑动距离,手的移动距离要大于这个距离才开始移动控件/翻页
    private static int TOUCH_SLOP;

    //获取当前控件宽度，基于此宽度，将控件本身滑动此宽度距离，从而实现切换到下一屏
    private static int WORKSPACE_WIDTH;

    // The is the first screen. It is always present, even if its empty.
    public static final long FIRST_SCREEN_ID = 0;

    private boolean isScrollMode = false;

    private int mCurrentScreenId;

    private float mDownX;
    private float mLastMotionX;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    private Map<Long, CellLayout> mWorkspaceScreens = new HashMap<>();
    private List<Long> mScreenOrder = new ArrayList<>();

    private Context mContext;

    public Workspace(Context context) {
        this(context, null);
    }

    public Workspace(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Workspace(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public Workspace(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        mContext = context;

        initWorkspace();

        TOUCH_SLOP =  ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);
        mCurrentScreenId = LauncherConfiguration.WORKSPACE_DEFAULT_SCREEN;
    }

/*    // extends LinearLayout
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        WORKSPACE_WIDTH = getWidth();
    }*/

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    // extends ViewGroup
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        WORKSPACE_WIDTH = getWidth();
        for(int i = 0; i < getChildCount(); i++) {
            getChildAt(i).layout(i * getWidth(), 0, (i+1) * getWidth(), getHeight());
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        int action = e.getAction();

        Log.d(TAG, "onInterceptTouchEvent: action = " + action);

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
                isScrollMode = false;
                break;

        }
//        return true;
        return isScrollMode;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        int action = event.getAction();

        Log.d(TAG, "onTouchEvent: action = " + action);

        final float lastMotionX = mLastMotionX;
        mLastMotionX = event.getX();


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
                    int deltaX = (int)(lastMotionX - mLastMotionX);

                    scrollBy(deltaX, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000);
                float xVelocity = velocityTracker.getXVelocity();
//                Log.d(TAG, "onTouchEvent: xVelocity = " + xVelocity);
                if(xVelocity > LauncherConfiguration.WORKSPACE_VELOCITY_SLOP) {
                    scrollToScreen(mCurrentScreenId - 1);
                } else if(xVelocity < -LauncherConfiguration.WORKSPACE_VELOCITY_SLOP) {
                    scrollToScreen(mCurrentScreenId + 1);
                } else {
                    scrollToDestination();
                }

                if (mVelocityTracker != null) {
                    mVelocityTracker.clear();
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
        }

        return true; //永远返回true，对否。
    }

    private void scrollToDestination() {
        int screenId;
        screenId = (int)((getScrollX() + WORKSPACE_WIDTH / 2) / WORKSPACE_WIDTH);

        scrollToScreen(screenId);
    }

    private void scrollToScreen(int screenId) {
        mCurrentScreenId = Math.max(0, Math.min(LauncherConfiguration.WORKSPACE_SCREEN_COUNT - 1, screenId));

        startScroll(mCurrentScreenId * WORKSPACE_WIDTH);
    }

    public void startScroll(int targetX) {
        int dx = targetX - getScrollX();

//        Log.d(TAG, "zoomIn: getScrollX() = " + getScrollX() + ", dx = " + dx);
        // Revert any animation currently in progress
        mScroller.forceFinished(true);
        // Start scrolling by providing a starting point and
        // the distance to travel
        mScroller.startScroll(getScrollX(), 0, dx, 0);
        // Invalidate to request a redraw
        postInvalidate();
    }

    @Override
    public void computeScroll() {
//        Log.d(TAG, "computeScroll: mScroller = " + mScroller);
        if (mScroller != null) {
//            Log.d(TAG, "computeScroll: mScroller.computeScrollOffset() = " + mScroller.computeScrollOffset());
            if (mScroller.computeScrollOffset()) {
                // Get current x and y positions
                int currX = mScroller.getCurrX();
                int currY = mScroller.getCurrY();
                scrollTo(currX, currY);
                postInvalidate();
            }
        }
    }

    /**
     * Initializes and binds the first page
     * @param qsb an existing qsb to recycle or null.
     */
    public void bindAndInitFirstWorkspaceScreen(View qsb) {
        // Add the first page
        CellLayout firstPage = insertNewWorkspaceScreen(Workspace.FIRST_SCREEN_ID, 0);
    }
    public CellLayout insertNewWorkspaceScreen(long screenId, int insertIndex) {
        if(mWorkspaceScreens.containsKey(screenId)){
            throw new RuntimeException("Screen id " + screenId + " already exists.");
        }

        // Inflate the cell layout, but do not add it automatically so that we can get the newly
        // created CellLayout.
        CellLayout newScreen = (CellLayout) LayoutInflater.from(mContext).inflate(R.layout.workspace_screen, this, false);
        mWorkspaceScreens.put(screenId, newScreen);
        mScreenOrder.set(insertIndex, screenId);
        addView(newScreen, insertIndex);

        return newScreen;
    }

    /**
     * Initializes various states for this workspace.
     */
    protected void initWorkspace() {

    }

}
