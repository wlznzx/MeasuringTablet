package alauncher.cn.measuringtablet.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;
import java.util.List;

import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.BaseActivity;
import alauncher.cn.measuringtablet.view.fragment.CodeBaseInfoFragment;
import alauncher.cn.measuringtablet.view.fragment.CodeStepFragment;
import alauncher.cn.measuringtablet.view.fragment.ForceCalibrationFragment;
import alauncher.cn.measuringtablet.view.fragment.WorkpieceFragment;
import butterknife.BindView;

public class CodeDetailActivity extends BaseActivity {

    @BindView(R.id.tab_tl_indicator)
    protected TabLayout mTlIndicator;


    @BindView(R.id.tab_vp)
    protected ViewPager mVp;
    /**************Adapter***************/
    protected TabFragmentPageAdapter tabFragmentPageAdapter;
    /************Params*******************/
    protected List<Fragment> mFragmentList;
    private List<String> mTitleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_code_detail);
    }

    @Override
    protected void initView() {
        CodeBaseInfoFragment codeBaseInfoFragment = null;
        ForceCalibrationFragment forceCalibrationFragment = null;
        CodeStepFragment launcherFragment = null;
        WorkpieceFragment mWorkpieceFragment = null;
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof CodeBaseInfoFragment) {
                codeBaseInfoFragment = (CodeBaseInfoFragment) fragment;
            } else if (fragment instanceof alauncher.cn.measuringtablet.view.fragment.CodeBaseInfoFragment) {
                forceCalibrationFragment = (ForceCalibrationFragment) fragment;
            } else if (fragment instanceof CodeStepFragment) {
                launcherFragment = (CodeStepFragment) fragment;
            } else if(fragment instanceof  WorkpieceFragment){
                mWorkpieceFragment = (WorkpieceFragment) fragment;
            }
        }
        if (codeBaseInfoFragment == null)
            codeBaseInfoFragment = new CodeBaseInfoFragment();
        if (forceCalibrationFragment == null)
            forceCalibrationFragment = new ForceCalibrationFragment();
        if (launcherFragment == null)
            launcherFragment = new CodeStepFragment();
        if(mWorkpieceFragment == null){
            mWorkpieceFragment = new WorkpieceFragment();
        }

        String[] mTitles = new String[]{getString(R.string.code_base_info),
                getString(R.string.workpiece_pic),
                getString(R.string.code_force_cailbration),
                getString(R.string.code_step)};
        mTitleList = Arrays.asList(mTitles);


        mFragmentList = Arrays.asList(codeBaseInfoFragment,mWorkpieceFragment, forceCalibrationFragment, launcherFragment);

        tabFragmentPageAdapter = new TabFragmentPageAdapter(getSupportFragmentManager());
        mVp.setAdapter(tabFragmentPageAdapter);
        mVp.setOffscreenPageLimit(1);
        mTlIndicator.setupWithViewPager(mVp);
    }

    private View tab_icon(String name) {
        @SuppressLint("InflateParams")
        View tabView = LayoutInflater.from(this).inflate(R.layout.tab_view_icon_right, null);
        TextView tv = tabView.findViewById(R.id.tabtext);
        tv.setText(name);
        return tabView;
    }

    public class TabFragmentPageAdapter extends FragmentPagerAdapter {

        TabFragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            int size = mFragmentList.size();
            return size;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }

        public View getTabView(Context mContext, int position) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_header, null);
            TextView textView = view.findViewById(R.id.tv_header);
            return view;
        }
    }
}
