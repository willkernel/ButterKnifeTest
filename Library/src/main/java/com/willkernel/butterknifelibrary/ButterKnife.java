package com.willkernel.butterknifelibrary;

import android.app.Activity;

/**
 * Created by willkernel
 * on 2019/5/3.
 */
public class ButterKnife {
    public static void bind(Activity activity) {
        //MainActivity$ViewBinder
        String clzName=activity.getClass().getName()+"$ViewBinder";
        try {
            Class clz=Class.forName(clzName);
            ViewBinder viewBinder= (ViewBinder) clz.newInstance();
            viewBinder.bind(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void unBind(Activity activity) {
        String className = activity.getClass().getName() + "$ViewBinder";

        try {
            Class<?> viewBindClass = Class.forName(className);
            ViewBinder viewBinder = (ViewBinder) viewBindClass.newInstance();
            viewBinder.unBind(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
