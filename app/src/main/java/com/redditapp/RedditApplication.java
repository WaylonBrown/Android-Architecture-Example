package com.redditapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.redditapp.dagger.RedditAppComponent;
import com.redditapp.ui.ActivityHierarchyServer;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import timber.log.Timber;

public class RedditApplication extends Application {
    private RedditAppComponent component;

    @Inject
    ActivityHierarchyServer activityHierarchyServer;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashlyticsTree());
        }

        buildComponentAndInject();

        registerActivityLifecycleCallbacks(activityHierarchyServer);
    }

    public void buildComponentAndInject() {
        component = RedditAppComponent.Initializer.init(this);
        component.inject(this);
    }

    public RedditAppComponent getComponent() {
        return component;
    }

    public static RedditApplication get(Context context) {
        return (RedditApplication)context.getApplicationContext();
    }

    /** A tree which logs important information for crash reporting. */
    private static class CrashlyticsTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            //TODO: implement Crashlytics
//            FakeCrashLibrary.log(priority, tag, message);
            if (t != null) {
                if (priority == Log.ERROR) {
//                    FakeCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
//                    FakeCrashLibrary.logWarning(t);
                }
            }
        }
    }
}