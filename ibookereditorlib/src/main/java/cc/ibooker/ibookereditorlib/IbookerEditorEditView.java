package cc.ibooker.ibookereditorlib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 书客编辑器 - 编辑界面
 * Created by 邹峰立 on 2018/2/11.
 */
public class IbookerEditorEditView extends NestedScrollView {
    private EditText ibookerTitleEd;
    private View lineView;
    private EditText ibookerEd;
    private float currentTextSize = 16f;

    private ArrayList<String> textList = new ArrayList<>();
    private boolean isSign = true;// 标记是否需要记录currentPos=textList.size()和textList
    private int currentPos = 0;

    private int dp10;

    public float getCurrentTextSize() {
        return currentTextSize;
    }

    public EditText getIbookerTitleEd() {
        return ibookerTitleEd;
    }

    public void setIbookerTitleEd(EditText ibookerTitleEd) {
        this.ibookerTitleEd = ibookerTitleEd;
    }

    public View getLineView() {
        return lineView;
    }

    public void setLineView(View lineView) {
        this.lineView = lineView;
    }

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
        setVerticalScrollBarEnabled(false);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setFillViewport(true);

        dp10 = IbookerEditorUtil.dpToPx(context, 10F);

        init(context);
    }

    // 初始化
    private void init(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        addView(linearLayout);

        ibookerTitleEd = new EditText(context);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, IbookerEditorUtil.dpToPx(context, 50F));
        titleParams.leftMargin = dp10;
        titleParams.rightMargin = dp10;
        ibookerTitleEd.setLayoutParams(titleParams);
        ibookerTitleEd.setPadding(0, 0, 0, 0);
        ibookerTitleEd.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        ibookerTitleEd.setSingleLine(true);
        ibookerTitleEd.setLines(1);
        ibookerTitleEd.setTextColor(Color.parseColor("#444444"));
        ibookerTitleEd.setTextSize(18f);
        ibookerTitleEd.setLineSpacing(4f, 1.3f);
        ibookerTitleEd.setHint("标题");
        ibookerTitleEd.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        ibookerTitleEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (onIbookerTitleEdTextChangedListener != null)
                    onIbookerTitleEdTextChangedListener.beforeTextChanged(s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onIbookerTitleEdTextChangedListener != null)
                    onIbookerTitleEdTextChangedListener.onTextChanged(s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (onIbookerTitleEdTextChangedListener != null)
                    onIbookerTitleEdTextChangedListener.afterTextChanged(s);
            }
        });
        linearLayout.addView(ibookerTitleEd);

        lineView = new View(context);
        lineView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        lineView.setBackgroundColor(Color.parseColor("#BABABA"));
        linearLayout.addView(lineView);

        ibookerEd = new EditText(context);
        ibookerEd.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));
        ibookerEd.setGravity(Gravity.TOP | Gravity.START);
        ibookerEd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        ibookerEd.setSingleLine(false);
        ibookerEd.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
        ibookerEd.setHint("书客编辑器，从这里开始");
        ibookerEd.setPadding(dp10, dp10, dp10, dp10);
        ibookerEd.setBackgroundResource(android.R.color.transparent);
        ibookerEd.setTextColor(Color.parseColor("#444444"));
        ibookerEd.setTextSize(currentTextSize);
        ibookerEd.setLineSpacing(4f, 1.3f);
        ibookerEd.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                try {
                    Field mEditor = TextView.class.getDeclaredField("mEditor");// 找到 TextView中的成员变量mEditor
                    mEditor.setAccessible(true);
                    Object object = mEditor.get(ibookerEd);// 根据持有对象拿到mEditor变量里的值 （android.widget.Editor类的实例）

                    //--------------------显示选择控制工具------------------------------//
                    @SuppressLint("PrivateApi")
                    Class mClass = Class.forName("android.widget.Editor");// 拿到隐藏类Editor；
                    Method method = mClass.getDeclaredMethod("getSelectionController");// 取得方法  getSelectionController
                    method.setAccessible(true);// 取消访问私有方法的合法性检查
                    Object resultobject = method.invoke(object);// 调用方法，返回SelectionModifierCursorController类的实例

                    Method show = resultobject.getClass().getDeclaredMethod("show");// 查找 SelectionModifierCursorController类中的show方法
                    show.invoke(resultobject);// 执行SelectionModifierCursorController类的实例的show方法
                    ibookerEd.setHasTransientState(true);

                    //--------------------忽略最后一次TouchUP事件------------------------------//
                    Field mSelectionActionMode = mClass.getDeclaredField("mDiscardNextActionUp");// 查找变量Editor类中mDiscardNextActionUp
                    mSelectionActionMode.setAccessible(true);
                    mSelectionActionMode.set(object, true);//赋值为true
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;// 返回false 就是屏蔽ActionMode菜单
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
        ibookerEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (onIbookerEdTextChangedListener != null)
                    onIbookerEdTextChangedListener.beforeTextChanged(s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onIbookerEdTextChangedListener != null)
                    onIbookerEdTextChangedListener.onTextChanged(s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (textList == null)
                    textList = new ArrayList<>();
                if (s != null) {
                    if (isSign) {
                        textList.add(s.toString());
                        currentPos = textList.size();
                    }
                }
                if (onIbookerEdTextChangedListener != null)
                    onIbookerEdTextChangedListener.afterTextChanged(s);
            }
        });
        ibookerEd.setHorizontallyScrolling(false);
        linearLayout.addView(ibookerEd);

        ((Activity) ibookerEd.getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    // 重做 - redo
    protected void redo() {
        if (textList != null && textList.size() > 0 && (currentPos + 1) >= 0 && (currentPos + 1) < textList.size()) {
            isSign = false;
            currentPos++;
            ibookerEd.setText(textList.get(currentPos));
            ibookerEd.setSelection(textList.get(currentPos).length());
            isSign = true;
        }
    }

    // 撤销 - undo
    protected void undo() {
        if (textList != null && textList.size() > 0) {
            isSign = false;
            if (currentPos == 0)
                ibookerEd.setText("");
            if ((currentPos - 1) >= 0 && (currentPos - 1) < textList.size()) {
                currentPos--;
                ibookerEd.setText(textList.get(currentPos));
                ibookerEd.setSelection(textList.get(currentPos).length());
            }
            isSign = true;
        }

    }

    /**
     * 设置输入框字体大小
     *
     * @param size 字体大小
     */
    public IbookerEditorEditView setIbookerEdTextSize(float size) {
        currentTextSize = size;
        ibookerEd.setTextSize(size);
        return this;
    }

    /**
     * 设置输入框字体颜色
     *
     * @param color 字体颜色
     */
    public IbookerEditorEditView setIbookerEdTextColor(@ColorInt int color) {
        ibookerEd.setTextColor(color);
        return this;
    }

    /**
     * 设置输入框hint内容
     *
     * @param hint hint内容
     */
    public IbookerEditorEditView setIbookerEdHint(CharSequence hint) {
        ibookerEd.setHint(hint);
        return this;
    }

    /**
     * 设置输入框hint颜色
     *
     * @param color hint颜色
     */
    public IbookerEditorEditView setIbookerEdHintTextColor(@ColorInt int color) {
        ibookerEd.setHintTextColor(color);
        return this;
    }

    /**
     * 设置输入框背景颜色
     *
     * @param color 背景颜色
     */
    public IbookerEditorEditView setIbookerEdBackgroundColor(@ColorInt int color) {
        ibookerEd.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置标题显示或者隐藏
     *
     * @param visibility View.GONE,View.VISIBLE,View.INVISIBLE
     */
    public IbookerEditorEditView setIbookerTitleEdVisibility(int visibility) {
        if (visibility == View.GONE || visibility == View.VISIBLE || visibility == View.INVISIBLE) {
            if (ibookerTitleEd != null)
                ibookerTitleEd.setVisibility(visibility);
            if (lineView != null)
                lineView.setVisibility(visibility);
        }
        return this;
    }

    /**
     * 设置标题输入框字体大小
     *
     * @param size 字体大小
     */
    public IbookerEditorEditView setIbookerTitleEdTextSize(float size) {
        ibookerTitleEd.setTextSize(size);
        return this;
    }

    /**
     * 设置标题输入框字体颜色
     *
     * @param color 字体颜色
     */
    public IbookerEditorEditView setIbookerTitleEdTextColor(@ColorInt int color) {
        ibookerTitleEd.setTextColor(color);
        return this;
    }

    /**
     * 设置标题输入框hint内容
     *
     * @param hint hint内容
     */
    public IbookerEditorEditView setIbookerTitleEdHint(CharSequence hint) {
        ibookerTitleEd.setHint(hint);
        return this;
    }

    /**
     * 设置标题输入框hint颜色
     *
     * @param color hint颜色
     */
    public IbookerEditorEditView setIbookerTitleEdHintTextColor(@ColorInt int color) {
        ibookerTitleEd.setHintTextColor(color);
        return this;
    }

    /**
     * 设置线条的背景颜色
     *
     * @param color 颜色
     */
    public IbookerEditorEditView setLineViewBackgroundColor(@ColorInt int color) {
        lineView.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置线条显示或者隐藏
     *
     * @param visibility View.GONE,View.VISIBLE,View.INVISIBLE
     */
    public IbookerEditorEditView setLineViewVisibility(int visibility) {
        if (visibility == View.GONE || visibility == View.VISIBLE || visibility == View.INVISIBLE)
            lineView.setVisibility(visibility);
        return this;
    }

    /**
     * 标题输入框输入监听
     */
    public interface OnIbookerTitleEdTextChangedListener {
        void beforeTextChanged(CharSequence s, int start, int count, int after);

        void onTextChanged(CharSequence s, int start, int before, int count);

        void afterTextChanged(Editable s);
    }

    private OnIbookerTitleEdTextChangedListener onIbookerTitleEdTextChangedListener;

    public void setOnIbookerTitleEdTextChangedListener(OnIbookerTitleEdTextChangedListener onIbookerTitleEdTextChangedListener) {
        this.onIbookerTitleEdTextChangedListener = onIbookerTitleEdTextChangedListener;
    }

    /**
     * 内容输入框监听
     */
    public interface OnIbookerEdTextChangedListener {
        void beforeTextChanged(CharSequence s, int start, int count, int after);

        void onTextChanged(CharSequence s, int start, int before, int count);

        void afterTextChanged(Editable s);
    }

    private OnIbookerEdTextChangedListener onIbookerEdTextChangedListener;

    public void setOnIbookerEdTextChangedListener(OnIbookerEdTextChangedListener onIbookerEdTextChangedListener) {
        this.onIbookerEdTextChangedListener = onIbookerEdTextChangedListener;
    }
}
