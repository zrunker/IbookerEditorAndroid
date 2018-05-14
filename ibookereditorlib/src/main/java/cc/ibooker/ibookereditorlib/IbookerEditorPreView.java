package cc.ibooker.ibookereditorlib;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 书客编辑器 - 预览界面 - 自定义WebView
 * Created by 邹峰立 on 2018/2/11.
 */
public class IbookerEditorPreView extends NestedScrollView {
    private TextView ibookerTitleTv;
    private View lineView;
    private IbookerEditorWebView ibookerEditorWebView;

    public TextView getIbookerTitleTv() {
        return ibookerTitleTv;
    }

    public void setIbookerTitleTv(TextView ibookerTitleTv) {
        this.ibookerTitleTv = ibookerTitleTv;
    }

    public View getLineView() {
        return lineView;
    }

    public void setLineView(View lineView) {
        this.lineView = lineView;
    }

    public IbookerEditorWebView getIbookerEditorWebView() {
        return ibookerEditorWebView;
    }

    public void setIbookerEditorWebView(IbookerEditorWebView ibookerEditorWebView) {
        this.ibookerEditorWebView = ibookerEditorWebView;
    }

    public IbookerEditorPreView(@NonNull Context context) {
        this(context, null);
    }

    public IbookerEditorPreView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IbookerEditorPreView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setVerticalScrollBarEnabled(false);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        init(context);
    }

    /**
     * 初始化
     */
    private void init(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        addView(linearLayout);

        ibookerTitleTv = new TextView(context);
        ibookerTitleTv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, IbookerEditorUtil.dpToPx(context, 50)));
        ibookerTitleTv.setPadding(IbookerEditorUtil.dpToPx(context, 10F), IbookerEditorUtil.dpToPx(context, 10F), IbookerEditorUtil.dpToPx(context, 10F), IbookerEditorUtil.dpToPx(context, 10F));
        ibookerTitleTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        ibookerTitleTv.setSingleLine(true);
        ibookerTitleTv.setLines(1);
        ibookerTitleTv.setEllipsize(TextUtils.TruncateAt.END);
        ibookerTitleTv.setTextColor(Color.parseColor("#444444"));
        ibookerTitleTv.setTextSize(18f);
        ibookerTitleTv.setLineSpacing(4f, 1.3f);
        ibookerTitleTv.setHint("标题");
        ibookerTitleTv.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.addView(ibookerTitleTv);

        lineView = new View(context);
        lineView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        lineView.setBackgroundColor(Color.parseColor("#BABABA"));
        linearLayout.addView(lineView);

        ibookerEditorWebView = new IbookerEditorWebView(context);
        linearLayout.addView(ibookerEditorWebView);
    }

    /**
     * 设置WebView控件背景颜色
     *
     * @param color 背景颜色
     */
    public IbookerEditorPreView setIbookerEditorWebViewBackgroundColor(@ColorInt int color) {
        ibookerEditorWebView.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置标题显示 或者隐藏
     *
     * @param visibility View.GONE,View.VISIBLE,View.INVISIBLE
     */
    public IbookerEditorPreView setIbookerTitleTdVisibility(int visibility) {
        if (visibility == View.GONE || visibility == View.VISIBLE || visibility == View.INVISIBLE) {
            if (ibookerTitleTv != null)
                ibookerTitleTv.setVisibility(visibility);
            if (lineView != null)
                lineView.setVisibility(visibility);
        }
        return this;
    }

    /**
     * 设置标题字体大小
     *
     * @param size 字体大小
     */
    public IbookerEditorPreView setIbookerTitleTdTextSize(float size) {
        ibookerTitleTv.setTextSize(size);
        return this;
    }

    /**
     * 设置标题字体颜色
     *
     * @param color 字体颜色
     */
    public IbookerEditorPreView setIbookerTitleTdTextColor(@ColorInt int color) {
        ibookerTitleTv.setTextColor(color);
        return this;
    }

    /**
     * 设置标题hint内容
     *
     * @param hint hint内容
     */
    public IbookerEditorPreView setIbookerTitleTdHint(CharSequence hint) {
        ibookerTitleTv.setHint(hint);
        return this;
    }

    /**
     * 设置标题hint颜色
     *
     * @param color hint颜色
     */
    public IbookerEditorPreView setIbookerTitleTdHintTextColor(@ColorInt int color) {
        ibookerTitleTv.setHintTextColor(color);
        return this;
    }

    /**
     * 设置线条的背景颜色
     *
     * @param color 颜色
     */
    public IbookerEditorPreView setLineViewBackgroundColor(@ColorInt int color) {
        lineView.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置线条显示或者隐藏
     *
     * @param visibility View.GONE,View.VISIBLE,View.INVISIBLE
     */
    public IbookerEditorPreView setLineViewVisibility(int visibility) {
        if (visibility == View.GONE || visibility == View.VISIBLE || visibility == View.INVISIBLE)
            lineView.setVisibility(visibility);
        return this;
    }
}
