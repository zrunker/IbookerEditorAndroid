package cc.ibooker.ibookereditorlib;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * 书客编辑器 - 编辑界面
 * Created by 邹峰立 on 2018/2/11.
 */
public class IbookerEditorEditView extends LinearLayout {
    private EditText ibookerEd;
    private IbookerEditorToolView ibookerEditorToolView;

    public IbookerEditorEditView(Context context) {
        this(context, null);
    }

    public IbookerEditorEditView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IbookerEditorEditView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOrientation(VERTICAL);
        this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        init(context);
    }

    // 初始化
    private void init(Context context) {
        ibookerEd = new EditText(context);
        ibookerEd.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        ibookerEd.setGravity(Gravity.TOP | Gravity.START);
        ibookerEd.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        ibookerEd.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
        ibookerEd.setBackgroundResource(android.R.color.transparent);
        addView(ibookerEd);

        // 菜单工具栏
//        ibookerEditorToolView = new IbookerEditorToolView(context);
//        addView(ibookerEditorToolView);
    }

    /**
     * dp转换成px
     *
     * @param value 待转换值
     */
    public int dpToPx(float value) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm);
    }

}
