package cc.ibooker.ibookereditorlib;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * 书客编辑器界面 - ViewPager
 * Created by 邹峰立 on 2018/1/17.
 */
public class IbookerEditorVpView extends ViewPager {
    private IbookerEditorEditView editView;
    private IbookerEditorPreView preView;
    private ArrayList<View> mDatas;
    private IbookerEditorVpAdapter adapter;

    public IbookerEditorEditView getEditView() {
        return editView;
    }

    public IbookerEditorPreView getPreView() {
        return preView;
    }

    public IbookerEditorVpView(Context context) {
        this(context, null);
    }

    public IbookerEditorVpView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    // 初始化
    private void init(Context context) {
        editView = new IbookerEditorEditView(context);
        preView = new IbookerEditorPreView(context);

        if (mDatas == null)
            mDatas = new ArrayList<>();
        mDatas.add(editView);
        mDatas.add(preView);

        setAdapter();
        // 设置缓存
        setOffscreenPageLimit(mDatas.size());
    }

    /**
     * 自定义setAdapter
     */
    private void setAdapter() {
        if (adapter == null) {
            adapter = new IbookerEditorVpAdapter(mDatas);
            this.setAdapter(adapter);
        } else {
            adapter.reflashData(mDatas);
        }
    }
}
