package cc.ibooker.ibookereditorlib;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * 书客编辑器 - 编辑界面
 * Created by 邹峰立 on 2018/2/11.
 */
public class IbookerEditorEditView extends LinearLayout {
    private EditText ibookerEd;

    public EditText getIbookerEd() {
        return ibookerEd;
    }

    public void setIbookerEd(EditText ibookerEd) {
        this.ibookerEd = ibookerEd;
    }

    // 三种构造方法
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
        ibookerEd.setInputType(InputType.TYPE_CLASS_TEXT);
        ibookerEd.setPadding(IbookerEditorUtil.dpToPx(context, 8), IbookerEditorUtil.dpToPx(context, 8), IbookerEditorUtil.dpToPx(context, 8), IbookerEditorUtil.dpToPx(context, 8));
        ibookerEd.setBackgroundResource(android.R.color.transparent);
//        ibookerEd.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
//            @Override
//            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//                return false;
//            }
//
//            @Override
//            public void onDestroyActionMode(ActionMode mode) {
//            }
//
//            @Override
//            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//                return false;// 返回false 就是屏蔽ActionMode菜单
//            }
//
//            @Override
//            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//                return false;
//            }
//        });
        addView(ibookerEd);

        ((Activity) ibookerEd.getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    // 设置输入框
    public IbookerEditorEditView setIbookerEdTextSize(float size) {
        ibookerEd.setTextSize(size);
        return this;
    }

    public IbookerEditorEditView setIbookerEdTextColor(@ColorInt int color) {
        ibookerEd.setTextColor(color);
        return this;
    }

    public IbookerEditorEditView setIbookerEdHint(CharSequence hint) {
        ibookerEd.setHint(hint);
        return this;
    }

    public IbookerEditorEditView setIbookerEdHintTextColor(@ColorInt int color) {
        ibookerEd.setHintTextColor(color);
        return this;
    }

    public IbookerEditorEditView setIbookerEdBackgroundColor(@ColorInt int color) {
        ibookerEd.setBackgroundColor(color);
        return this;
    }

}
