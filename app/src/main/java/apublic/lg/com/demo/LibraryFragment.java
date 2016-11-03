package apublic.lg.com.demo;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;


import apublic.lg.com.commonlib.base.ui.BaseFragment;
import apublic.lg.com.commonlib.bridge.BridgeCallBackFunction;
import apublic.lg.com.commonlib.bridge.BridgeHandler;
import apublic.lg.com.commonlib.bridge.BridgeInstance;
import apublic.lg.com.commonlib.demo.R;
import apublic.lg.com.commonlib.util.LogUtil;

/**
 * Created by ligang967 on 16/9/6.
 */
public class LibraryFragment extends BaseFragment {

    private BridgeInstance bridgeInstance;

    WebView content;

    @Override
    protected String currentTitle() {
        return getString(R.string.main_tab_library);
    }

    @Override
    protected void requestData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        content = new WebView(getActivity());
        return content;
    }

    Handler handler = new Handler();

    @Override
    public void onResume() {
        super.onResume();
        bridgeInstance = BridgeInstance.init(content);
        bridgeInstance.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, BridgeCallBackFunction function) {
                LogUtil.e(data);
                function.onCallBack("Native return");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bridgeInstance.sendMsg("lg", "111111", new BridgeCallBackFunction() {
                            @Override
                            public void onCallBack(String data) {
                                LogUtil.d(data+"");
                            }
                        });
                    }
                },10*1000);
            }
        });
        content.loadUrl("file:///android_asset/demo.html");


    }
}
