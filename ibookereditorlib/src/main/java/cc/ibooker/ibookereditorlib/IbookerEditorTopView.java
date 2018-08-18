package cc.ibooker.ibookereditorlib;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 书客编辑器 - 顶部布局
 * Created by 邹峰立 on 2018/2/11.
 */
public class IbookerEditorTopView extends LinearLayout implements View.OnClickListener {
    private ImageView backImg, aboutImg;
    private LinearLayout rightLayout;
    private ImageButton undoIBtn, redoIBtn, editIBtn, previewIBtn, helpIBtn, shareIBtn;

    // 构造方法
    public IbookerEditorTopView(Context context) {
        this(context, null);
    }

    public IbookerEditorTopView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IbookerEditorTopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setBackgroundColor(Color.parseColor("#EFEFEF"));
        setGravity(Gravity.CENTER_VERTICAL);
        setMinimumHeight(IbookerEditorUtil.dpToPx(context, 48F));

        setPadding(IbookerEditorUtil.dpToPx(context, 5F), IbookerEditorUtil.dpToPx(context, 5F), IbookerEditorUtil.dpToPx(context, 5F), IbookerEditorUtil.dpToPx(context, 5F));
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        init(context);
    }

    // 初始化
    private void init(Context context) {
        backImg = new ImageView(context);
        LayoutParams backParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, IbookerEditorUtil.dpToPx(context, 22F));
        backParams.setMargins(IbookerEditorUtil.dpToPx(context, 5F), 0, IbookerEditorUtil.dpToPx(context, 5F), 0);
        backImg.setLayoutParams(backParams);
        backImg.setImageResource(R.drawable.icon_back_black);
        backImg.setAdjustViewBounds(true);
        backImg.setContentDescription(getResources().getString(R.string.back));
        backImg.setTag(IbookerEditorEnum.TOOLVIEW_TAG.IMG_BACK);
        backImg.setOnClickListener(this);
        addView(backImg);

        rightLayout = new LinearLayout(context);
        rightLayout.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        rightLayout.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        addView(rightLayout);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(IbookerEditorUtil.dpToPx(context, 5F), 0, IbookerEditorUtil.dpToPx(context, 5F), 0);

        undoIBtn = new ImageButton(context);
        undoIBtn.setLayoutParams(layoutParams);
        undoIBtn.setBackgroundResource(R.drawable.draw_undo);
        undoIBtn.setContentDescription(getResources().getString(R.string.undo));
        undoIBtn.setPadding(IbookerEditorUtil.dpToPx(context, 13F), IbookerEditorUtil.dpToPx(context, 13F), IbookerEditorUtil.dpToPx(context, 13F), IbookerEditorUtil.dpToPx(context, 13F));
        undoIBtn.setTag(IbookerEditorEnum.TOOLVIEW_TAG.IBTN_UNDO);
        undoIBtn.setOnClickListener(this);
        rightLayout.addView(undoIBtn);

        redoIBtn = new ImageButton(context);
        redoIBtn.setLayoutParams(layoutParams);
        redoIBtn.setBackgroundResource(R.drawable.draw_redo);
        redoIBtn.setContentDescription(getResources().getString(R.string.redo));
        redoIBtn.setPadding(IbookerEditorUtil.dpToPx(context, 13F), IbookerEditorUtil.dpToPx(context, 13F), IbookerEditorUtil.dpToPx(context, 13F), IbookerEditorUtil.dpToPx(context, 13F));
        redoIBtn.setTag(IbookerEditorEnum.TOOLVIEW_TAG.IBTN_REDO);
        redoIBtn.setOnClickListener(this);
        rightLayout.addView(redoIBtn);

        editIBtn = new ImageButton(context);
        editIBtn.setLayoutParams(layoutParams);
        editIBtn.setBackgroundResource(R.drawable.draw_edit);
        editIBtn.setContentDescription(getResources().getString(R.string.edit));
        editIBtn.setPadding(IbookerEditorUtil.dpToPx(context, 13F), IbookerEditorUtil.dpToPx(context, 13F), IbookerEditorUtil.dpToPx(context, 13F), IbookerEditorUtil.dpToPx(context, 13F));
        editIBtn.setTag(IbookerEditorEnum.TOOLVIEW_TAG.IBTN_EDIT);
        editIBtn.setOnClickListener(this);
        rightLayout.addView(editIBtn);

        previewIBtn = new ImageButton(context);
        previewIBtn.setLayoutParams(layoutParams);
        previewIBtn.setBackgroundResource(R.drawable.draw_preview);
        previewIBtn.setContentDescription(getResources().getString(R.string.preview));
        previewIBtn.setPadding(IbookerEditorUtil.dpToPx(context, 13F), IbookerEditorUtil.dpToPx(context, 13F), IbookerEditorUtil.dpToPx(context, 13F), IbookerEditorUtil.dpToPx(context, 13F));
        previewIBtn.setTag(IbookerEditorEnum.TOOLVIEW_TAG.IBTN_PREVIEW);
        previewIBtn.setOnClickListener(this);
        rightLayout.addView(previewIBtn);

        shareIBtn = new ImageButton(context);
        shareIBtn.setLayoutParams(layoutParams);
        shareIBtn.setBackgroundResource(R.drawable.draw_share);
        shareIBtn.setContentDescription(getResources().getString(R.string.share));
        shareIBtn.setPadding(IbookerEditorUtil.dpToPx(context, 13F), IbookerEditorUtil.dpToPx(context, 13F), IbookerEditorUtil.dpToPx(context, 13F), IbookerEditorUtil.dpToPx(context, 13F));
        shareIBtn.setTag(IbookerEditorEnum.TOOLVIEW_TAG.IBTN_SHARE);
        shareIBtn.setOnClickListener(this);
        rightLayout.addView(shareIBtn);

        helpIBtn = new ImageButton(context);
        helpIBtn.setLayoutParams(layoutParams);
        helpIBtn.setBackgroundResource(R.drawable.draw_help);
        helpIBtn.setContentDescription(getResources().getString(R.string.help));
        helpIBtn.setPadding(IbookerEditorUtil.dpToPx(context, 13F), IbookerEditorUtil.dpToPx(context, 13F), IbookerEditorUtil.dpToPx(context, 13F), IbookerEditorUtil.dpToPx(context, 13F));
        helpIBtn.setTag(IbookerEditorEnum.TOOLVIEW_TAG.IBTN_HELP);
        helpIBtn.setOnClickListener(this);
        rightLayout.addView(helpIBtn);

        LayoutParams params = new LayoutParams(IbookerEditorUtil.dpToPx(context, 29F), IbookerEditorUtil.dpToPx(context, 29F));
        params.setMargins(IbookerEditorUtil.dpToPx(context, 5F), 0, IbookerEditorUtil.dpToPx(context, 5F), 0);
        aboutImg = new ImageView(context);
        aboutImg.setLayoutParams(params);
        aboutImg.setImageResource(R.drawable.ibooker_editor_logo);
        aboutImg.setContentDescription(getResources().getString(R.string.about));
        aboutImg.setPadding(IbookerEditorUtil.dpToPx(context, 5F), IbookerEditorUtil.dpToPx(context, 5F), IbookerEditorUtil.dpToPx(context, 5F), IbookerEditorUtil.dpToPx(context, 5F));
        aboutImg.setTag(IbookerEditorEnum.TOOLVIEW_TAG.IBTN_ABOUT);
        aboutImg.setOnClickListener(this);
        rightLayout.addView(aboutImg);
    }

    // 点击事件监听
    @Override
    public void onClick(View v) {
        if (onTopClickListener != null)
            onTopClickListener.onTopClick(v.getTag());
    }

    // getter
    public ImageView getBackImg() {
        return backImg;
    }

    public ImageView getAboutImg() {
        return aboutImg;
    }

    public LinearLayout getRightLayout() {
        return rightLayout;
    }

    public ImageButton getUndoIBtn() {
        return undoIBtn;
    }

    public ImageButton getRedoIBtn() {
        return redoIBtn;
    }

    public ImageButton getEditIBtn() {
        return editIBtn;
    }

    public ImageButton getPreviewIBtn() {
        return previewIBtn;
    }

    public ImageButton getHelpIBtn() {
        return helpIBtn;
    }

    public ImageButton getShareIBtn() {
        return shareIBtn;
    }

    // 设置返回按钮backImg
    public IbookerEditorTopView setBackImageResource(@DrawableRes int resId) {
        backImg.setImageResource(resId);
        return this;
    }

    public IbookerEditorTopView setBackImgVisibility(int visibility) {
        backImg.setVisibility(visibility);
        return this;
    }

    // 设置撤销按钮undoIBtn
    public IbookerEditorTopView setUndoImageResource(@DrawableRes int resId) {
        undoIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorTopView setUndoIBtnVisibility(int visibility) {
        undoIBtn.setVisibility(visibility);
        return this;
    }

    // 设置重做按钮
    public IbookerEditorTopView setRedoImageResource(@DrawableRes int resId) {
        redoIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorTopView setRedoIBtnVisibility(int visibility) {
        redoIBtn.setVisibility(visibility);
        return this;
    }

    // 设置编辑按钮
    public IbookerEditorTopView setEditImageResource(@DrawableRes int resId) {
        editIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorTopView setEditIBtnVisibility(int visibility) {
        editIBtn.setVisibility(visibility);
        return this;
    }

    // 设置预览按钮
    public IbookerEditorTopView setPreviewImageResource(@DrawableRes int resId) {
        previewIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorTopView setPreviewIBtnVisibility(int visibility) {
        previewIBtn.setVisibility(visibility);
        return this;
    }

    // 设置帮助按钮
    public IbookerEditorTopView setHelpImageResource(@DrawableRes int resId) {
        helpIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorTopView setHelpIBtnVisibility(int visibility) {
        helpIBtn.setVisibility(visibility);
        return this;
    }

    // 设置关于按钮
    public IbookerEditorTopView setAboutImageResource(@DrawableRes int resId) {
        aboutImg.setImageResource(resId);
        return this;
    }

    public IbookerEditorTopView setAboutImgVisibility(int visibility) {
        aboutImg.setVisibility(visibility);
        return this;
    }

    // 设置关于分享按钮
    public IbookerEditorTopView setShareImageResource(@DrawableRes int resId) {
        shareIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorTopView setShareImgVisibility(int visibility) {
        shareIBtn.setVisibility(visibility);
        return this;
    }

    /**
     * 点击事件监听
     */
    public interface OnTopClickListener {
        void onTopClick(Object tag);
    }

    private OnTopClickListener onTopClickListener;

    public void setOnTopClickListener(OnTopClickListener onTopClickListener) {
        this.onTopClickListener = onTopClickListener;
    }
}
