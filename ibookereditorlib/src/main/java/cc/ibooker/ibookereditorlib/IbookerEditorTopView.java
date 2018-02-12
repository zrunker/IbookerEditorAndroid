package cc.ibooker.ibookereditorlib;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
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
    private ImageButton undoIBtn, redoIBtn, editIBtn, previewIBtn, helpIBtn;

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
        setMinimumHeight(dpToPx(48));
        setPadding(dpToPx(5), dpToPx(5), dpToPx(5), dpToPx(5));
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        init(context);
    }

    // 初始化
    private void init(Context context) {
        backImg = new ImageView(context);
        LayoutParams backParams = new LayoutParams(dpToPx(30), ViewGroup.LayoutParams.WRAP_CONTENT);
        backParams.setMargins(dpToPx(5), 0, dpToPx(5), 0);
        backImg.setLayoutParams(backParams);
        backImg.setImageResource(R.drawable.icon_back);
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
        layoutParams.setMargins(dpToPx(5), 0, dpToPx(5), 0);

        undoIBtn = new ImageButton(context);
        undoIBtn.setLayoutParams(layoutParams);
        undoIBtn.setBackgroundResource(R.drawable.draw_undo);
        undoIBtn.setContentDescription(getResources().getString(R.string.undo));
        undoIBtn.setPadding(dpToPx(12), dpToPx(12), dpToPx(12), dpToPx(12));
        undoIBtn.setTag(IbookerEditorEnum.TOOLVIEW_TAG.IBTN_UNDO);
        undoIBtn.setOnClickListener(this);
        rightLayout.addView(undoIBtn);

        redoIBtn = new ImageButton(context);
        redoIBtn.setLayoutParams(layoutParams);
        redoIBtn.setBackgroundResource(R.drawable.draw_redo);
        redoIBtn.setContentDescription(getResources().getString(R.string.redo));
        redoIBtn.setPadding(dpToPx(12), dpToPx(12), dpToPx(12), dpToPx(12));
        redoIBtn.setTag(IbookerEditorEnum.TOOLVIEW_TAG.IBTN_REDO);
        redoIBtn.setOnClickListener(this);
        rightLayout.addView(redoIBtn);

        editIBtn = new ImageButton(context);
        editIBtn.setLayoutParams(layoutParams);
        editIBtn.setBackgroundResource(R.drawable.draw_edit);
        editIBtn.setContentDescription(getResources().getString(R.string.edit));
        editIBtn.setPadding(dpToPx(12), dpToPx(12), dpToPx(12), dpToPx(12));
        editIBtn.setTag(IbookerEditorEnum.TOOLVIEW_TAG.IBTN_EDIT);
        editIBtn.setOnClickListener(this);
        rightLayout.addView(editIBtn);

        previewIBtn = new ImageButton(context);
        previewIBtn.setLayoutParams(layoutParams);
        previewIBtn.setBackgroundResource(R.drawable.draw_preview);
        previewIBtn.setContentDescription(getResources().getString(R.string.preview));
        previewIBtn.setPadding(dpToPx(12), dpToPx(12), dpToPx(12), dpToPx(12));
        previewIBtn.setTag(IbookerEditorEnum.TOOLVIEW_TAG.IBTN_PREVIEW);
        previewIBtn.setOnClickListener(this);
        rightLayout.addView(previewIBtn);

        helpIBtn = new ImageButton(context);
        helpIBtn.setLayoutParams(layoutParams);
        helpIBtn.setBackgroundResource(R.drawable.draw_help);
        helpIBtn.setContentDescription(getResources().getString(R.string.help));
        helpIBtn.setPadding(dpToPx(12), dpToPx(12), dpToPx(12), dpToPx(12));
        helpIBtn.setTag(IbookerEditorEnum.TOOLVIEW_TAG.IBTN_HELP);
        helpIBtn.setOnClickListener(this);
        rightLayout.addView(helpIBtn);

        LayoutParams params = new LayoutParams(dpToPx(29), dpToPx(29));
        params.setMargins(dpToPx(5), 0, dpToPx(5), 0);
        aboutImg = new ImageView(context);
        aboutImg.setLayoutParams(params);
        aboutImg.setImageResource(R.drawable.ibooker_editor_logo);
        aboutImg.setContentDescription(getResources().getString(R.string.about));
        aboutImg.setPadding(dpToPx(5), dpToPx(5), dpToPx(5), dpToPx(5));
        aboutImg.setTag(IbookerEditorEnum.TOOLVIEW_TAG.IBTN_ABOUT);
        aboutImg.setOnClickListener(this);
        rightLayout.addView(aboutImg);
    }

    /**
     * dp转换成px
     *
     * @param value 待转换值
     */
    public int dpToPx(float value) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm);
    }

    // 点击事件监听
    @Override
    public void onClick(View v) {
        if (onTopClickListener != null)
            onTopClickListener.onTopClick(v.getTag());
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
