package apublic.lg.com.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.util.Arrays;

import apublic.lg.com.commonlib.base.ui.BaseFragment;
import apublic.lg.com.commonlib.demo.R;


/**
 * Created by ligang967 on 16/9/6.
 */
public class HomeFragment extends BaseFragment {


    @Override
    protected String currentTitle() {
        return getString(R.string.main_tab_index);
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

    @Override
    public void onResume() {
        super.onResume();
    }

}
