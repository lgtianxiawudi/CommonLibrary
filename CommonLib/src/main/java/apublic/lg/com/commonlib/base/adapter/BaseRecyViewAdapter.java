package apublic.lg.com.commonlib.base.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

/**
 * Created by ligang967 on 16/9/5.
 */
public abstract class  BaseRecyViewAdapter<T> extends RecyclerView.Adapter<BaseRecyViewAdapterHolder>{

    private List<T> datas = null;

    private AdapterView.OnItemClickListener onItemClickListener;

    public BaseRecyViewAdapter(List<T> datas) {
        this.datas = datas;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public BaseRecyViewAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return BaseRecyViewAdapterHolder.createHolderByRedId(parent.getContext(),getLayoutId(),parent,false);
    }

    @Override
    public void onBindViewHolder(BaseRecyViewAdapterHolder holder, final int position) {
        bindDataToItem(holder,datas.get(position));
        holder.getContentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(null,null,position,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (datas!=null){
            return datas.size();
        }
        return 0;
    }
    public abstract void bindDataToItem(BaseRecyViewAdapterHolder baseRecyViewAdapterHolder,T t);

    public abstract int getLayoutId();
}
