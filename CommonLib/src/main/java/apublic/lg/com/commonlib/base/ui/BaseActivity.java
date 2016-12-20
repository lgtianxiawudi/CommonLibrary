package apublic.lg.com.commonlib.base.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import apublic.lg.com.commonlib.R;
import apublic.lg.com.commonlib.swipeback.SwipeBackActivity;
import apublic.lg.com.commonlib.util.AbSystemBarTintManager;
import apublic.lg.com.commonlib.view.TopTitleView;


public abstract class BaseActivity extends SwipeBackActivity {


    protected String TAG = this.getClass().getSimpleName();

    protected Handler mHandler = null;

    private FrameLayout content;
    public TopTitleView topTitleView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_root);
        getWindow().setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        AbSystemBarTintManager.setWindowStatusBarColor(this, Color.BLACK);
        initBaseView();
    }

    /**
     * set title
     */
    protected abstract String currActivityName();


    /**
     * 请求数据
     *
     */
    protected abstract void requestData();

    protected abstract void initView();


    private void initBaseView() {
        content = (FrameLayout) findViewById(R.id.root_container);
        topTitleView = (TopTitleView) findViewById(R.id.topTitleView);
        setTitle(currActivityName());
    }

    /**
     * set title for fragment
     *
     * @param title
     */
    public void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {

            topTitleView.setVisibility(View.GONE);

            return;
        } else {
            topTitleView.setVisibility(View.VISIBLE);
        }
        topTitleView.setTitle(title);
    }


    public TopTitleView getTopTitleView() {
        return topTitleView;
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, content);
        initView();
    }

    @Override
    public void setContentView(View view) {
        if (view == null) {
            return;
        }

        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(view,layoutParams);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        content.addView(view, params);
        initView();
    }

    @Override
    protected void onStart() {
        requestData();
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
