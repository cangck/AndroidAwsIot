package me.jessyan.mvparms.demo.mvp.ui.globalui;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import me.jessyan.mvparms.demo.R;

/**
 * @package me.jessyan.mvparms.demo.mvp.ui.globalui
 * @fileName AlertDialogUtils
 * @date 2018/11/17
 * @describe
 * @email shenzhencuco@gmail
 */
public class AlertDialogUtils {
    private static AlertDialogUtils mAlertDialogUtils;

    private AlertDialogUtils() {
    }

    public static AlertDialogUtils get() {
        if (mAlertDialogUtils == null) {
            synchronized (AlertDialogUtils.class) {
                if (mAlertDialogUtils == null) {
                    mAlertDialogUtils = new AlertDialogUtils();
                }
            }
        }
        return mAlertDialogUtils;

    }

    /**
     * 显示对话框
     */
    public AlertDialog show(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder.setTitle("text")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("testtest").create();
    }
}
