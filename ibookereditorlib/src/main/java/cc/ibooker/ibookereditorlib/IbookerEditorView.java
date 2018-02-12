package cc.ibooker.ibookereditorlib;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_ABOUT;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_EDIT;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_HELP;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_PREVIEW;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_REDO;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_UNDO;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IMG_BACK;

/**
 * 书客编辑器布局
 * Created by 邹峰立 on 2018/2/11.
 */
public class IbookerEditorView extends LinearLayout implements IbookerEditorTopView.OnTopClickListener {
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
        ibookerEditorTopView.setOnTopClickListener(this);
        addView(ibookerEditorTopView);
        // 中间区域
        ibookerEditorVpView = new IbookerEditorVpView(context);
        ibookerEditorVpView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        addView(ibookerEditorVpView);
        // 底部工具栏
        ibookerEditorToolView = new IbookerEditorToolView(context);
        addView(ibookerEditorToolView);
    }

    // 顶部按钮点击事件监听
    @Override
    public void onTopClick(Object tag) {
        if (tag.equals(IMG_BACK)) {// 返回
            ((Activity) getContext()).finish();
        } else if (tag.equals(IBTN_UNDO)) {// 撤销

        } else if (tag.equals(IBTN_REDO)) {// 重做

        } else if (tag.equals(IBTN_EDIT)) {// 编辑

        } else if (tag.equals(IBTN_PREVIEW)) {// 预览

        } else if (tag.equals(IBTN_HELP)) {// 帮助

        } else if (tag.equals(IBTN_ABOUT)) {// 关于

        }
    }
}
