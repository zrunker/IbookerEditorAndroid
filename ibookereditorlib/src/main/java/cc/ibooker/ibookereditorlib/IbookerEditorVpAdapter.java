package cc.ibooker.ibookereditorlib;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * ViewPage适配器
 * Created by 邹峰立 on 2018/2/11.
 */
public class IbookerEditorVpAdapter extends PagerAdapter {
    private ArrayList<View> mDatas = new ArrayList<>();

    IbookerEditorVpAdapter(ArrayList<View> list) {
        this.mDatas = list;
    }

    void reflashData(ArrayList<View> list) {
        this.mDatas = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mDatas.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 移除
        if (position >= 0 && position < mDatas.size())
            container.removeView(mDatas.get(position));
    }
}
