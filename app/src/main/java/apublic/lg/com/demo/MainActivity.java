package apublic.lg.com.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;


import apublic.lg.com.commonlib.base.ui.BaseActivity;
import apublic.lg.com.commonlib.demo.R;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    public RadioGroup mainTab;

    private HomeFragment homeFragment = null;

    private MessageFragment messageFragment = null;

    private ShopFragment shopFragment = null;

    private LibraryFragment libraryFragment = null;


    @Override
    protected String currActivityName() {
        return getString(R.string.main_title);
    }

    @Override
    protected void requestData() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        mainTab = (RadioGroup)findViewById(R.id.main_tab);
        mainTab.setOnCheckedChangeListener(this);
        homeFragment = new HomeFragment();
        messageFragment = new MessageFragment();
        shopFragment = new ShopFragment();
        libraryFragment = new LibraryFragment();
        switchFragment(homeFragment, mLastselectFragment, 0, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.main_tab_index:{
                switchFragment(homeFragment, mLastselectFragment, 0, true);
            }
            break;
            case R.id.main_tab_message:{
                switchFragment(messageFragment, mLastselectFragment, 1, true);
            }
            break;
            case R.id.main_tab_shop:{
                switchFragment(shopFragment, mLastselectFragment, 2, true);
            }
            break;
            case R.id.main_tab_library:{
                switchFragment(libraryFragment, mLastselectFragment, 3, true);
            }
            break;
        }
    }
    private final static String[] mFragmentTag = {"homefragment", "messagefragment",
            "managefragment", "servicefragment", "minefragment"};
    private int mLastselectFragment = 0;

    /**
     * change fragment
     *
     * @param mFragment
     * @param selectedPage
     * @param currPage
     * @param isAnim
     */
    protected void switchFragment(Fragment mFragment, int selectedPage, int currPage, boolean isAnim) {


        mLastselectFragment = currPage;

        Fragment orginFragment = getSupportFragmentManager().findFragmentByTag(mFragmentTag[selectedPage]);
        FragmentTransaction ft = getFragmentTransaction(isAnim, selectedPage, currPage);
        if (mFragment != null && !mFragment.isAdded()) {
            if (orginFragment != null) {
                ft.hide(orginFragment).add(R.id.main_content, mFragment, mFragmentTag[currPage]);
            } else {
                ft.add(R.id.main_content, mFragment, mFragmentTag[currPage]);
            }

        } else {
            if (orginFragment != null) {
                ft.hide(orginFragment).show(mFragment);
            } else {
                ft.show(mFragment);
            }

        }
        ft.commit();
    }


    /**
     * get transaction
     *
     * @param isAnimation
     * @param selectedPage
     * @param currPage
     * @return
     */
    private FragmentTransaction getFragmentTransaction(boolean isAnimation, int selectedPage, int currPage) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (isAnimation) {
            if (selectedPage < currPage) {
                ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
            } else {
                ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out);
            }
        }
        return ft;
    }
}
