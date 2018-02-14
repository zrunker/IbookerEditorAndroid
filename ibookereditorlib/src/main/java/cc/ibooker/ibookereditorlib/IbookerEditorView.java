package cc.ibooker.ibookereditorlib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_ABOUT;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_BOLD;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_CAPITALS;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_CODE;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_EDIT;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_H1;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_H2;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_H3;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_H4;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_H5;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_H6;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_HELP;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_HR;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_HTML;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_IMG_U;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_ITALIC;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_LINK;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_LOWERCASE;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_OL;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_PREVIEW;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_QUOTE;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_REDO;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_SELECTED;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_STRIKEOUT;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_TABLE;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_UL;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_UNDERLINE;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_UNDO;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_UNSELECTED;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_UPPERCASE;
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IMG_BACK;

/**
 * 书客编辑器布局
 * Created by 邹峰立 on 2018/2/11.
 */
public class IbookerEditorView extends LinearLayout implements IbookerEditorTopView.OnTopClickListener, IbookerEditorToolView.OnToolClickListener {
    private IbookerEditorTopView ibookerEditorTopView;
    private IbookerEditorVpView ibookerEditorVpView;
    private IbookerEditorToolView ibookerEditorToolView;
    private IbookerEditorUtil ibookerEditorUtil;

    public IbookerEditorTopView getIbookerEditorTopView() {
        return ibookerEditorTopView;
    }

    public void setIbookerEditorTopView(IbookerEditorTopView ibookerEditorTopView) {
        this.ibookerEditorTopView = ibookerEditorTopView;
    }

    public IbookerEditorVpView getIbookerEditorVpView() {
        return ibookerEditorVpView;
    }

    public void setIbookerEditorVpView(IbookerEditorVpView ibookerEditorVpView) {
        this.ibookerEditorVpView = ibookerEditorVpView;
    }

    public IbookerEditorToolView getIbookerEditorToolView() {
        return ibookerEditorToolView;
    }

    public void setIbookerEditorToolView(IbookerEditorToolView ibookerEditorToolView) {
        this.ibookerEditorToolView = ibookerEditorToolView;
    }

    // 构造方法
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
        ibookerEditorVpView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeVpUpdateIbookerEditorTopView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        addView(ibookerEditorVpView);
        ibookerEditorVpView.setCurrentItem(0);
        changeVpUpdateIbookerEditorTopView(0);
        // 底部工具栏
        ibookerEditorToolView = new IbookerEditorToolView(context);
        ibookerEditorToolView.setOnToolClickListener(this);
        addView(ibookerEditorToolView);
    }

    // 设置ViewPager变化
    private void changeVpUpdateIbookerEditorTopView(int position) {
        if (ibookerEditorTopView != null)
            if (position == 0) {
                ibookerEditorTopView.getEditIBtn().setBackgroundResource(R.drawable.icon_ibooker_editor_edit_orange);
                ibookerEditorTopView.getPreviewIBtn().setBackgroundResource(R.drawable.icon_ibooker_editor_preview_gray);
                openInputSoft(true);
            } else if (position == 1) {
                ibookerEditorTopView.getEditIBtn().setBackgroundResource(R.drawable.icon_ibooker_editor_edit_gray);
                ibookerEditorTopView.getPreviewIBtn().setBackgroundResource(R.drawable.icon_ibooker_editor_preview_orange);
                openInputSoft(false);
            }
    }

    // 关闭/开启软盘
    private void openInputSoft(boolean isOpen) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            if (isOpen)
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
            else
                imm.hideSoftInputFromWindow(getApplicationWindowToken(), 0);
        }
    }

    // 顶部按钮点击事件监听
    @Override
    public void onTopClick(Object tag) {
        if (tag.equals(IMG_BACK)) {// 返回
            ((Activity) getContext()).finish();
        } else if (tag.equals(IBTN_UNDO)) {// 撤销

        } else if (tag.equals(IBTN_REDO)) {// 重做

        } else if (tag.equals(IBTN_EDIT)) {// 编辑
            ibookerEditorVpView.setCurrentItem(0);
        } else if (tag.equals(IBTN_PREVIEW)) {// 预览
            ibookerEditorVpView.setCurrentItem(1);
        } else if (tag.equals(IBTN_HELP)) {// 帮助
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse("http://ibooker.cc/article/1/detail");
            intent.setData(content_url);
            getContext().startActivity(intent);
        } else if (tag.equals(IBTN_ABOUT)) {// 关于
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse("http://ibooker.cc/article/182/detail");
            intent.setData(content_url);
            getContext().startActivity(intent);
        }
    }

    // 工具栏按钮点击事件监听
    @Override
    public void onToolClick(Object tag) {
        if (ibookerEditorUtil == null)
            ibookerEditorUtil = new IbookerEditorUtil(ibookerEditorVpView.getEditView());
        if (tag.equals(IBTN_BOLD)) {// 加粗
            ibookerEditorUtil.bold();
        } else if (tag.equals(IBTN_ITALIC)) {// 斜体
            ibookerEditorUtil.italic();
        } else if (tag.equals(IBTN_STRIKEOUT)) {// 删除线
            ibookerEditorUtil.strikeout();
        } else if (tag.equals(IBTN_UNDERLINE)) {// 下划线
            ibookerEditorUtil.underline();
        } else if (tag.equals(IBTN_CAPITALS)) {// 单词首字母大写
            ibookerEditorUtil.capitals();
        } else if (tag.equals(IBTN_UPPERCASE)) {// 字母转大写
            ibookerEditorUtil.uppercase();
        } else if (tag.equals(IBTN_LOWERCASE)) {// 字母转小写
            ibookerEditorUtil.lowercase();
        } else if (tag.equals(IBTN_H1)) {// 一级标题
            ibookerEditorUtil.h1();
        } else if (tag.equals(IBTN_H2)) {// 二级标题
            ibookerEditorUtil.h2();
        } else if (tag.equals(IBTN_H3)) {// 三级标题
            ibookerEditorUtil.h3();
        } else if (tag.equals(IBTN_H4)) {// 四级标题
            ibookerEditorUtil.h4();
        } else if (tag.equals(IBTN_H5)) {// 五级标题
            ibookerEditorUtil.h5();
        } else if (tag.equals(IBTN_H6)) {// 六级标题
            ibookerEditorUtil.h6();
        } else if (tag.equals(IBTN_LINK)) {// 超链接
            ibookerEditorUtil.link();
        } else if (tag.equals(IBTN_QUOTE)) {// 引用
            ibookerEditorUtil.quote();
        } else if (tag.equals(IBTN_CODE)) {// 代码
            ibookerEditorUtil.code();
        } else if (tag.equals(IBTN_IMG_U)) {// 图片
            ibookerEditorUtil.imgu();
        } else if (tag.equals(IBTN_OL)) {// 数字列表
            ibookerEditorUtil.ol();
        } else if (tag.equals(IBTN_UL)) {// 普通列表
            ibookerEditorUtil.ul();
        } else if (tag.equals(IBTN_UNSELECTED)) {// 复选框未选中
            ibookerEditorUtil.tasklistsUnChecked();
        } else if (tag.equals(IBTN_SELECTED)) {// 复选框选中
            ibookerEditorUtil.tasklistsChecked();
        } else if (tag.equals(IBTN_TABLE)) {// 表格
            ibookerEditorUtil.tables();
        } else if (tag.equals(IBTN_HTML)) {// HTML
            ibookerEditorUtil.html();
        } else if (tag.equals(IBTN_HR)) {// 分割线
            ibookerEditorUtil.hr();
        }

        // 执行预览
    }
}
