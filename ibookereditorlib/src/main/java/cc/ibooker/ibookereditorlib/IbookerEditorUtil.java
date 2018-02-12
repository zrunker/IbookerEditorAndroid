package cc.ibooker.ibookereditorlib;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * 书客编辑器 - 工具类
 * Created by 邹峰立 on 2018/2/12.
 */
public class IbookerEditorUtil {
    /**
     * dp转换成px
     *
     * @param value 待转换值
     */
    static int dpToPx(Context context, float value) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm);
    }
}
