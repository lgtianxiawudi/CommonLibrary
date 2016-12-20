package apublic.lg.com.commonlib.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class BaseListAdapter<T> extends BaseAdapter {

    public List<T> data;
    public Context context;
    private int layoutId;

    public BaseListAdapter(Context context, List<T> data, int layoutId) {
        this.context = context;
        this.data = data;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = ViewHolder.get(context, view, viewGroup, layoutId, position);
        setDetailView(holder, getItem(position), position);
        return holder.getConvertView();

    }

    public void refresh(List<T> list) {
        this.data = list;
        notifyDataSetChanged();
    }

    public abstract void setDetailView(ViewHolder holder, T t, int position);
}
