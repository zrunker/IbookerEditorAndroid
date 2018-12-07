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
 * Emjio适配器
 */
public class EmjioGwAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<EmjioData> mDatas;

    public EmjioGwAdapter(Context context, ArrayList<EmjioData> list) {
        this.inflater = LayoutInflater.from(context);
        this.mDatas = list;
    }

    public void reflushData(ArrayList<EmjioData> list) {
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
            convertView = inflater.inflate(R.layout.ibooker_editor_layout_emjio_item, parent, false);
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        EmjioData emjioData = mDatas.get(position);
        holder.textView.setText(emjioData.getText());
        return convertView;
    }

    private static class ViewHolder {
        TextView textView;
    }
}
