package apublic.lg.com.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import apublic.lg.com.commonlib.base.ui.BaseFragment;
import apublic.lg.com.commonlib.demo.R;

/**
 * Created by ligang967 on 16/9/6.
 */
public class MessageFragment extends BaseFragment {
    @Override
    protected String currentTitle() {
        return getString(R.string.main_tab_message);
    }

    @Override
    protected void requestData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView content = new TextView(getActivity());
        content.setText(currentTitle());
        return content;
    }
}
