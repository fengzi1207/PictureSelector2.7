package com.luck.picture.lib.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：luck
 * @date：2019-11-20 19:07
 * @describe：权限检查
 */
public class PermissionChecker {

    /**
     * 检查是否有某个权限
     *
     * @param ctx
     * @param permission
     * @return
     */
    public static boolean checkSelfPermission(Context ctx, String permission) {
        return ContextCompat.checkSelfPermission(ctx.getApplicationContext(), permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 检测多个权限
     *
     * @return 未授权的权限
     */
    public static List<String> checkMorePermissions(Context context, String[] permissions) {
        List<String> permissionList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (!checkSelfPermission(context, permissions[i]))
                permissionList.add(permissions[i]);
        }
        return permissionList;
    }

    /**
     * 检测多个权限
     *
     * @return 未授权的权限
     */
    public static List<String> checkMorePermissions(Context context, List<String> permissions) {
        List<String> permissionList = new ArrayList<>();
        for (int i = 0; i < permissions.size(); i++) {
            if (!checkSelfPermission(context, permissions.get(i)))
                permissionList.add(permissions.get(i));
        }
        return permissionList;
    }


    /**
     * 动态申请多个权限
     *
     * @param activity
     * @param code
     */
    public static void requestPermissions(Activity activity, @NonNull String[] permissions, int code) {
        ActivityCompat.requestPermissions(activity, permissions, code);
    }


    /**
     * Launch the application's details settings.
     */
    public static void launchAppDetailsSettings(Context context) {
        Context applicationContext = context.getApplicationContext();
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + applicationContext.getPackageName()));
        if (!isIntentAvailable(context, intent)) return;
        applicationContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    private static boolean isIntentAvailable(Context context, final Intent intent) {
        return context.getApplicationContext()
                .getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                .size() > 0;
    }
}
