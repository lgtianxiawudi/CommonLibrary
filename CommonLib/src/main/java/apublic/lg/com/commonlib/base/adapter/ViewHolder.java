package apublic.lg.com.commonlib.base.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ViewHolder {

    private View convertView;
    private SparseArray<View> sparseArray;
    private int position;

    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.position = position;
        this.sparseArray = new SparseArray<>();
        this.convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        convertView.setTag(this);
    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId,
                                 int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.position = position;
            return viewHolder;
        }
    }

    public View getConvertView() {
        return convertView;
    }

    public <T extends View> T getView(int viewId) {
        View view = sparseArray.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            sparseArray.put(viewId, view);
        }
        return (T) view;
    }
}
