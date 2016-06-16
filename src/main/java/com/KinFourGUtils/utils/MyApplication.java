package com.KinFourGUtils.utils;

import android.app.Activity;
import android.app.Application;

import com.KinFourGUtils.volley.VolleyUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 作者：KingGGG on 15/11/20 10:13
 * 描述：APPLICATION
 */

public class MyApplication extends Application {
    private List<Activity> mlist = new LinkedList<Activity>();
    private static MyApplication instance;

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

    public synchronized static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        mlist.add(activity);
    }

    public void exitApp() {
        try {
            for (Activity activity : mlist) {
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
            for (Activity activity : mlist) {
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
