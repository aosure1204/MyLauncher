package com.wedesign.launcher3.dragndrop;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class DragLayer extends FrameLayout {
    public DragLayer(Context context) {
        this(context, null);
    }

    public DragLayer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayer(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DragLayer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
