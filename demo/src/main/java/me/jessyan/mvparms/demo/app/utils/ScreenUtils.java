package me.jessyan.mvparms.demo.app.utils;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

/**
 * @package me.jessyan.mvparms.demo.app.utils
 * @fileName ScreenUtils
 * @date 2018/11/27
 * @describe 屏幕处理工具
 * @email shenzhencuco@gmail
 */
public class ScreenUtils {


    /**
     * 设置隐藏标题栏
     *
     * @param activity
     */
    public static void setNoTitleBar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 设置全屏
     *
     * @param activity
     */
    public static void setFullScreen(Activity activity) {
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 取消全屏
     *
     * @param activity
     */
    public static void cancelFullScreen(Activity activity) {
        activity.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
