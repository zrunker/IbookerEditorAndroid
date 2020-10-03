package cc.ibooker.ibookereditorlib;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;

/**
 * 更多PopupWindow
 */
public class IbookerEditorMorePopuwindow extends PopupWindow {
    private Context context;
    private ListView listView;
    private MoreLvAdapter adapter;

    public IbookerEditorMorePopuwindow(Context context, ArrayList<MoreBean> list) {
        this(context, null, list);
    }

    public IbookerEditorMorePopuwindow(Context context, AttributeSet attrs, ArrayList<MoreBean> list) {
        this(context, attrs, 0, list);
    }

    public IbookerEditorMorePopuwindow(Context context, AttributeSet attrs, int defStyleAttr, ArrayList<MoreBean> list) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(list);
    }

    private void init(ArrayList<MoreBean> list) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.ibooker_editor_layout_more, null);
        listView = rootView.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ClickUtil.isFastClick()) return;
                dismiss();
                if (onMoreLvItemClickListener != null)
                    onMoreLvItemClickListener.onItemClick(parent, view, position, id);
                else {
                    if (position == 0) {// 帮助
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        Uri content_url = Uri.parse("http://ibooker.cc/article/1/detail");
                        intent.setData(content_url);
                        context.startActivity(intent);
                    } else if (position == 1) {// 关于
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        Uri content_url = Uri.parse("http://ibooker.cc/article/182/detail");
                        intent.setData(content_url);
                        context.startActivity(intent);
                    }
                }
            }
        });
        setMoreLvAdapter(list);

        setContentView(rootView);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(IbookerEditorUtil.dpToPx(context, 150F));
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
    }

    public void setMoreLvAdapter(ArrayList<MoreBean> list) {
        if (adapter == null) {
            adapter = new MoreLvAdapter(context, list);
            listView.setAdapter(adapter);
        } else {
            adapter.reflashData(list);
        }
    }

    public interface OnMoreLvItemClickListener {
        void onItemClick(AdapterView<?> parent, View view, int position, long id);
    }

    private OnMoreLvItemClickListener onMoreLvItemClickListener;

    public IbookerEditorMorePopuwindow setOnMoreLvItemClickListener(OnMoreLvItemClickListener onMoreLvItemClickListener) {
        this.onMoreLvItemClickListener = onMoreLvItemClickListener;
        return this;
    }
}
