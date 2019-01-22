package com.wedesign.launcher3;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.view.View;

import com.wedesign.launcher3.dragndrop.DragLayer;

/**
 * Default launcher application.
 */
public class Launcher extends Activity {
    public static final String TAG = "Launcher";

    private IconCache mIconCache;

    private View mLauncherView;
    private DragLayer mDragLayer;
    private Workspace mWorkspace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LauncherAppState app = LauncherAppState.getInstance(this);
        mIconCache = app.getIconCache();

        //AppShortcutParser.parseWorkspaceFromXml(getApplicationContext(), R.xml.default_workspace);
        AppShortcutParser mAppShortcutParser = new AppShortcutParser(getApplicationContext(), mIconCache);
        mAppShortcutParser.setCallback(new AppShortcutParser.Callback(){

            @Override
            public void bindItems() {

            }
        });
        mAppShortcutParser.parseWorkspaceFromXml(R.xml.default_workspace);

        mLauncherView = getLayoutInflater().inflate(R.layout.launcher, null);

        setupViews();


        setContentView(mLauncherView);
    }

    @Override
    public View findViewById(int id) {
        return mLauncherView.findViewById(id);
    }


    public static Launcher getLauncher(Context context) {
        if (context instanceof Launcher) {
            return (Launcher) context;
        }
        return ((Launcher) ((ContextWrapper) context).getBaseContext());
    }

    /**
     * Finds all the views we need and configure them properly.
     */
    private void setupViews() {
//        mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
        mWorkspace = (Workspace) findViewById(R.id.workspace);
    }
}
