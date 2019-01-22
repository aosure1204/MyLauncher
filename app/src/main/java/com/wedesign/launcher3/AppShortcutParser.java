package com.wedesign.launcher3;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import org.xmlpull.v1.XmlPullParser;

public class AppShortcutParser {

    private static Context mContext;
    private static Resources mSourceRes;
    private static IconCache mIconCache;

    // define tag
    private static final String TAG_WORKSPACE = "workspace";
    private static final String TAG_FAVORITE = "favorite";
    private static final String TAG_FOLDER = "folder";

    // define attribute namespace
    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";

    // define attribute name.
    private static final String ATTR_PACKAGE_NAME = "packageName";
    private static final String ATTR_CLASS_NAME = "className";
    private static final String ATTR_SCREEN = "screen";
    private static final String ATTR_X = "x";
    private static final String ATTR_Y = "y";
    private static final String ATTR_LARGE_ICON = "largeIcon";
    private static final String ATTR_SMALL_ICON = "smallIcon";
    private static final String ATTR_TITLE = "title";

    public AppShortcutParser(Context context, IconCache iconCache) {
        mContext = context;
        mSourceRes = mContext.getResources();
        mIconCache = iconCache;
    }

    public void parseWorkspaceFromXml(final int xmlResId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                parseWorkspaceFromXmlInBackground(xmlResId);
            }
        }).start();
    }

    private Callback mCallback;
    public interface Callback{
        public void bindItems();
    }
    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    private void parseWorkspaceFromXmlInBackground(int xmlResId){
        try {
            XmlResourceParser parser = mContext.getResources().getXml(xmlResId);
            int eventType = parser.getEventType();
            while( eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = parser.getName();
                if(eventType == XmlPullParser.START_TAG) {
                    if(TAG_FAVORITE.equals(nodeName)){
                        ComponentName componentName = generateComponentName(parser);
                        IconCache.CacheEntry entry = generateIconCacheEntry(parser);
                        mIconCache.addIconToDBAndMemCache(componentName, entry);
                    } else if(TAG_FOLDER.equals(nodeName)){
                        generateIconCacheEntry(parser);
                    } else if(!TAG_WORKSPACE.equals(nodeName)) {
                        throw new RuntimeException("XmlResId: " + xmlResId + " contain unsupported nodeName: " + nodeName);
                    }
                }
                eventType = parser.next();
            }

            mCallback.bindItems();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    /**
     * Return attribute value, attempting launcher-specific namespace first
     * before falling back to anonymous attribute.
     */
    protected int getAttributeResourceValue(XmlResourceParser parser, String attribute, int defaultValue) {
        int value = parser.getAttributeResourceValue(NAMESPACE, attribute, defaultValue);
        if (value == 0) {
            value = parser.getAttributeResourceValue(null, attribute, defaultValue);
        }
        return value;
    }

    /**
     * Return attribute value, attempting launcher-specific namespace first
     * before falling back to anonymous attribute.
     */
    protected String getAttributeValue(XmlResourceParser parser, String attribute) {
        String value = parser.getAttributeValue(NAMESPACE, attribute);
        if (value == null) {
            value = parser.getAttributeValue(null, attribute);
        }
        return value;
    }

    protected ComponentName generateComponentName(XmlResourceParser parser){
        String pkg = getAttributeValue(parser, ATTR_PACKAGE_NAME);
        String cls = getAttributeValue(parser, ATTR_CLASS_NAME);
        return new ComponentName(pkg, cls);
    }

    protected IconCache.CacheEntry generateIconCacheEntry(XmlResourceParser parser){
        IconCache.CacheEntry entry = new IconCache.CacheEntry();
        try {
            String screen = getAttributeValue(parser, ATTR_SCREEN);
            entry.screen = Integer.parseInt(screen);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            String x = getAttributeValue(parser, ATTR_X);
            entry.cellX = Integer.parseInt(x);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            String y = getAttributeValue(parser, ATTR_Y);
            entry.cellY = Integer.parseInt(y);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        int titleResId = getAttributeResourceValue(parser, ATTR_TITLE, 0);
        if (titleResId != 0) {
           entry.title = mSourceRes.getString(titleResId);
        }
        int largeIconResId = getAttributeResourceValue(parser, ATTR_LARGE_ICON, 0);
        if(largeIconResId != 0) {
            entry.largeIcon = BitmapFactory.decodeResource(mSourceRes, largeIconResId);
        }
        int smallIconResId = getAttributeResourceValue(parser, ATTR_SMALL_ICON, 0);
        if(smallIconResId != 0) {
            entry.smallIcon = BitmapFactory.decodeResource(mSourceRes, smallIconResId);
        }
        return entry;
    }

}
