package cc.ibooker.ibookereditorlib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    // 顶部控件
    private IbookerEditorTopView ibookerEditorTopView;
    // 中间区域ViewPager
    private IbookerEditorVpView ibookerEditorVpView;
    // 底部工具栏
    private IbookerEditorToolView ibookerEditorToolView;
    // 底部工具栏-操作类
    private IbookerEditorUtil ibookerEditorUtil;

    // 工具栏进入和退出动画
    private Animation inAnim, outAnim;

    // getter/setter
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

    public IbookerEditorUtil getIbookerEditorUtil() {
        return ibookerEditorUtil;
    }

    public void setIbookerEditorUtil(IbookerEditorUtil ibookerEditorUtil) {
        this.ibookerEditorUtil = ibookerEditorUtil;
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

        init(context, attrs);
    }

    // 初始化
    private void init(Context context, AttributeSet attrs) {
        // 顶部
        ibookerEditorTopView = new IbookerEditorTopView(context);
        ibookerEditorTopView.setOnTopClickListener(this);
        addView(ibookerEditorTopView);
        // 中间区域ViewPager
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
        ibookerEditorVpView.setCurrentItem(0);
        changeVpUpdateIbookerEditorTopView(0);
        addView(ibookerEditorVpView);
        // 底部工具栏
        ibookerEditorToolView = new IbookerEditorToolView(context);
        ibookerEditorToolView.setOnToolClickListener(this);
        addView(ibookerEditorToolView);
        // 底部工具栏 - 管理类
        ibookerEditorUtil = new IbookerEditorUtil(ibookerEditorVpView.getEditView());

        // 进入和退出动画
        inAnim = AnimationUtils.loadAnimation(context, R.anim.ibooker_editor_toolview_in);
        outAnim = AnimationUtils.loadAnimation(context, R.anim.ibooker_editor_toolview_out);

        if (attrs != null) {
            // 获取自定义属性，并设置
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.IbookerEditorView);

            // 顶部工具栏
            boolean ibookerEditorTopViewVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorTopView_Visible, true);
            ibookerEditorTopView.setVisibility(ibookerEditorTopViewVisible ? VISIBLE : GONE);

            // 返回按钮
            boolean backImgVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorTopView_BackImg_Visible, true);
            int backImgRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorTopView_BackImg_Res, R.drawable.icon_back_black);
            ibookerEditorTopView.getBackImg().setVisibility(backImgVisible ? VISIBLE : GONE);
            ibookerEditorTopView.getBackImg().setImageResource(backImgRes);

            // 撤销按钮
            boolean undoIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorTopView_UndoIBtn_Visible, true);
            int undoIBtnRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorTopView_UndoIBtn_Res, R.drawable.draw_undo);
            ibookerEditorTopView.getUndoIBtn().setVisibility(undoIBtnVisible ? VISIBLE : GONE);
            ibookerEditorTopView.getUndoIBtn().setBackgroundResource(undoIBtnRes);

            // 重做按钮
            boolean redoIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorTopView_RedoIBtn_Visible, true);
            int redoIBtnRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorTopView_RedoIBtn_Res, R.drawable.draw_redo);
            ibookerEditorTopView.getRedoIBtn().setVisibility(redoIBtnVisible ? VISIBLE : GONE);
            ibookerEditorTopView.getRedoIBtn().setBackgroundResource(redoIBtnRes);

            // 编辑模式
            boolean editIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorTopView_EditIBtn_Visible, true);
            int editIBtnRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorTopView_EditIBtn_Res, R.drawable.draw_edit);
            ibookerEditorTopView.getEditIBtn().setVisibility(editIBtnVisible ? VISIBLE : GONE);
            ibookerEditorTopView.getEditIBtn().setBackgroundResource(editIBtnRes);

            // 预览模式
            boolean previewIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorTopView_PreviewIBtn_Visible, true);
            int previewIBtnRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorTopView_PreviewIBtn_Res, R.drawable.draw_preview);
            ibookerEditorTopView.getPreviewIBtn().setVisibility(previewIBtnVisible ? VISIBLE : GONE);
            ibookerEditorTopView.getPreviewIBtn().setBackgroundResource(previewIBtnRes);

            // 帮助
            boolean helpIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorTopView_HelpIBtn_Visible, true);
            int helpIBtnRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorTopView_HelpIBtn_Res, R.drawable.draw_help);
            ibookerEditorTopView.getHelpIBtn().setVisibility(helpIBtnVisible ? VISIBLE : GONE);
            ibookerEditorTopView.getHelpIBtn().setBackgroundResource(helpIBtnRes);

            // 关于
            boolean aboutImgVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorTopView_AboutImg_Visible, true);
            int aboutImgRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorTopView_AboutImg_Res, R.drawable.ibooker_editor_logo);
            ibookerEditorTopView.getAboutImg().setVisibility(aboutImgVisible ? VISIBLE : GONE);
            ibookerEditorTopView.getAboutImg().setImageResource(aboutImgRes);

            // 编辑框
            int ibookerEditorEditViewBackgroundColor = ta.getColor(R.styleable.IbookerEditorView_IbookerEditorEditView_BackgroundColor, 0xffffffff);
            ibookerEditorVpView.getEditView().setBackgroundColor(ibookerEditorEditViewBackgroundColor);

            // 标题
            boolean titleEdVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorEditView_TitleEd_Visible, true);
            float titleEdTextSize = ta.getDimension(R.styleable.IbookerEditorView_IbookerEditorEditView_TitleEd_TextSize, 18f);
            int titleEdTextColor = ta.getColor(R.styleable.IbookerEditorView_IbookerEditorEditView_TitleEd_TextColor, 0xff444444);
            String titleEdHint = ta.getString(R.styleable.IbookerEditorView_IbookerEditorEditView_TitleEd_Hint);
            int titleEdHintTextColor = ta.getColor(R.styleable.IbookerEditorView_IbookerEditorEditView_TitleEd_HintTextColor, 0xff999999);
            ibookerEditorVpView.getEditView().getIbookerTitleEd().setVisibility(titleEdVisible ? VISIBLE : GONE);
            ibookerEditorVpView.getEditView().getIbookerTitleEd().setTextSize(titleEdTextSize);
            ibookerEditorVpView.getEditView().getIbookerTitleEd().setTextColor(titleEdTextColor);
            if (!TextUtils.isEmpty(titleEdHint))
                ibookerEditorVpView.getEditView().getIbookerTitleEd().setHint(titleEdHint);
            ibookerEditorVpView.getEditView().getIbookerTitleEd().setHintTextColor(titleEdHintTextColor);

            // 分割线
            boolean editLineViewVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorEditView_LineView_Visible, true);
            int editLineViewBackgroundColor = ta.getColor(R.styleable.IbookerEditorView_IbookerEditorEditView_LineView_BackgroundColor, 0xFFBABABA);
            ibookerEditorVpView.getEditView().getLineView().setVisibility(editLineViewVisible ? VISIBLE : GONE);
            ibookerEditorVpView.getEditView().getLineView().setBackgroundColor(editLineViewBackgroundColor);

            // 内容
            int ibookerEdBackgroundColor = ta.getColor(R.styleable.IbookerEditorView_IbookerEditorEditView_IbookerEd_BackgroundColor, ibookerEditorEditViewBackgroundColor);
            float ibookerEdTextSize = ta.getDimension(R.styleable.IbookerEditorView_IbookerEditorEditView_IbookerEd_TextSize, 16f);
            int ibookerEdTextColor = ta.getColor(R.styleable.IbookerEditorView_IbookerEditorEditView_IbookerEd_TextColor, 0xff444444);
            String ibookerEdHint = ta.getString(R.styleable.IbookerEditorView_IbookerEditorEditView_IbookerEd_Hint);
            int ibookerEdHintTextColor = ta.getColor(R.styleable.IbookerEditorView_IbookerEditorEditView_IbookerEd_HintTextColor, 0xff999999);
            ibookerEditorVpView.getEditView().getIbookerEd().setBackgroundColor(ibookerEdBackgroundColor);
            ibookerEditorVpView.getEditView().getIbookerEd().setTextSize(ibookerEdTextSize);
            ibookerEditorVpView.getEditView().getIbookerEd().setTextColor(ibookerEdTextColor);
            if (!TextUtils.isEmpty(ibookerEdHint))
                ibookerEditorVpView.getEditView().getIbookerEd().setHint(ibookerEdHint);
            ibookerEditorVpView.getEditView().getIbookerEd().setHintTextColor(ibookerEdHintTextColor);

            // 预览框
            int ibookerEditorPreViewBackgroundColor = ta.getColor(R.styleable.IbookerEditorView_IbookerEditorPreView_BackgroundColor, 0xffffffff);
            ibookerEditorVpView.getPreView().setBackgroundColor(ibookerEditorPreViewBackgroundColor);

            // 标题
            boolean titleTvVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorPreView_TitleTv_Visible, true);
            float titleTvTextSize = ta.getDimension(R.styleable.IbookerEditorView_IbookerEditorPreView_TitleTv_TextSize, 18f);
            int titleTvTextColor = ta.getColor(R.styleable.IbookerEditorView_IbookerEditorPreView_TitleTv_TextColor, 0xff444444);
            String titleTvHint = ta.getString(R.styleable.IbookerEditorView_IbookerEditorPreView_TitleTv_Hint);
            int titleTvHintTextColor = ta.getColor(R.styleable.IbookerEditorView_IbookerEditorPreView_TitleTv_HintTextColor, 0xff999999);
            ibookerEditorVpView.getPreView().getIbookerTitleTv().setVisibility(titleTvVisible ? VISIBLE : GONE);
            ibookerEditorVpView.getPreView().getIbookerTitleTv().setTextSize(titleTvTextSize);
            ibookerEditorVpView.getPreView().getIbookerTitleTv().setTextColor(titleTvTextColor);
            if (!TextUtils.isEmpty(titleTvHint))
                ibookerEditorVpView.getPreView().getIbookerTitleTv().setHint(titleTvHint);
            ibookerEditorVpView.getPreView().getIbookerTitleTv().setHintTextColor(titleTvHintTextColor);

            // 分割线
            boolean preLineViewVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorPreView_LineView_Visible, true);
            int preLineViewBackgroundColor = ta.getColor(R.styleable.IbookerEditorView_IbookerEditorPreView_LineView_BackgroundColor, 0xFFBABABA);
            ibookerEditorVpView.getPreView().getLineView().setVisibility(preLineViewVisible ? VISIBLE : GONE);
            ibookerEditorVpView.getPreView().getLineView().setBackgroundColor(preLineViewBackgroundColor);

            // 内容
            int ibookerWebViewBackgroundColor = ta.getColor(R.styleable.IbookerEditorView_IbookerEditorPreView_IbookerWebView_BackgroundColor, ibookerEditorEditViewBackgroundColor);
            ibookerEditorVpView.getPreView().getIbookerEditorWebView().setBackgroundColor(ibookerWebViewBackgroundColor);

            // 底部工具栏
            int ibookerEditorToolViewBackgroundColor = ta.getColor(R.styleable.IbookerEditorView_IbookerEditorToolView_BackgroundColor, 0xff);
            if (ibookerEditorToolViewBackgroundColor == 0xff)
                ibookerEditorToolView.setBackgroundResource(R.drawable.bg_ibooker_editor_tool);
            else
                ibookerEditorToolView.setBackgroundColor(ibookerEditorToolViewBackgroundColor);

            boolean boldIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_BoldIBtn_Visible, true);
            int boldIBtnRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_BoldIBtn_Res, R.drawable.draw_bold);
            ibookerEditorToolView.getBoldIBtn().setVisibility(boldIBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getBoldIBtn().setBackgroundResource(boldIBtnRes);

            boolean italicIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_ItalicIBtn_Visible, true);
            int italicIBtnRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_ItalicIBtn_Res, R.drawable.draw_italic);
            ibookerEditorToolView.getItalicIBtn().setVisibility(italicIBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getItalicIBtn().setBackgroundResource(italicIBtnRes);

            boolean strikeoutIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_StrikeoutIBtn_Visible, true);
            int strikeoutIBtnRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_StrikeoutIBtn_Res, R.drawable.draw_strikeout);
            ibookerEditorToolView.getStrikeoutIBtn().setVisibility(strikeoutIBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getStrikeoutIBtn().setBackgroundResource(strikeoutIBtnRes);

            boolean underlineIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_UnderlineIBtn_Visible, true);
            int underlineIBtnRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_UnderlineIBtn_Res, R.drawable.draw_underline);
            ibookerEditorToolView.getUnderlineIBtn().setVisibility(underlineIBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getUnderlineIBtn().setBackgroundResource(underlineIBtnRes);

            boolean capitalsIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_CapitalsIBtn_Visible, true);
            int capitalsIBtnVisibleRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_CapitalsIBtn_Res, R.drawable.draw_capitals);
            ibookerEditorToolView.getCapitalsIBtn().setVisibility(capitalsIBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getCapitalsIBtn().setBackgroundResource(capitalsIBtnVisibleRes);

            boolean uppercaseIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_UppercaseIBtn_Visible, true);
            int uppercaseIBtnVisibleRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_UppercaseIBtnBackImg_Res, R.drawable.draw_uppercase);
            ibookerEditorToolView.getUppercaseIBtn().setVisibility(uppercaseIBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getUppercaseIBtn().setBackgroundResource(uppercaseIBtnVisibleRes);

            boolean lowercaseIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_LowercaseIBtn_Visible, true);
            int lowercaseIBtnVisibleRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_LowercaseIBtn_Res, R.drawable.draw_lowercase);
            ibookerEditorToolView.getLowercaseIBtn().setVisibility(lowercaseIBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getLowercaseIBtn().setBackgroundResource(lowercaseIBtnVisibleRes);

            boolean h1IBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_H1IBtn_Visible, true);
            int h1IBtnVisibleRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_H1IBtn_Res, R.drawable.draw_h1);
            ibookerEditorToolView.getH1IBtn().setVisibility(h1IBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getH1IBtn().setBackgroundResource(h1IBtnVisibleRes);

            boolean h2IBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_H2IBtn_Visible, true);
            int h2IBtnVisibleRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_H2IBtn_Res, R.drawable.draw_h2);
            ibookerEditorToolView.getH2IBtn().setVisibility(h2IBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getH2IBtn().setBackgroundResource(h2IBtnVisibleRes);

            boolean h3IBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_H3IBtn_Visible, true);
            int h3IBtnVisibleRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_H3IBtn_Res, R.drawable.draw_h3);
            ibookerEditorToolView.getH3IBtn().setVisibility(h3IBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getH3IBtn().setBackgroundResource(h3IBtnVisibleRes);

            boolean h4IBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_H4IBtn_Visible, true);
            int h4IBtnVisibleRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_H4IBtn_Res, R.drawable.draw_h4);
            ibookerEditorToolView.getH4IBtn().setVisibility(h4IBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getH4IBtn().setBackgroundResource(h4IBtnVisibleRes);

            boolean h5IBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_H5IBtn_Visible, true);
            int h5IBtnVisibleRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_H5IBtn_Res, R.drawable.draw_h5);
            ibookerEditorToolView.getH5IBtn().setVisibility(h5IBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getH5IBtn().setBackgroundResource(h5IBtnVisibleRes);

            boolean h6IBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_H6IBtn_Visible, true);
            int h6IBtnVisibleRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_H6IBtn_Res, R.drawable.draw_h6);
            ibookerEditorToolView.getH6IBtn().setVisibility(h6IBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getH6IBtn().setBackgroundResource(h6IBtnVisibleRes);

            boolean linkIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_LinkIBtn_Visible, true);
            int linkIBtnVisibleRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_LinkIBtn_Res, R.drawable.draw_link);
            ibookerEditorToolView.getLinkIBtn().setVisibility(linkIBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getLinkIBtn().setBackgroundResource(linkIBtnVisibleRes);

            boolean quoteIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_QuoteIBtn_Visible, true);
            int quoteIBtnVisibleRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_QuoteIBtn_Res, R.drawable.draw_quote);
            ibookerEditorToolView.getQuoteIBtn().setVisibility(quoteIBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getQuoteIBtn().setBackgroundResource(quoteIBtnVisibleRes);

            boolean codeIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_CodeIBtn_Visible, true);
            int codeIBtnRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_CodeIBtn_Res, R.drawable.draw_code);
            ibookerEditorToolView.getCodeIBtn().setVisibility(codeIBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getCodeIBtn().setBackgroundResource(codeIBtnRes);

            boolean imguIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_ImguIBtn_Visible, true);
            int imguIBtnRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_ImguIBtn_Res, R.drawable.draw_img_u);
            ibookerEditorToolView.getImguIBtn().setVisibility(imguIBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getImguIBtn().setBackgroundResource(imguIBtnRes);

            boolean olIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_OlIBtn_Visible, true);
            int olIBtnRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_OlIBtn_Res, R.drawable.draw_ol);
            ibookerEditorToolView.getOlIBtn().setVisibility(olIBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getOlIBtn().setBackgroundResource(olIBtnRes);

            boolean ulIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_UlIBtn_Visible, true);
            int ulIBtnRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_UlIBtn_Res, R.drawable.draw_ul);
            ibookerEditorToolView.getUlIBtn().setVisibility(ulIBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getUlIBtn().setBackgroundResource(ulIBtnRes);

            boolean unselectedIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_UnselectedIBtn_Visible, true);
            int unselectedIBtnRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_UnselectedIBtn_Res, R.drawable.draw_unselected);
            ibookerEditorToolView.getUnselectedIBtn().setVisibility(unselectedIBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getUnselectedIBtn().setBackgroundResource(unselectedIBtnRes);

            boolean selectedIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_SelectedIBtn_Visible, true);
            int selectedIBtnRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_SelectedIBtn_Res, R.drawable.draw_selected);
            ibookerEditorToolView.getSelectedIBtn().setVisibility(selectedIBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getSelectedIBtn().setBackgroundResource(selectedIBtnRes);

            boolean tableIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_TableIBtn_Visible, true);
            int tableIBtnRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_TableIBtn_Res, R.drawable.draw_table);
            ibookerEditorToolView.getTableIBtn().setVisibility(tableIBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getTableIBtn().setBackgroundResource(tableIBtnRes);

            boolean htmlIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_HtmlIBtn_Visible, true);
            int htmlIBtnRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_HtmlIBtn_Res, R.drawable.draw_html);
            ibookerEditorToolView.getHtmlIBtn().setVisibility(htmlIBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getHtmlIBtn().setBackgroundResource(htmlIBtnRes);

            boolean hrIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_HrIBtn_Visible, true);
            int hrIBtnRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_HrIBtn_Res, R.drawable.draw_hr);
            ibookerEditorToolView.getHrIBtn().setVisibility(hrIBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getHrIBtn().setBackgroundResource(hrIBtnRes);

            boolean emojiIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorToolView_EmojiIBtn_Visible, true);
            int emojiIBtnRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorToolView_EmojiIBtng_Res, R.drawable.draw_emoji);
            ibookerEditorToolView.getEmojiIBtn().setVisibility(emojiIBtnVisible ? VISIBLE : GONE);
            ibookerEditorToolView.getEmojiIBtn().setBackgroundResource(emojiIBtnRes);

            ta.recycle();
        }

        ibookerEditorTopView.getEditIBtn().setBackgroundResource(R.drawable.icon_ibooker_editor_edit_orange);
        ibookerEditorTopView.getPreviewIBtn().setBackgroundResource(R.drawable.icon_ibooker_editor_preview_gray);
    }

    // 设置ViewPager变化
    private void changeVpUpdateIbookerEditorTopView(int position) {
        if (ibookerEditorTopView != null)
            if (position == 0) {
                ibookerEditorTopView.getEditIBtn().setBackgroundResource(R.drawable.icon_ibooker_editor_edit_orange);
                ibookerEditorTopView.getPreviewIBtn().setBackgroundResource(R.drawable.icon_ibooker_editor_preview_gray);

                // 设置动画
                if (ibookerEditorToolView != null) {
                    if (inAnim != null)
                        ibookerEditorToolView.startAnimation(inAnim);
                    ibookerEditorToolView.setVisibility(VISIBLE);
                }

                openInputSoft(true);
            } else if (position == 1) {
                ibookerEditorTopView.getEditIBtn().setBackgroundResource(R.drawable.icon_ibooker_editor_edit_gray);
                ibookerEditorTopView.getPreviewIBtn().setBackgroundResource(R.drawable.icon_ibooker_editor_preview_orange);
                // 执行预览
                ibookerCompile();

                // 设置动画
                if (ibookerEditorToolView != null) {
                    if (outAnim != null)
                        ibookerEditorToolView.startAnimation(outAnim);
                    ibookerEditorToolView.setVisibility(GONE);
                }

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
            ibookerEditorVpView.getEditView().undo();
        } else if (tag.equals(IBTN_REDO)) {// 重做
            ibookerEditorVpView.getEditView().redo();
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
        if (ibookerEditorUtil == null)// 初始化ibookerEditorUtil
            ibookerEditorUtil = new IbookerEditorUtil(ibookerEditorVpView.getEditView());
        if (ibookerEditorVpView.getCurrentItem() != 0)// 切换到编辑器模式
            ibookerEditorVpView.setCurrentItem(0);
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
    }

    // 执行Text预览
    private void ibookerCompile() {
        // 获取待转义内容
        String title = ibookerEditorVpView.getEditView().getIbookerTitleEd().getText().toString().trim();
        String content = ibookerEditorVpView.getEditView().getIbookerEd().getText().toString().trim();
        // 执行预览
        if (!TextUtils.isEmpty(title))
            ibookerEditorVpView.getPreView().getIbookerTitleTv().setText(title);
        if (!TextUtils.isEmpty(content))
            ibookerEditorVpView.getPreView().getIbookerEditorWebView().ibookerCompile(content);
    }

    // 销毁方法
    public void destoryIbookerEditor() {
        inAnim.cancel();
        inAnim = null;
        outAnim.cancel();
        outAnim = null;
        ibookerEditorVpView.getPreView().getIbookerEditorWebView().destroy();
    }

    /**
     * 显示或者隐藏顶部工具栏
     *
     * @param visibility 状态
     */
    public IbookerEditorView setIbookerEditorTopViewVisibility(int visibility) {
        if (visibility == VISIBLE || visibility == GONE || visibility == INVISIBLE)
            ibookerEditorTopView.setVisibility(visibility);
        return this;
    }

    /**
     * 设置编辑控件背景颜色
     *
     * @param color 背景颜色
     */
    public IbookerEditorView setIbookerEditorEditViewBackgroundColor(@ColorInt int color) {
        ibookerEditorVpView.getEditView().setBackgroundColor(color);
        ibookerEditorVpView.getEditView().getIbookerTitleEd().setBackgroundColor(color);
        ibookerEditorVpView.getEditView().getIbookerEd().setBackgroundColor(color);
        return this;
    }

    /**
     * 设置预览控件背景颜色
     *
     * @param color 背景颜色
     */
    public IbookerEditorView setIbookerEditorPreViewBackgroundColor(@ColorInt int color) {
        ibookerEditorVpView.getPreView().setBackgroundColor(color);
        ibookerEditorVpView.getPreView().getIbookerTitleTv().setBackgroundColor(color);
        ibookerEditorVpView.getPreView().getIbookerEditorWebView().setBackgroundColor(color);
        return this;
    }
}
