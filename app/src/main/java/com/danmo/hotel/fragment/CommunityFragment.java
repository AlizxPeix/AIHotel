package com.danmo.hotel.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.danmo.hotel.R;
import com.danmo.hotel.base.BaseFragment;
import com.danmo.hotel.base.ViewHolder;
import com.danmo.hotel.fragment.sub.CommunityContentFragment;

/**
 * 圈子
 */

public class CommunityFragment extends BaseFragment {
    private FragmentManager fm;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        Toolbar toolbar = holder.get(R.id.toolbar);
        toolbar.setTitle("圈子");
        setHasOptionsMenu(true);
        ((AppCompatActivity) mContext).setSupportActionBar(toolbar);

        CommunityContentFragment fragment = new CommunityContentFragment();
        fm = this.getChildFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.quan_content, fragment);
        ft.show(fragment);
        ft.commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.quan_toolbar_menu, menu);
    }


}
