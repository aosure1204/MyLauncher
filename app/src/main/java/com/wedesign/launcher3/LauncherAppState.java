package com.wedesign.launcher3;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class LauncherAppState {

    // We do not need any synchronization for this variable as its only written on UI thread.
    private static LauncherAppState INSTANCE;

    private final Context mContext;
    private final IconCache mIconCache;

    public static LauncherAppState getInstance(final Context context) {
        if (INSTANCE == null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                INSTANCE = new LauncherAppState(context.getApplicationContext());
            } else {
                try {
                    return new MainThreadExecutor().submit(new Callable<LauncherAppState>() {
                        @Override
                        public LauncherAppState call() throws Exception {
                            return LauncherAppState.getInstance(context);
                        }
                    }).get();
                } catch (InterruptedException|ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return INSTANCE;
    }


    private LauncherAppState(Context context) {
/*        if (getLocalProvider(context) == null) {
            throw new RuntimeException(
                    "Initializing LauncherAppState in the absence of LauncherProvider");
        }*/
        Log.v(Launcher.TAG, "LauncherAppState initiated");
//        Preconditions.assertUIThread();
        mContext = context;

//        if (TestingUtils.MEMORY_DUMP_ENABLED) {
//            TestingUtils.startTrackingMemory(mContext);
//        }

        mIconCache = new IconCache(mContext);
    }

    public IconCache getIconCache() {
        return mIconCache;
    }
}
