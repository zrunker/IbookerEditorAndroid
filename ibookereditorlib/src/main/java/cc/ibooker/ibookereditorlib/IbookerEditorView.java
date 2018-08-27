package cc.ibooker.ibookereditorlib;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

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
import static cc.ibooker.ibookereditorlib.IbookerEditorEnum.TOOLVIEW_TAG.IBTN_SHARE;
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

    // 权限申请模块
    private String[] needPermissions = {
            // SDK在Android 6.0+需要进行运行检测的权限如下：
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    // 工具栏进入和退出动画
    private Animation inAnim, outAnim;
    private int editIBtnDefaultRes = R.drawable.icon_ibooker_editor_edit_gray,
            editIBtnSelectedRes = R.drawable.icon_ibooker_editor_edit_orange,
            previewIBtnDefaultRes = R.drawable.icon_ibooker_editor_preview_gray,
            previewIBtnSelectedRes = R.drawable.icon_ibooker_editor_preview_orange;

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
            editIBtnDefaultRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorTopView_EditIBtn_Default_Res, editIBtnDefaultRes);
            editIBtnSelectedRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorTopView_EditIBtn_Selected_Res, editIBtnSelectedRes);
            ibookerEditorTopView.getEditIBtn().setVisibility(editIBtnVisible ? VISIBLE : GONE);
            ibookerEditorTopView.getEditIBtn().setBackgroundResource(editIBtnDefaultRes);

            // 预览模式
            boolean previewIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorTopView_PreviewIBtn_Visible, true);
            previewIBtnDefaultRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorTopView_PreviewIBtn_Default_Res, previewIBtnDefaultRes);
            previewIBtnSelectedRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorTopView_PreviewIBtn_Selected_Res, previewIBtnSelectedRes);
            ibookerEditorTopView.getPreviewIBtn().setVisibility(previewIBtnVisible ? VISIBLE : GONE);
            ibookerEditorTopView.getPreviewIBtn().setBackgroundResource(previewIBtnDefaultRes);

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

            // 分享
            boolean shareIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorTopView_ShareIBtn_Visible, true);
            int shareIBtnRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorTopView_ShareIBtn_Res, R.drawable.draw_share);
            ibookerEditorTopView.getShareIBtn().setVisibility(shareIBtnVisible ? VISIBLE : GONE);
            ibookerEditorTopView.getShareIBtn().setBackgroundResource(shareIBtnRes);

            // 更多
            boolean elseIBtnVisible = ta.getBoolean(R.styleable.IbookerEditorView_IbookerEditorTopView_ElseIBtn_Visible, true);
            int elseIBtnRes = ta.getResourceId(R.styleable.IbookerEditorView_IbookerEditorTopView_ElseIBtn_Res, R.drawable.draw_else);
            ibookerEditorTopView.getElseIBtn().setVisibility(elseIBtnVisible ? VISIBLE : GONE);
            ibookerEditorTopView.getElseIBtn().setBackgroundResource(elseIBtnRes);

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

        ibookerEditorTopView.getEditIBtn().setBackgroundResource(editIBtnSelectedRes);
        ibookerEditorTopView.getPreviewIBtn().setBackgroundResource(previewIBtnDefaultRes);
    }

    // 设置ViewPager变化
    public void changeVpUpdateIbookerEditorTopView(int position) {
        if (ibookerEditorTopView != null)
            if (position == 0) {
                ibookerEditorTopView.getEditIBtn().setBackgroundResource(editIBtnSelectedRes);
                ibookerEditorTopView.getPreviewIBtn().setBackgroundResource(previewIBtnDefaultRes);

                // 设置动画
                if (ibookerEditorToolView != null) {
                    if (inAnim != null)
                        ibookerEditorToolView.startAnimation(inAnim);
                    ibookerEditorToolView.setVisibility(VISIBLE);
                }

                openInputSoft(true);
            } else if (position == 1) {
                ibookerEditorTopView.getEditIBtn().setBackgroundResource(editIBtnDefaultRes);
                ibookerEditorTopView.getPreviewIBtn().setBackgroundResource(previewIBtnSelectedRes);
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
        if (imm != null && imm.isActive() && ((Activity) getContext()).getCurrentFocus() != null) {
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
        } else if (tag.equals(IBTN_SHARE)) {// 分享
            ibookerEditorVpView.setCurrentItem(1);
            if (!hasPermission(needPermissions)) {
                requestPermission(12112, needPermissions);
            } else {
                generateBitmap();
            }
        }
    }

    // 工具栏按钮点击事件监听
    @Override
    public void onToolClick(Object tag) {
        ibookerEditorVpView.getEditView().getIbookerTitleEd().clearFocus();
        ibookerEditorVpView.getEditView().getIbookerEd().requestFocus();
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
    public IbookerEditorView setIETopViewVisibility(int visibility) {
        if (visibility == VISIBLE || visibility == GONE || visibility == INVISIBLE)
            ibookerEditorTopView.setVisibility(visibility);
        return this;
    }

    /**
     * 设置编辑控件背景颜色
     *
     * @param color 背景颜色
     */
    public IbookerEditorView setIEEditViewBackgroundColor(@ColorInt int color) {
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
    public IbookerEditorView setIEPreViewBackgroundColor(@ColorInt int color) {
        ibookerEditorVpView.getPreView().setBackgroundColor(color);
        ibookerEditorVpView.getPreView().getIbookerTitleTv().setBackgroundColor(color);
        ibookerEditorVpView.getPreView().getIbookerEditorWebView().setBackgroundColor(color);
        return this;
    }

    // 设置返回按钮backImg
    public IbookerEditorView setIETopViewBackImageResource(@DrawableRes int resId) {
        ibookerEditorTopView.setBackImageResource(resId);
        return this;
    }

    public IbookerEditorView setIETopViewBackImgVisibility(int visibility) {
        ibookerEditorTopView.setBackImgVisibility(visibility);
        return this;
    }

    // 设置撤销按钮undoIBtn
    public IbookerEditorView setIETopViewUndoImageResource(@DrawableRes int resId) {
        ibookerEditorTopView.setUndoImageResource(resId);
        return this;
    }

    public IbookerEditorView setIETopViewUndoIBtnVisibility(int visibility) {
        ibookerEditorTopView.setUndoIBtnVisibility(visibility);
        return this;
    }

    // 设置重做按钮
    public IbookerEditorView setIETopViewRedoImageResource(@DrawableRes int resId) {
        ibookerEditorTopView.setRedoImageResource(resId);
        return this;
    }

    public IbookerEditorView setIETopViewRedoIBtnVisibility(int visibility) {
        ibookerEditorTopView.setRedoIBtnVisibility(visibility);
        return this;
    }

    // 设置编辑按钮
    public IbookerEditorView setIETopViewEditImageResource(@DrawableRes int resId) {
        ibookerEditorTopView.setEditImageResource(resId);
        return this;
    }

    public IbookerEditorView setIETopViewEditIBtnVisibility(int visibility) {
        ibookerEditorTopView.setEditIBtnVisibility(visibility);
        return this;
    }

    // 设置预览按钮
    public IbookerEditorView setIETopViewPreviewImageResource(@DrawableRes int resId) {
        ibookerEditorTopView.setPreviewImageResource(resId);
        return this;
    }

    public IbookerEditorView setIETopViewPreviewIBtnVisibility(int visibility) {
        ibookerEditorTopView.setPreviewIBtnVisibility(visibility);
        return this;
    }

    // 设置帮助按钮
    public IbookerEditorView setIETopViewHelpImageResource(@DrawableRes int resId) {
        ibookerEditorTopView.setHelpImageResource(resId);
        return this;
    }

    public IbookerEditorView setIETopViewHelpIBtnVisibility(int visibility) {
        ibookerEditorTopView.setHelpIBtnVisibility(visibility);
        return this;
    }

    // 设置关于按钮
    public IbookerEditorView setIETopViewAboutImageResource(@DrawableRes int resId) {
        ibookerEditorTopView.setAboutImageResource(resId);
        return this;
    }

    public IbookerEditorView setIETopViewAboutImgVisibility(int visibility) {
        ibookerEditorTopView.setAboutImgVisibility(visibility);
        return this;
    }

    // 设置分享按钮
    public IbookerEditorView setIETopViewShareIBtnResource(@DrawableRes int resId) {
        ibookerEditorTopView.setShareIBtnResource(resId);
        return this;
    }

    public IbookerEditorView setIETopViewShareIBtnVisibility(int visibility) {
        ibookerEditorTopView.setShareIBtnVisibility(visibility);
        return this;
    }

    // 设置更多按钮
    public IbookerEditorView setIETopViewElseIBtnResource(@DrawableRes int resId) {
        ibookerEditorTopView.setElseIBtnResource(resId);
        return this;
    }

    public IbookerEditorView setIETopViewElseIBtnVisibility(int visibility) {
        ibookerEditorTopView.setElseIBtnVisibility(visibility);
        return this;
    }

    /**
     * 设置输入框字体大小
     *
     * @param size 字体大小
     */
    public IbookerEditorView setIEEditViewIbookerEdTextSize(float size) {
        ibookerEditorVpView.getEditView().setIbookerEdTextSize(size);
        return this;
    }

    /**
     * 设置输入框字体颜色
     *
     * @param color 字体颜色
     */
    public IbookerEditorView setIEEditViewIbookerEdTextColor(@ColorInt int color) {
        ibookerEditorVpView.getEditView().setIbookerEdTextColor(color);
        return this;
    }

    /**
     * 设置输入框hint内容
     *
     * @param hint hint内容
     */
    public IbookerEditorView setIEEditViewIbookerEdHint(CharSequence hint) {
        ibookerEditorVpView.getEditView().setIbookerEdHint(hint);
        return this;
    }

    /**
     * 设置输入框text内容
     *
     * @param text text内容
     */
    public IbookerEditorView setIEEditViewIbookerEdText(CharSequence text) {
        ibookerEditorVpView.getEditView().getIbookerEd().setText(text);
        return this;
    }

    /**
     * 设置输入框hint颜色
     *
     * @param color hint颜色
     */
    public IbookerEditorView setIEEditViewIbookerEdHintTextColor(@ColorInt int color) {
        ibookerEditorVpView.getEditView().setIbookerEdHintTextColor(color);
        return this;
    }

    /**
     * 设置输入框背景颜色
     *
     * @param color 背景颜色
     */
    public IbookerEditorView setIEEditViewIbookerEdBackgroundColor(@ColorInt int color) {
        ibookerEditorVpView.getEditView().setIbookerEdBackgroundColor(color);
        return this;
    }

    /**
     * 设置标题显示或者隐藏
     *
     * @param visibility View.GONE,View.VISIBLE,View.INVISIBLE
     */
    public IbookerEditorView setIEEditViewIbookerTitleEdVisibility(int visibility) {
        ibookerEditorVpView.getEditView().setIbookerTitleEdVisibility(visibility);
        return this;
    }

    /**
     * 设置标题输入框字体大小
     *
     * @param size 字体大小
     */
    public IbookerEditorView setIEEditViewIbookerTitleEdTextSize(float size) {
        ibookerEditorVpView.getEditView().setIbookerTitleEdTextSize(size);
        return this;
    }

    /**
     * 设置标题输入框字体颜色
     *
     * @param color 字体颜色
     */
    public IbookerEditorView setIEEditViewIbookerTitleEdTextColor(@ColorInt int color) {
        ibookerEditorVpView.getEditView().setIbookerTitleEdTextColor(color);
        return this;
    }

    /**
     * 设置标题输入框hint内容
     *
     * @param hint hint内容
     */
    public IbookerEditorView setIEEditViewIbookerTitleEdHint(CharSequence hint) {
        ibookerEditorVpView.getEditView().setIbookerTitleEdHint(hint);
        return this;
    }

    /**
     * 设置标题输入框text内容
     *
     * @param text text内容
     */
    public IbookerEditorView setIEEditViewIbookerTitleEdText(CharSequence text) {
        ibookerEditorVpView.getEditView().getIbookerTitleEd().setText(text);
        return this;
    }

    /**
     * 设置标题输入框hint颜色
     *
     * @param color hint颜色
     */
    public IbookerEditorView setIEEditViewIbookerTitleEdHintTextColor(@ColorInt int color) {
        ibookerEditorVpView.getEditView().setIbookerTitleEdHintTextColor(color);
        return this;
    }

    /**
     * 设置线条的背景颜色
     *
     * @param color 颜色
     */
    public IbookerEditorView setIEEditViewLineViewBackgroundColor(@ColorInt int color) {
        ibookerEditorVpView.getEditView().setLineViewBackgroundColor(color);
        return this;
    }

    /**
     * 设置线条显示或者隐藏
     *
     * @param visibility View.GONE,View.VISIBLE,View.INVISIBLE
     */
    public IbookerEditorView setIEEditViewLineViewVisibility(int visibility) {
        ibookerEditorVpView.getEditView().setLineViewVisibility(visibility);
        return this;
    }

    // 粗体
    public IbookerEditorView setIEToolViewBoldIBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setBoldIBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewBoldIBtnVisibility(int visibility) {
        ibookerEditorToolView.setBoldIBtnVisibility(visibility);
        return this;
    }

    // 斜体
    public IbookerEditorView setIEToolViewItalicIBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setItalicIBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewItalicIBtnVisibility(int visibility) {
        ibookerEditorToolView.setItalicIBtnVisibility(visibility);
        return this;
    }

    // 删除线
    public IbookerEditorView setIEToolViewStrikeoutIBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setStrikeoutIBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewStrikeoutIBtnVisibility(int visibility) {
        ibookerEditorToolView.setStrikeoutIBtnVisibility(visibility);
        return this;
    }

    // 下划线
    public IbookerEditorView setIEToolViewUnderlineIBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setUnderlineIBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewUnderlineIBtnVisibility(int visibility) {
        ibookerEditorToolView.setUnderlineIBtnVisibility(visibility);
        return this;
    }

    // 单词首字母大写
    public IbookerEditorView setIEToolViewCapitalsIBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setCapitalsIBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewCapitalsIBtnVisibility(int visibility) {
        ibookerEditorToolView.setCapitalsIBtnVisibility(visibility);
        return this;
    }

    // 单词转大写
    public IbookerEditorView setIEToolViewUppercaseIBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setUppercaseIBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewUppercaseIBtnVisibility(int visibility) {
        ibookerEditorToolView.setUppercaseIBtnVisibility(visibility);
        return this;
    }

    // 单词转小写
    public IbookerEditorView setIEToolViewLowercaseIBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setLowercaseIBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewLowercaseIBtnVisibility(int visibility) {
        ibookerEditorToolView.setLowercaseIBtnVisibility(visibility);
        return this;
    }

    // 一级标题
    public IbookerEditorView setIEToolViewH1IBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setH1IBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewH1IBtnVisibility(int visibility) {
        ibookerEditorToolView.setH1IBtnVisibility(visibility);
        return this;
    }

    // 二级标题
    public IbookerEditorView setIEToolViewH2IBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setH2IBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewH2IBtnVisibility(int visibility) {
        ibookerEditorToolView.setH2IBtnVisibility(visibility);
        return this;
    }

    // 三级标题
    public IbookerEditorView setIEToolViewH3IBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setH3IBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewH3IBtnVisibility(int visibility) {
        ibookerEditorToolView.setH3IBtnVisibility(visibility);
        return this;
    }

    // 四级标题
    public IbookerEditorView setIEToolViewH4IBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setH4IBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewH4IBtnVisibility(int visibility) {
        ibookerEditorToolView.setH4IBtnVisibility(visibility);
        return this;
    }

    // 五级标题
    public IbookerEditorView setIEToolViewH5IBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setH5IBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewH5IBtnVisibility(int visibility) {
        ibookerEditorToolView.setH5IBtnVisibility(visibility);
        return this;
    }

    // 六级标题
    public IbookerEditorView setIEToolViewH6IBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setH6IBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewH6IBtnVisibility(int visibility) {
        ibookerEditorToolView.setH6IBtnVisibility(visibility);
        return this;
    }

    // 链接
    public IbookerEditorView setIEToolViewLinkIBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setLinkIBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewLinkIBtnVisibility(int visibility) {
        ibookerEditorToolView.setLinkIBtnVisibility(visibility);
        return this;
    }

    // 引用
    public IbookerEditorView setIEToolViewQuoteIBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setQuoteIBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewQuoteIBtnVisibility(int visibility) {
        ibookerEditorToolView.setQuoteIBtnVisibility(visibility);
        return this;
    }

    // 代码
    public IbookerEditorView setIEToolViewCodeIBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setCodeIBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewCodeIBtnVisibility(int visibility) {
        ibookerEditorToolView.setCodeIBtnVisibility(visibility);
        return this;
    }

    // 图片
    public IbookerEditorView setIEToolViewImguIBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setImguIBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewImguIBtnVisibility(int visibility) {
        ibookerEditorToolView.setImguIBtnVisibility(visibility);
        return this;
    }

    // 数字列表
    public IbookerEditorView setIEToolViewOlIBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setOlIBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewOlIBtnVisibility(int visibility) {
        ibookerEditorToolView.setOlIBtnVisibility(visibility);
        return this;
    }

    // 普通列表
    public IbookerEditorView setIEToolViewUlIBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setUlIBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewUlIBtnVisibility(int visibility) {
        ibookerEditorToolView.setUlIBtnVisibility(visibility);
        return this;
    }

    // 列表未选中
    public IbookerEditorView setIEToolViewUnselectedIBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setUnselectedIBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewUnselectedIBtnVisibility(int visibility) {
        ibookerEditorToolView.setUnselectedIBtnVisibility(visibility);
        return this;
    }

    // 列表选中
    public IbookerEditorView setIEToolViewSelectedIBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setSelectedIBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewSelectedIBtnVisibility(int visibility) {
        ibookerEditorToolView.setSelectedIBtnVisibility(visibility);
        return this;
    }

    // 表格
    public IbookerEditorView setIEToolViewTableIBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setTableIBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewTableIBtnVisibility(int visibility) {
        ibookerEditorToolView.setTableIBtnVisibility(visibility);
        return this;
    }

    // HTML
    public IbookerEditorView setIEToolViewHtmlIBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setHtmlIBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewHtmlIBtnVisibility(int visibility) {
        ibookerEditorToolView.setHtmlIBtnVisibility(visibility);
        return this;
    }

    // 分割线
    public IbookerEditorView setIEToolViewHrIBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setHrIBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewHrIBtnVisibility(int visibility) {
        ibookerEditorToolView.setHrIBtnVisibility(visibility);
        return this;
    }

    // 表情
    public IbookerEditorView setIEToolViewEmojiIBtnImageResource(@DrawableRes int resId) {
        ibookerEditorToolView.setEmojiIBtnImageResource(resId);
        return this;
    }

    public IbookerEditorView setIEToolViewEmojiIBtnVisibility(int visibility) {
        ibookerEditorToolView.setEmojiIBtnVisibility(visibility);
        return this;
    }

    /**
     * 设置WebView控件背景颜色
     *
     * @param color 背景颜色
     */
    public IbookerEditorView setIEPreViewIbookerEditorWebViewBackgroundColor(
            @ColorInt int color) {
        ibookerEditorVpView.getPreView().setIbookerEditorWebViewBackgroundColor(color);
        return this;
    }

    /**
     * 设置标题显示 或者隐藏
     *
     * @param visibility View.GONE,View.VISIBLE,View.INVISIBLE
     */
    public IbookerEditorView setIEPreViewIbookerTitleTvVisibility(int visibility) {
        ibookerEditorVpView.getPreView().setIbookerTitleTvVisibility(visibility);
        return this;
    }

    /**
     * 设置标题字体大小
     *
     * @param size 字体大小
     */
    public IbookerEditorView setIEPreViewIbookerTitleTvTextSize(float size) {
        ibookerEditorVpView.getPreView().setIbookerTitleTvTextSize(size);
        return this;
    }

    /**
     * 设置标题字体颜色
     *
     * @param color 字体颜色
     */
    public IbookerEditorView setIEPreViewIbookerTitleTvTextColor(@ColorInt int color) {
        ibookerEditorVpView.getPreView().setIbookerTitleTvTextColor(color);
        return this;
    }

    /**
     * 设置标题hint内容
     *
     * @param hint hint内容
     */
    public IbookerEditorView setIEPreViewIbookerTitleTvHint(CharSequence hint) {
        ibookerEditorVpView.getPreView().setIbookerTitleTvHint(hint);
        return this;
    }

    /**
     * 设置标题hint颜色
     *
     * @param color hint颜色
     */
    public IbookerEditorView setIEPreViewIbookerTitleTvHintTextColor(@ColorInt int color) {
        ibookerEditorVpView.getPreView().setIbookerTitleTvHintTextColor(color);
        return this;
    }

    /**
     * 设置线条的背景颜色
     *
     * @param color 颜色
     */
    public IbookerEditorView setIEPreViewLineViewBackgroundColor(@ColorInt int color) {
        ibookerEditorVpView.getPreView().setLineViewBackgroundColor(color);
        return this;
    }

    /**
     * 设置线条显示或者隐藏
     *
     * @param visibility View.GONE,View.VISIBLE,View.INVISIBLE
     */
    public IbookerEditorView setIEPreViewLineViewVisibility(int visibility) {
        ibookerEditorVpView.getPreView().setLineViewVisibility(visibility);
        return this;
    }

    /**
     * 是否完成本地文件加载
     */
    public boolean isLoadFinished() {
        return ibookerEditorVpView.getPreView().getIbookerEditorWebView().isLoadFinished();
    }

    /**
     * 执行预览
     *
     * @param ibookerEditorText 待预览内容 非HTML
     */
    public void ibookerCompile(String ibookerEditorText) {
        ibookerEditorVpView.getPreView().getIbookerEditorWebView().ibookerCompile(ibookerEditorText);
    }

    /**
     * 执行Html预览
     *
     * @param ibookerEditorHtml 待预览内容 HTML
     */
    public void ibookerHtmlCompile(String ibookerEditorHtml) {
        ibookerEditorVpView.getPreView().getIbookerEditorWebView().ibookerHtmlCompile(ibookerEditorHtml);
    }

    /**
     * 获取整个WebView截图
     */
    public Bitmap getWebViewBitmap() {
        return ibookerEditorVpView.getPreView().getIbookerEditorWebView().getWebViewBitmap();
    }

    /**
     * 编辑框顶部按钮点击监听
     */
    public void setOnTopClickListener(IbookerEditorTopView.OnTopClickListener
                                              onTopClickListener) {
        ibookerEditorTopView.setOnTopClickListener(onTopClickListener);
    }

    /**
     * 编辑区输入标题监听
     */
    public void setOnIbookerTitleEdTextChangedListener
    (IbookerEditorEditView.OnIbookerTitleEdTextChangedListener
             onIbookerTitleEdTextChangedListener) {
        ibookerEditorVpView.getEditView().setOnIbookerTitleEdTextChangedListener(onIbookerTitleEdTextChangedListener);
    }

    /**
     * 编辑区输入内容监听
     */
    public void setOnIbookerEdTextChangedListener
    (IbookerEditorEditView.OnIbookerEdTextChangedListener onIbookerEdTextChangedListener) {
        ibookerEditorVpView.getEditView().setOnIbookerEdTextChangedListener(onIbookerEdTextChangedListener);
    }

    /**
     * 底部工具栏监听
     */
    public void setOnToolClickListener(IbookerEditorToolView.OnToolClickListener
                                               onToolClickListener) {
        ibookerEditorToolView.setOnToolClickListener(onToolClickListener);
    }

    /**
     * 图片预览接口
     */
    public void setIbookerEditorImgPreviewListener
    (IbookerEditorWebView.IbookerEditorImgPreviewListener ibookerEditorImgPreviewListener) {
        ibookerEditorVpView.getPreView().getIbookerEditorWebView().setIbookerEditorImgPreviewListener(ibookerEditorImgPreviewListener);
    }

    /**
     * Url加载状态接口
     */
    public void setIbookerEditorWebViewUrlLoadingListener
    (IbookerEditorWebView.IbookerEditorWebViewUrlLoadingListener
             ibookerEditorWebViewUrlLoadingListener) {
        ibookerEditorVpView.getPreView().getIbookerEditorWebView().setIbookerEditorWebViewUrlLoadingListener(ibookerEditorWebViewUrlLoadingListener);
    }

    /**
     * 生成File文件
     */
    public File createSDDirs(String path) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            File dir = new File(path);
            boolean bool = true;
            if (!dir.exists()) {
                bool = dir.mkdirs();
            }
            return !bool ? null : dir;
        } else {
            return null;
        }
    }

    /**
     * 生成图片
     */
    public void generateBitmap() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ibookerEditorVpView.getPreView().getIbookerEditorWebView().isLoadFinished()) {
                    Toast.makeText(IbookerEditorView.this.getContext(), "图片生成中...", Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = ibookerEditorVpView.getPreView().getIbookerEditorWebView().getWebViewBitmap();
                    if (bitmap != null) {
                        // 分享图片
                        try {
                            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ibookerEditor" + File.separator + "shares" + File.separator;
                            String fileName = System.currentTimeMillis() + ".jpg";
                            File dir = new File(filePath);
                            boolean bool = dir.exists();
                            if (!bool)
                                createSDDirs(filePath);
                            File file = new File(filePath, fileName);

                            FileOutputStream fOut = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fOut);
                            fOut.flush();
                            fOut.close();

                            // 进入图片预览
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(file), "image/*");
                            IbookerEditorView.this.getContext().startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            bitmap.recycle();
                            System.gc();
                        }
                    } else {
                        Toast.makeText(IbookerEditorView.this.getContext(), "生成图片失败！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    generateBitmap();
                }
            }
        }, 500);
    }

    /**
     * 权限检查方法，false代表没有该权限，ture代表有该权限
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this.getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 权限请求方法
     */
    public void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions((Activity) this.getContext(), permissions, code);
    }

}
