package apublic.lg.com.commonlib.base.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;



public abstract class BaseFragment extends android.support.v4.app.Fragment  {

    public BaseActivity mActivity;

    protected Handler mHandler = null;

    protected String TAG = this.getClass().getSimpleName();

    /**
     * do the request for fragment
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        requestData();
    }

    /**
     * use to set fragment's title
     * @param transit
     * @param enter
     * @param nextAnim
     * @return
     */
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if(enter){
            ((BaseActivity)getActivity()).setTitle(currentTitle());
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    /**
     * 如果 当前的fragment不需要改变标题  ,返回 null 或是 "" ,默认重写返回 null  . 无需再管
     * @return
     */
    protected abstract String currentTitle();

    protected  abstract void  requestData();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
    }
}
