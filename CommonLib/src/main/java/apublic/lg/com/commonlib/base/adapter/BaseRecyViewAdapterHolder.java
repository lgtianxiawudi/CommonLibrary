package apublic.lg.com.commonlib.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by ligang967 on 16/9/5.
 */
public class BaseRecyViewAdapterHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> listViews = null;
    private View mContentView = null;

    private BaseRecyViewAdapterHolder(View itemView) {
        super(itemView);
        listViews = new SparseArray<>();
        mContentView = itemView;
    }

    /**
     * 根据ResId创建Holder
     * @param context
     * @param resId
     * @return
     */
    public static BaseRecyViewAdapterHolder createHolderByRedId(@NonNull Context context, @NonNull int resId,ViewGroup root, boolean attachToRoot) {
        View view = LayoutInflater.from(context).inflate(resId, root,attachToRoot);
        return new BaseRecyViewAdapterHolder(view);
    }

    /**
     * 根据View创建Holder
     * @param mContentView
     * @return
     */
    public static BaseRecyViewAdapterHolder createHolderByView(@NonNull View mContentView) {
        return new BaseRecyViewAdapterHolder(mContentView);
    }

    /**
     * 根据RedId获取VIew实例
     * @param resId
     * @param <T>
     * @return
     */
    public <T extends View> T getViewById(@NonNull int resId) {
        View view = listViews.get(resId);
        if (view == null) {
            view = mContentView.findViewById(resId);
        }
        return (T) view;
    }

    /**
     * 获取跟View
     * @return
     */
    public View getContentView() {
        return mContentView;
    }
}
