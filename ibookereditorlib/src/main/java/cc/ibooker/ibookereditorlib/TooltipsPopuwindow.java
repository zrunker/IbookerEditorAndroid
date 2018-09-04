package cc.ibooker.ibookereditorlib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Tooltips 提示框
 */
public class TooltipsPopuwindow extends PopupWindow {
    private TextView tooltipsTv;

    public TooltipsPopuwindow(Context context) {
        this(context, null);
    }

    public TooltipsPopuwindow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TooltipsPopuwindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.ibooker_editor_layout_tooltips, null);
        tooltipsTv = view.findViewById(R.id.tv_name);
        setContentView(view);
        setFocusable(true);
    }

    public void setTooltipsTv(String text) {
        tooltipsTv.setText(text);
    }

    /**
     * 显示在指定View的正上方
     *
     * @param view    指定的View
     * @param yOffset Y轴偏移量
     */
    public void showViewTop(Context context, View view, int yOffset) {
        if (context != null) {
            // 获取需要在其上方显示的控件的位置信息
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            // 获取自身的长宽高
            this.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int popupHeight = this.getContentView().getMeasuredHeight();
            showAtLocation(view, Gravity.NO_GRAVITY, location[0] - 5, location[1] - popupHeight - yOffset);
        }
    }

}
