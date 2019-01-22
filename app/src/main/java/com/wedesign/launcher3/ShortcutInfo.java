package com.wedesign.launcher3;

import android.graphics.drawable.Drawable;

/**
 * Represents a launchable icon on the workspaces and in folders.
 */
public class ShortcutInfo {
    private String mPackageName;
    private String mClassName;
    private int mScreenId;
    private int mCellX;
    private int mCellY;
    private Drawable mLargeIconDrawable;
    private Drawable mSmallIconDrawable;
    private CharSequence mTitle;

    public ShortcutInfo() {

    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        mPackageName = packageName;
    }

    public String getClassName() {
        return mClassName;
    }

    public void setClassName(String className) {
        this.mClassName = className;
    }

    public int getScreenId() {
        return mScreenId;
    }

    public void setScreenId(int screenId) {
        mScreenId = screenId;
    }

    public int getCellX() {
        return mCellX;
    }

    public void setCellX(int cellX) {
        mCellX = cellX;
    }

    public int getCellY() {
        return mCellY;
    }

    public void setCellY(int cellY) {
        mCellY = cellY;
    }

    public Drawable getLargeIconDrawable() {
        return mLargeIconDrawable;
    }

    public void setLargeIconDrawable(Drawable largeIconDrawable) {
        mLargeIconDrawable = largeIconDrawable;
    }

    public Drawable getSmallIconDrawable() {
        return mSmallIconDrawable;
    }

    public void setSmallIconDrawable(Drawable smallIconDrawable) {
        mSmallIconDrawable = smallIconDrawable;
    }

    public CharSequence getTitle() {
        return mTitle;
    }

    public void setTitle(CharSequence title) {
        mTitle = title;
    }


}
