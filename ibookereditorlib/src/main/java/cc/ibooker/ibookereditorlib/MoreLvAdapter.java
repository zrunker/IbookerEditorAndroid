package cc.ibooker.ibookereditorlib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 更多PopupWindow列表适配器
 */
public class MoreLvAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<MoreBean> mDatas;

    public MoreLvAdapter(Context context, ArrayList<MoreBean> list) {
        this.inflater = LayoutInflater.from(context);
        this.mDatas = list;
    }

    public void reflashData(ArrayList<MoreBean> list) {
        this.mDatas = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.ibooker_editor_layout_more_item, parent, false);
            holder.imageView = convertView.findViewById(R.id.imageView);
            holder.textView = convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MoreBean moreBean = mDatas.get(position);
        holder.imageView.setImageResource(moreBean.getRes());
        holder.textView.setText(moreBean.getName());
        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
