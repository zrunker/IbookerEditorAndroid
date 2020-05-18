package cc.ibooker.ibookereditorlib;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * 书客编辑器工具栏
 * Created by 邹峰立 on 2018/1/17.
 */
public class IbookerEditorToolView extends HorizontalScrollView {
    private LinearLayout toolLayout;
    private ImageButton boldIBtn, italicIBtn, strikeoutIBtn, underlineIBtn, capitalsIBtn, uppercaseIBtn,
            lowercaseIBtn, h1IBtn, h2IBtn, h3IBtn, h4IBtn, h5IBtn, h6IBtn, linkIBtn, quoteIBtn,
            codeIBtn, imguIBtn, olIBtn, ulIBtn, unselectedIBtn, selectedIBtn, tableIBtn, htmlIBtn, hrIBtn,
            emojiIBtn;
    private int dp6, dp13;

    // getter
    public LinearLayout getToolLayout() {
        return toolLayout;
    }

    public ImageButton getBoldIBtn() {
        return boldIBtn;
    }

    public ImageButton getItalicIBtn() {
        return italicIBtn;
    }

    public ImageButton getStrikeoutIBtn() {
        return strikeoutIBtn;
    }

    public ImageButton getUnderlineIBtn() {
        return underlineIBtn;
    }

    public ImageButton getCapitalsIBtn() {
        return capitalsIBtn;
    }

    public ImageButton getUppercaseIBtn() {
        return uppercaseIBtn;
    }

    public ImageButton getLowercaseIBtn() {
        return lowercaseIBtn;
    }

    public ImageButton getH1IBtn() {
        return h1IBtn;
    }

    public ImageButton getH2IBtn() {
        return h2IBtn;
    }

    public ImageButton getH3IBtn() {
        return h3IBtn;
    }

    public ImageButton getH4IBtn() {
        return h4IBtn;
    }

    public ImageButton getH5IBtn() {
        return h5IBtn;
    }

    public ImageButton getH6IBtn() {
        return h6IBtn;
    }

    public ImageButton getLinkIBtn() {
        return linkIBtn;
    }

    public ImageButton getQuoteIBtn() {
        return quoteIBtn;
    }

    public ImageButton getCodeIBtn() {
        return codeIBtn;
    }

    public ImageButton getImguIBtn() {
        return imguIBtn;
    }

    public ImageButton getOlIBtn() {
        return olIBtn;
    }

    public ImageButton getUlIBtn() {
        return ulIBtn;
    }

    public ImageButton getUnselectedIBtn() {
        return unselectedIBtn;
    }

    public ImageButton getSelectedIBtn() {
        return selectedIBtn;
    }

    public ImageButton getTableIBtn() {
        return tableIBtn;
    }

    public ImageButton getHtmlIBtn() {
        return htmlIBtn;
    }

    public ImageButton getHrIBtn() {
        return hrIBtn;
    }

    public ImageButton getEmojiIBtn() {
        return emojiIBtn;
    }

    public OnToolClickListener getOnToolClickListener() {
        return onToolClickListener;
    }

    // 构造方法
    public IbookerEditorToolView(Context context) {
        this(context, null);
    }

    public IbookerEditorToolView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IbookerEditorToolView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setBackgroundResource(R.drawable.bg_ibooker_editor_tool);
        setVerticalScrollBarEnabled(false);
        setClipToPadding(true);

        dp6 = IbookerEditorUtil.dpToPx(context, 6F);
        dp13 = IbookerEditorUtil.dpToPx(context, 13F);

        init(context);
    }

    // 初始化
    private void init(Context context) {
        toolLayout = new LinearLayout(context);
        toolLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        toolLayout.setMinimumHeight(IbookerEditorUtil.dpToPx(context, 46F));
        toolLayout.setGravity(Gravity.CENTER_VERTICAL);
        toolLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(toolLayout);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(dp6, dp6, dp6, dp6);
        // 粗体
        boldIBtn = new ImageButton(context);
        setImageBtn(boldIBtn, layoutParams, R.drawable.draw_bold, getResources().getString(R.string.bold), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_BOLD);
        toolLayout.addView(boldIBtn);

        // 斜体
        italicIBtn = new ImageButton(context);
        setImageBtn(italicIBtn, layoutParams, R.drawable.draw_italic, getResources().getString(R.string.italic), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_ITALIC);
        toolLayout.addView(italicIBtn);

        // 删除线
        strikeoutIBtn = new ImageButton(context);
        setImageBtn(strikeoutIBtn, layoutParams, R.drawable.draw_strikeout, getResources().getString(R.string.strikeout), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_STRIKEOUT);
        toolLayout.addView(strikeoutIBtn);

        // 下划线
        underlineIBtn = new ImageButton(context);
        setImageBtn(underlineIBtn, layoutParams, R.drawable.draw_underline, getResources().getString(R.string.underline), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_UNDERLINE);
        toolLayout.addView(underlineIBtn);

        // 单词首字母大写
        capitalsIBtn = new ImageButton(context);
        setImageBtn(capitalsIBtn, layoutParams, R.drawable.draw_capitals, getResources().getString(R.string.capitals), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_CAPITALS);
        toolLayout.addView(capitalsIBtn);

        // 单词转大写
        uppercaseIBtn = new ImageButton(context);
        setImageBtn(uppercaseIBtn, layoutParams, R.drawable.draw_uppercase, getResources().getString(R.string.uppercase), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_UPPERCASE);
        toolLayout.addView(uppercaseIBtn);

        // 单词转小写
        lowercaseIBtn = new ImageButton(context);
        setImageBtn(lowercaseIBtn, layoutParams, R.drawable.draw_lowercase, getResources().getString(R.string.lowercase), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_LOWERCASE);
        toolLayout.addView(lowercaseIBtn);

        // 一级标题
        h1IBtn = new ImageButton(context);
        setImageBtn(h1IBtn, layoutParams, R.drawable.draw_h1, getResources().getString(R.string.h1), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_H1);
        toolLayout.addView(h1IBtn);

        // 二级标题
        h2IBtn = new ImageButton(context);
        setImageBtn(h2IBtn, layoutParams, R.drawable.draw_h2, getResources().getString(R.string.h2), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_H2);
        toolLayout.addView(h2IBtn);

        // 三级标题
        h3IBtn = new ImageButton(context);
        setImageBtn(h3IBtn, layoutParams, R.drawable.draw_h3, getResources().getString(R.string.h3), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_H3);
        toolLayout.addView(h3IBtn);

        // 四级标题
        h4IBtn = new ImageButton(context);
        setImageBtn(h4IBtn, layoutParams, R.drawable.draw_h4, getResources().getString(R.string.h4), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_H4);
        toolLayout.addView(h4IBtn);

        // 五级标题
        h5IBtn = new ImageButton(context);
        setImageBtn(h5IBtn, layoutParams, R.drawable.draw_h5, getResources().getString(R.string.h5), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_H5);
        toolLayout.addView(h5IBtn);

        // 六级标题
        h6IBtn = new ImageButton(context);
        setImageBtn(h6IBtn, layoutParams, R.drawable.draw_h6, getResources().getString(R.string.h6), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_H6);
        toolLayout.addView(h6IBtn);

        // 链接
        linkIBtn = new ImageButton(context);
        setImageBtn(linkIBtn, layoutParams, R.drawable.draw_link, getResources().getString(R.string.link), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_LINK);
        toolLayout.addView(linkIBtn);

        // 引用
        quoteIBtn = new ImageButton(context);
        setImageBtn(quoteIBtn, layoutParams, R.drawable.draw_quote, getResources().getString(R.string.quote), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_QUOTE);
        toolLayout.addView(quoteIBtn);

        // 代码
        codeIBtn = new ImageButton(context);
        setImageBtn(codeIBtn, layoutParams, R.drawable.draw_code, getResources().getString(R.string.code), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_CODE);
        toolLayout.addView(codeIBtn);

        // 图片
        imguIBtn = new ImageButton(context);
        setImageBtn(imguIBtn, layoutParams, R.drawable.draw_img_u, getResources().getString(R.string.img_u), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_IMG_U);
        toolLayout.addView(imguIBtn);

        // 数字列表
        olIBtn = new ImageButton(context);
        setImageBtn(olIBtn, layoutParams, R.drawable.draw_ol, getResources().getString(R.string.ol), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_OL);
        toolLayout.addView(olIBtn);

        // 普通列表
        ulIBtn = new ImageButton(context);
        setImageBtn(ulIBtn, layoutParams, R.drawable.draw_ul, getResources().getString(R.string.ul), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_UL);
        toolLayout.addView(ulIBtn);

        // 列表未选中
        unselectedIBtn = new ImageButton(context);
        setImageBtn(unselectedIBtn, layoutParams, R.drawable.draw_unselected, getResources().getString(R.string.unselected), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_UNSELECTED);
        toolLayout.addView(unselectedIBtn);

        // 列表选中
        selectedIBtn = new ImageButton(context);
        setImageBtn(selectedIBtn, layoutParams, R.drawable.draw_selected, getResources().getString(R.string.selected), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_SELECTED);
        toolLayout.addView(selectedIBtn);

        // 表格
        tableIBtn = new ImageButton(context);
        setImageBtn(tableIBtn, layoutParams, R.drawable.draw_table, getResources().getString(R.string.table), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_TABLE);
        toolLayout.addView(tableIBtn);

        // HTML
        htmlIBtn = new ImageButton(context);
        setImageBtn(htmlIBtn, layoutParams, R.drawable.draw_html, getResources().getString(R.string.html), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_HTML);
        toolLayout.addView(htmlIBtn);

        // 分割线
        hrIBtn = new ImageButton(context);
        setImageBtn(hrIBtn, layoutParams, R.drawable.draw_hr, getResources().getString(R.string.hr), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_HR);
        toolLayout.addView(hrIBtn);

        // 表情
        emojiIBtn = new ImageButton(context);
        setImageBtn(emojiIBtn, layoutParams, R.drawable.draw_emoji, getResources().getString(R.string.emoji), IbookerEditorEnum.TOOLVIEW_TAG.IBTN_EMOJI);
        toolLayout.addView(emojiIBtn);
    }

    /**
     * 设置ImageButton
     *
     * @param imageButton        目标ImageButton
     * @param layoutParams       ImageButton布局
     * @param resid              ImageButton背景资源
     * @param contentDescription ImageButton描述信息
     * @param tag                ImageButton的tag信息
     */
    private void setImageBtn(
            ImageButton imageButton,
            ViewGroup.LayoutParams layoutParams,
            @DrawableRes int resid,
            CharSequence contentDescription,
            Object tag) {
        imageButton.setLayoutParams(layoutParams);
        imageButton.setBackgroundResource(resid);
        imageButton.setContentDescription(contentDescription);
        imageButton.setPadding(dp13, dp13, dp13, dp13);
        imageButton.setTag(tag);
        imageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onToolClickListener != null)
                    onToolClickListener.onToolClick(v.getTag());
            }
        });
        imageButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onToolLongClickListener != null)
                    onToolLongClickListener.onToolLongClick(v.getTag());
                return true;
            }
        });
    }

    // 粗体
    public IbookerEditorToolView setBoldIBtnImageResource(@DrawableRes int resId) {
        boldIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setBoldIBtnVisibility(int visibility) {
        boldIBtn.setVisibility(visibility);
        return this;
    }

    // 斜体
    public IbookerEditorToolView setItalicIBtnImageResource(@DrawableRes int resId) {
        italicIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setItalicIBtnVisibility(int visibility) {
        italicIBtn.setVisibility(visibility);
        return this;
    }

    // 删除线
    public IbookerEditorToolView setStrikeoutIBtnImageResource(@DrawableRes int resId) {
        strikeoutIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setStrikeoutIBtnVisibility(int visibility) {
        strikeoutIBtn.setVisibility(visibility);
        return this;
    }

    // 下划线
    public IbookerEditorToolView setUnderlineIBtnImageResource(@DrawableRes int resId) {
        underlineIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setUnderlineIBtnVisibility(int visibility) {
        underlineIBtn.setVisibility(visibility);
        return this;
    }

    // 单词首字母大写
    public IbookerEditorToolView setCapitalsIBtnImageResource(@DrawableRes int resId) {
        capitalsIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setCapitalsIBtnVisibility(int visibility) {
        capitalsIBtn.setVisibility(visibility);
        return this;
    }

    // 单词转大写
    public IbookerEditorToolView setUppercaseIBtnImageResource(@DrawableRes int resId) {
        uppercaseIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setUppercaseIBtnVisibility(int visibility) {
        uppercaseIBtn.setVisibility(visibility);
        return this;
    }

    // 单词转小写
    public IbookerEditorToolView setLowercaseIBtnImageResource(@DrawableRes int resId) {
        lowercaseIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setLowercaseIBtnVisibility(int visibility) {
        lowercaseIBtn.setVisibility(visibility);
        return this;
    }

    // 一级标题
    public IbookerEditorToolView setH1IBtnImageResource(@DrawableRes int resId) {
        h1IBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setH1IBtnVisibility(int visibility) {
        h1IBtn.setVisibility(visibility);
        return this;
    }

    // 二级标题
    public IbookerEditorToolView setH2IBtnImageResource(@DrawableRes int resId) {
        h2IBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setH2IBtnVisibility(int visibility) {
        h2IBtn.setVisibility(visibility);
        return this;
    }

    // 三级标题
    public IbookerEditorToolView setH3IBtnImageResource(@DrawableRes int resId) {
        h3IBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setH3IBtnVisibility(int visibility) {
        h3IBtn.setVisibility(visibility);
        return this;
    }

    // 四级标题
    public IbookerEditorToolView setH4IBtnImageResource(@DrawableRes int resId) {
        h4IBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setH4IBtnVisibility(int visibility) {
        h4IBtn.setVisibility(visibility);
        return this;
    }

    // 五级标题
    public IbookerEditorToolView setH5IBtnImageResource(@DrawableRes int resId) {
        h5IBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setH5IBtnVisibility(int visibility) {
        h5IBtn.setVisibility(visibility);
        return this;
    }

    // 六级标题
    public IbookerEditorToolView setH6IBtnImageResource(@DrawableRes int resId) {
        h6IBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setH6IBtnVisibility(int visibility) {
        h6IBtn.setVisibility(visibility);
        return this;
    }

    // 链接
    public IbookerEditorToolView setLinkIBtnImageResource(@DrawableRes int resId) {
        linkIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setLinkIBtnVisibility(int visibility) {
        linkIBtn.setVisibility(visibility);
        return this;
    }

    // 引用
    public IbookerEditorToolView setQuoteIBtnImageResource(@DrawableRes int resId) {
        quoteIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setQuoteIBtnVisibility(int visibility) {
        quoteIBtn.setVisibility(visibility);
        return this;
    }

    // 代码
    public IbookerEditorToolView setCodeIBtnImageResource(@DrawableRes int resId) {
        codeIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setCodeIBtnVisibility(int visibility) {
        codeIBtn.setVisibility(visibility);
        return this;
    }

    // 图片
    public IbookerEditorToolView setImguIBtnImageResource(@DrawableRes int resId) {
        imguIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setImguIBtnVisibility(int visibility) {
        imguIBtn.setVisibility(visibility);
        return this;
    }

    // 数字列表
    public IbookerEditorToolView setOlIBtnImageResource(@DrawableRes int resId) {
        olIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setOlIBtnVisibility(int visibility) {
        olIBtn.setVisibility(visibility);
        return this;
    }

    // 普通列表
    public IbookerEditorToolView setUlIBtnImageResource(@DrawableRes int resId) {
        ulIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setUlIBtnVisibility(int visibility) {
        ulIBtn.setVisibility(visibility);
        return this;
    }

    // 列表未选中
    public IbookerEditorToolView setUnselectedIBtnImageResource(@DrawableRes int resId) {
        unselectedIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setUnselectedIBtnVisibility(int visibility) {
        unselectedIBtn.setVisibility(visibility);
        return this;
    }

    // 列表选中
    public IbookerEditorToolView setSelectedIBtnImageResource(@DrawableRes int resId) {
        selectedIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setSelectedIBtnVisibility(int visibility) {
        selectedIBtn.setVisibility(visibility);
        return this;
    }

    // 表格
    public IbookerEditorToolView setTableIBtnImageResource(@DrawableRes int resId) {
        tableIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setTableIBtnVisibility(int visibility) {
        tableIBtn.setVisibility(visibility);
        return this;
    }

    // HTML
    public IbookerEditorToolView setHtmlIBtnImageResource(@DrawableRes int resId) {
        htmlIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setHtmlIBtnVisibility(int visibility) {
        htmlIBtn.setVisibility(visibility);
        return this;
    }

    // 分割线
    public IbookerEditorToolView setHrIBtnImageResource(@DrawableRes int resId) {
        hrIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setHrIBtnVisibility(int visibility) {
        hrIBtn.setVisibility(visibility);
        return this;
    }

    // 表情
    public IbookerEditorToolView setEmojiIBtnImageResource(@DrawableRes int resId) {
        emojiIBtn.setImageResource(resId);
        return this;
    }

    public IbookerEditorToolView setEmojiIBtnVisibility(int visibility) {
        emojiIBtn.setVisibility(visibility);
        return this;
    }

    /**
     * 点击事件监听
     */
    public interface OnToolClickListener {
        void onToolClick(Object tag);
    }

    /**
     * 长按事件监听
     */
    public interface OnToolLongClickListener {
        void onToolLongClick(Object tag);
    }

    private OnToolClickListener onToolClickListener;
    private OnToolLongClickListener onToolLongClickListener;

    public IbookerEditorToolView setOnToolClickListener(OnToolClickListener onToolClickListener) {
        this.onToolClickListener = onToolClickListener;
        return this;
    }

    public IbookerEditorToolView setOnToolLongClickListener(OnToolLongClickListener onToolLongClickListener) {
        this.onToolLongClickListener = onToolLongClickListener;
        return this;
    }
}