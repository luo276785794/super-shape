package com.wenlong.shape;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

public class GraphicsActivityLifecycleRegister implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.e("luo", "onActivityCreated out");
        if (activity != null) {
            Log.e("luo", "onActivityCreated in");
            GraphicsDrawableLibrary.inject(activity);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
