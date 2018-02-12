package cc.ibooker.ibookereditorlib;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 书客编辑器布局
 * Created by 邹峰立 on 2018/2/11.
 */
public class IbookerEditorView extends LinearLayout {
    private IbookerEditorTopView ibookerEditorTopView;
    private IbookerEditorVpView ibookerEditorVpView;
    private IbookerEditorToolView ibookerEditorToolView;

    public IbookerEditorView(Context context) {
        this(context, null);
    }

    public IbookerEditorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IbookerEditorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setBackgroundColor(Color.parseColor("#FFFFFF"));

        init(context);
    }

    // 初始化
    private void init(Context context) {
        // 顶部
        ibookerEditorTopView = new IbookerEditorTopView(context);
        addView(ibookerEditorTopView);
        // 中间区域
        ibookerEditorVpView = new IbookerEditorVpView(context);
        ibookerEditorVpView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        addView(ibookerEditorVpView);
        // 底部工具栏
        ibookerEditorToolView = new IbookerEditorToolView(context);
        addView(ibookerEditorToolView);
    }
}
