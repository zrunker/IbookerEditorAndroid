package cc.ibooker.ibookereditorlib;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * 书客编辑器 - 预览界面
 * Created by 邹峰立 on 2018/2/11.
 */
public class IbookerEditorPreView extends WebView {

    public IbookerEditorPreView(Context context) {
        this(context, null);
    }

    public IbookerEditorPreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IbookerEditorPreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
