package cc.ibooker.ibookereditorlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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

        init();
    }

    // 初始化
    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        WebSettings webSettings = this.getSettings();
        // 允许JS
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // access Assets and resources
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(false);
        // 提高渲染优先级
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 设置编码格式
        webSettings.setDefaultTextEncodingName("utf-8");
        // 支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        // 关闭webview中缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        // 支持缩放，默认为true。
        webSettings.setSupportZoom(true);
        // 设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setBuiltInZoomControls(true);
        // 隐藏原生的缩放控件
        webSettings.setDisplayZoomControls(false);


        // 隐藏滚动条
        this.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        // 使页面获取焦点，防止点击无响应
        this.requestFocus();
        // 设置WebViewClient
        this.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                // 当网页加载出错时，加载本地错误文件
                IbookerEditorPreView.this.loadUrl("file:///android_asset/error.html");
            }
        });
        // 加载本地HTML
        this.loadUrl("file:///android_asset/ibooker_editor_index.html");
    }

    /**
     * 执行Html预览
     *
     * @param ibookerEditorText 待预览内容 非HTML
     */
    public void ibookerHtmlCompile(String ibookerEditorText) {
        ibookerEditorText = ibookerEditorText.replaceAll("\\n", "\\\\n");
        String js = "javascript:ibookerHtmlCompile('" + ibookerEditorText + "')";
        this.loadUrl(js);
    }
}
