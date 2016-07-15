package com.kingggg;

import android.app.Activity;
import android.app.Application;

import com.kingggg.volley.VolleyUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 作者：KingGGG on 15/11/20 10:13
 * 描述：APPLICATION
 */

public class AppApplication extends Application {
    private List<Activity> activities = new LinkedList<Activity>();
    private static AppApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        initRequestQueue();
    }

    //初始化请求队列
    private void initRequestQueue() {
        //初始化 volley
        VolleyUtils.initialize(this);
    }

    public synchronized static AppApplication getInstance() {
        if (null == instance) {
            instance = new AppApplication();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void exitApp() {
        try {
            for (Activity activity : activities) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public void finishAllActivity() {
        try {
            for (Activity activity : activities) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

}
