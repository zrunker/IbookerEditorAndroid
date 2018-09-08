package cc.ibooker.ibookereditorlib;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * 屏幕亮度管理类
 */
public class ScreenBrightnessUtil {

    /**
     * 判断是否为自动亮度
     */
    public static boolean isAutoBrightness(Context context) {
        boolean isAutoBrightness = false;
        try {
            ContentResolver contentResolver = context.getApplicationContext().getContentResolver();
            isAutoBrightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS)
                    == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return isAutoBrightness;
    }

    /**
     * 开启自动亮度
     */
    public synchronized static void startAutoBrightness(Context context) {
        Settings.System.putInt(context.getApplicationContext().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    /**
     * 停止自动亮度
     */
    public synchronized static void stopAutoBrightness(Context context) {
        Settings.System.putInt(context.getApplicationContext().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    /**
     * 设置屏幕亮度
     *
     * @param light 亮度值 1-255
     */
    public synchronized static void saveBrightness(Context context, int light) {
        if (light < 1 || light > 255) return;
        ContentResolver resolver = context.getApplicationContext().getContentResolver();
        Uri uri = Settings.System.getUriFor("screen_brightness");
        Settings.System.putInt(resolver, "screen_brightness", light);
        resolver.notifyChange(uri, null);

    }

    /**
     * 检测权限
     */
    public synchronized static boolean checkPermission(Context context, boolean isJump) {
        boolean isNeedPermission = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.System.canWrite(context.getApplicationContext())) {
            isNeedPermission = true;
            if (isJump)
                enterSettingIntent(context);
        }
        return isNeedPermission;
    }

    /**
     * 进入设置界面
     */
    public synchronized static void enterSettingIntent(Context context) {
        Uri selfPackageUri = Uri.parse("package:" + context.getApplicationContext().getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, selfPackageUri);
        context.startActivity(intent);
    }
}
