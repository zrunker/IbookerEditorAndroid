package cc.ibooker.ibookereditorlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

/**
 * 书客编辑器 - 预览界面 - 自定义WebView
 * Created by 邹峰立 on 2018/2/11.
 */
public class IbookerEditorWebView extends WebView {
    private boolean isLoadFinished = false;// 本地文件是否加载完成
    private boolean isExecuteCompile = false;// 是否执行预览
    private boolean isExecuteHtmlCompile = false;// 是否执行HTML预览
    private boolean isLoadError = false;// 本地文件是否加载错误
    private String ibookerEditorText, ibookerEditorHtml;

    private ArrayList<String> imgPathList;// WebView所有图片地址
    private IbookerEditorJsCheckImgEvent ibookerEditorJsCheckImgEvent;
    private WebSettings webSettings;
    private int currentFontSize;

    public int getCurrentFontSize() {
        return currentFontSize;
    }

    public IbookerEditorWebView(Context context) {
        this(context, null);
    }

    public IbookerEditorWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IbookerEditorWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setVerticalScrollBarEnabled(false);
        setVerticalScrollbarOverlay(false);
        setHorizontalScrollBarEnabled(false);
        setHorizontalScrollbarOverlay(false);

        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public void destroy() {
        this.clearHistory();
        super.destroy();
    }

    // 初始化
    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    private void init() {
        webSettings = this.getSettings();
        // 支持内容重新布局
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 允许JS
        webSettings.setJavaScriptEnabled(true);
        // 支持插件
        webSettings.setPluginState(WebSettings.PluginState.ON);
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

        // 获取当前字体大小
        if (webSettings.getTextSize() == WebSettings.TextSize.SMALLEST) {
            currentFontSize = 1;
        } else if (webSettings.getTextSize() == WebSettings.TextSize.SMALLER) {
            currentFontSize = 2;
        } else if (webSettings.getTextSize() == WebSettings.TextSize.NORMAL) {
            currentFontSize = 3;
        } else if (webSettings.getTextSize() == WebSettings.TextSize.LARGER) {
            currentFontSize = 4;
        } else if (webSettings.getTextSize() == WebSettings.TextSize.LARGEST) {
            currentFontSize = 5;
        }

        // 隐藏滚动条
        setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        // 使页面获取焦点，防止点击无响应
        requestFocus();
        // 设置WebViewClient
        setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (ibookerEditorWebViewUrlLoadingListener != null)
                    return ibookerEditorWebViewUrlLoadingListener.shouldOverrideUrlLoading(view, url);
                else {
//                    view.loadUrl(url);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    getContext().startActivity(intent);
                    return true;
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (ibookerEditorWebViewUrlLoadingListener != null)
                    return ibookerEditorWebViewUrlLoadingListener.shouldOverrideUrlLoading(view, request);
                else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        view.loadUrl(request.getUrl().toString());
                    }
                    return true;
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (ibookerEditorWebViewUrlLoadingListener != null)
                    ibookerEditorWebViewUrlLoadingListener.onReceivedError(view, request, error);
                else
                    // 当网页加载出错时，加载本地错误文件
                    IbookerEditorWebView.this.loadUrl("file:///android_asset/error.html");
                isLoadError = true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if (error.getPrimaryError() == SslError.SSL_DATE_INVALID
                        || error.getPrimaryError() == SslError.SSL_EXPIRED
                        || error.getPrimaryError() == SslError.SSL_INVALID
                        || error.getPrimaryError() == SslError.SSL_UNTRUSTED) {
                    handler.proceed();
                } else {
                    handler.cancel();
                }
                if (ibookerEditorWebViewUrlLoadingListener != null)
                    ibookerEditorWebViewUrlLoadingListener.onReceivedSslError(view, handler, error);
                else
                    // 当网页加载出错时，加载本地错误文件
                    IbookerEditorWebView.this.loadUrl("file:///android_asset/error.html");
                isLoadError = true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (ibookerEditorWebViewUrlLoadingListener != null)
                    ibookerEditorWebViewUrlLoadingListener.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                isLoadFinished = true;
                if (isExecuteCompile) {
                    ibookerCompile(ibookerEditorText);
                } else if (isExecuteHtmlCompile) {
                    ibookerHtmlCompile(ibookerEditorHtml);
                } else {
                    addWebViewListener();
                }
                if (ibookerEditorWebViewUrlLoadingListener != null)
                    ibookerEditorWebViewUrlLoadingListener.onPageFinished(view, url);
            }
        });
        // 添加js
        ibookerEditorJsCheckImgEvent = new IbookerEditorJsCheckImgEvent();
        addJavascriptInterface(ibookerEditorJsCheckImgEvent, "ibookerEditorJsCheckImgEvent");
        // 加载本地HTML
        loadUrl("file:///android_asset/ibooker_editor_index.html");
        isLoadError = false;
    }

    // 给WebView添加相关监听
    private void addWebViewListener() {
        // 获取WebView中全部图片地址
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.evaluateJavascript("javascript:getImgAllPaths()", new ValueCallback<String>() {

                @Override
                public void onReceiveValue(String value) {
                    // value即为所有图片地址
                    if (!TextUtils.isEmpty(value)) {
                        value = value.replace("\"", "").replace("\"", "");
                        if (!TextUtils.isEmpty(value)) {
                            if (imgPathList == null)
                                imgPathList = new ArrayList<>();
                            imgPathList.clear();
                            String[] imgPaths = value.split(";ibookerEditor;");
                            for (String imgPath : imgPaths) {
                                if (!TextUtils.isEmpty(imgPath))
                                    imgPathList.add(imgPath);
                            }
                            ibookerEditorJsCheckImgEvent.setmImgPathList(imgPathList);
                        }
                    }
                }
            });
        }

        // 动态添加图片点击事件
        this.loadUrl("javascript:(function() {"
                + "  var objs = document.getElementsByTagName(\"img\"); "
                + "  for(var i = 0; i < objs.length; i++) {"
                + "     objs[i].onclick = function() {"
                + "          window.ibookerEditorJsCheckImgEvent.onCheckImg(this.src);"
                + "     }"
                + "  }"
                + "})()");
    }

    /**
     * 是否完成本地文件加载
     */
    public boolean isLoadFinished() {
        return isLoadFinished;
    }

    /**
     * 执行预览
     *
     * @param ibookerEditorText 待预览内容 非HTML
     */
    public void ibookerCompile(String ibookerEditorText) {
        if (!TextUtils.isEmpty(ibookerEditorText)) {
            if (isLoadFinished && !isLoadError) {
                ibookerEditorText = ibookerEditorText.replaceAll("\\n", "\\\\n");
                String js = "javascript:ibookerCompile('" + ibookerEditorText + "')";
                this.loadUrl(js);

                // 重新WebView添加监听
                addWebViewListener();

                this.isExecuteCompile = false;
                this.ibookerEditorText = null;
                this.isExecuteHtmlCompile = false;
                this.ibookerEditorHtml = null;
            } else {
                if (isLoadError) {
                    // 加载本地HTML
                    loadUrl("file:///android_asset/ibooker_editor_index.html");
                    isLoadError = false;
                }
                this.isExecuteCompile = true;
                this.ibookerEditorText = ibookerEditorText;
            }
        }
    }

    /**
     * 执行Html预览
     *
     * @param ibookerEditorHtml 待预览内容 HTML
     */
    public void ibookerHtmlCompile(String ibookerEditorHtml) {
        if (!TextUtils.isEmpty(ibookerEditorHtml)) {
            if (isLoadFinished && !isLoadError) {
                String js = "javascript:ibookerHtmlCompile('" + ibookerEditorHtml + "')";
                this.loadUrl(js);

                // 重新WebView添加监听
                addWebViewListener();

                this.isExecuteHtmlCompile = false;
                this.ibookerEditorHtml = null;
                this.isExecuteCompile = false;
                this.ibookerEditorText = null;
            } else {
                if (isLoadError) {
                    // 加载本地HTML
                    loadUrl("file:///android_asset/ibooker_editor_index.html");
                    isLoadError = false;
                }
                this.isExecuteHtmlCompile = true;
                this.ibookerEditorHtml = ibookerEditorHtml;
            }
        }
    }

    /**
     * 获取整个WebView截图 - 5.0以上无效
     */
    public Bitmap getWebViewBitmap() {
        Picture picture = capturePicture();
        Bitmap bitmap = Bitmap.createBitmap(picture.getWidth(), picture.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        picture.draw(canvas);
        return bitmap;
    }

    /**
     * 获取WebView长图
     */
    public Bitmap getLongImage() {
        measure(MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
        setDrawingCacheEnabled(true);
        buildDrawingCache();
        Bitmap longImage = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(longImage);  // 画布的宽高和 WebView 的网页保持一致
        Paint paint = new Paint();
        canvas.drawBitmap(longImage, 0, getMeasuredHeight(), paint);
        draw(canvas);
        return longImage;
    }

    /**
     * 设置当前字体大小
     */
    public void setIbookerEditorWebViewFontSize(int fontSize) {
        if (fontSize >= 1 && fontSize <= 5) {
            currentFontSize = fontSize;
            switch (fontSize) {
                case 1:
                    webSettings.setTextSize(WebSettings.TextSize.SMALLEST);
                    break;
                case 2:
                    webSettings.setTextSize(WebSettings.TextSize.SMALLER);
                    break;
                case 3:
                    webSettings.setTextSize(WebSettings.TextSize.NORMAL);
                    break;
                case 4:
                    webSettings.setTextSize(WebSettings.TextSize.LARGER);
                    break;
                case 5:
                    webSettings.setTextSize(WebSettings.TextSize.LARGEST);
                    break;
            }
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mIbookerEditorWebViewOnScrollChangedCallback != null) {
            mIbookerEditorWebViewOnScrollChangedCallback.onScroll(l - oldl, t - oldt);
        }
    }

    // 滚动监听接口
    public interface IbookerEditorWebViewOnScrollChangedCallback {
        void onScroll(int dx, int dy);
    }

    private IbookerEditorWebViewOnScrollChangedCallback mIbookerEditorWebViewOnScrollChangedCallback;

    public void setIbookerEditorWebViewOnScrollChangedCallback(IbookerEditorWebViewOnScrollChangedCallback ibookerEditorWebViewOnScrollChangedCallback) {
        mIbookerEditorWebViewOnScrollChangedCallback = ibookerEditorWebViewOnScrollChangedCallback;
    }

    // 图片预览接口
    public interface IbookerEditorImgPreviewListener {
        void onIbookerEditorImgPreview(String currentPath, int position, ArrayList<String> imgAllPathList);
    }

    public void setIbookerEditorImgPreviewListener(IbookerEditorImgPreviewListener ibookerEditorImgPreviewListener) {
        ibookerEditorJsCheckImgEvent.setmIbookerEditorImgPreviewListener(ibookerEditorImgPreviewListener);
    }

    // Url加载状态监听
    public interface IbookerEditorWebViewUrlLoadingListener {
        boolean shouldOverrideUrlLoading(WebView view, String url);

        boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request);

        void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error);

        void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error);

        void onPageStarted(WebView view, String url, Bitmap favicon);

        void onPageFinished(WebView view, String url);
    }

    private IbookerEditorWebViewUrlLoadingListener ibookerEditorWebViewUrlLoadingListener;

    public void setIbookerEditorWebViewUrlLoadingListener(IbookerEditorWebViewUrlLoadingListener ibookerEditorWebViewUrlLoadingListener) {
        this.ibookerEditorWebViewUrlLoadingListener = ibookerEditorWebViewUrlLoadingListener;
    }
}
