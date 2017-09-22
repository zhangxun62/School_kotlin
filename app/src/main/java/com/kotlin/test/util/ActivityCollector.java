package com.kotlin.test.util;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ActivityCollector
 * @Author: android_chenlong
 * @Date: 2016/1/28 17:30
 * @Version: 1.0.0
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activity.finish();
        activities.remove(activity);
    }

    public static void removeActivity(Context context) {
        Activity activity = (Activity) context;
        activity.finish();
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
                activities.remove(activity);
            }
        }
    }
}
