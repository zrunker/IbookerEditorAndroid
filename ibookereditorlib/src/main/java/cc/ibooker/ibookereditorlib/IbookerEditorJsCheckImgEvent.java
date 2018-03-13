package cc.ibooker.ibookereditorlib;

import android.webkit.JavascriptInterface;

import java.util.ArrayList;

/**
 * 书客编辑器 - 查看图片js事件
 * Created by 邹峰立 on 2018/3/13.
 */
public class IbookerEditorJsCheckImgEvent {
    private ArrayList<String> mImgPathList;
    private IbookerEditorPreView.IbookerEditorImgPreviewListener mIbookerEditorImgPreviewListener;

    public ArrayList<String> getmImgPathList() {
        return mImgPathList;
    }

    public void setmImgPathList(ArrayList<String> mImgPathList) {
        this.mImgPathList = mImgPathList;
    }

    public IbookerEditorPreView.IbookerEditorImgPreviewListener getmIbookerEditorImgPreviewListener() {
        return mIbookerEditorImgPreviewListener;
    }

    public void setmIbookerEditorImgPreviewListener(IbookerEditorPreView.IbookerEditorImgPreviewListener mIbookerEditorImgPreviewListener) {
        this.mIbookerEditorImgPreviewListener = mIbookerEditorImgPreviewListener;
    }

    @JavascriptInterface
    public void onCheckImg(String src) {
        // 执行相应的逻辑操作-查看图片
        if (mIbookerEditorImgPreviewListener != null) {
            int position = 0;
            if (mImgPathList != null) {
                for (int i = 0; i < mImgPathList.size(); i++) {
                    String imgPath = mImgPathList.get(i);
                    if (imgPath.equals(src)) {
                        position = i;
                        break;
                    }
                }
            }
            mIbookerEditorImgPreviewListener.onIbookerEditorImgPreview(src, position, mImgPathList);
        }
    }
}
