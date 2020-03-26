package com.danmo.hotel.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.danmo.hotel.R;
import com.danmo.hotel.base.BaseFragment;
import com.danmo.hotel.base.BaseViewPagerAdapter;
import com.danmo.hotel.base.ViewHolder;
import com.danmo.hotel.fragment.sub.HotGoodsAllFragment;
import com.danmo.hotel.util.Config;
import com.danmo.hotel.widget.menu.HotGoodsAppMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * 辣品
 */

public class HotGoodsFragment extends BaseFragment {
    private TabLayout mTabNav;
    private ViewPager mBaseViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hotgoods_main;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        mTabNav = holder.get(R.id.tab_nav);
        mBaseViewPager = holder.get(R.id.viewpager);
        Toolbar toolbar = holder.get(R.id.toolbar);
        toolbar.setTitle("辣品");
        setHasOptionsMenu(true);
        ((AppCompatActivity) mContext).setSupportActionBar(toolbar);

        BaseViewPagerAdapter adapter = new BaseViewPagerAdapter(mContext, getChildFragmentManager(), getPagers());
        mBaseViewPager.setAdapter(adapter);
        mTabNav.setupWithViewPager(mBaseViewPager);
        mBaseViewPager.setCurrentItem(0, true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.hotgoods_toolbar_menu, menu);
        MenuItem item = menu.findItem(R.id.option_cal);
        HotGoodsAppMenu cartActionProvider = (HotGoodsAppMenu) MenuItemCompat.getActionProvider(item);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private List<BaseViewPagerAdapter.PagerInfo> getPagers() {

        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_tab", "tab_hotgoods");

        List<BaseViewPagerAdapter.PagerInfo> list = new ArrayList<>();

        if (Config.hotgoodsTabTitles.length < 6) {
            return null;
        }
        list.add(new BaseViewPagerAdapter.PagerInfo(Config.hotgoodsTabTitles[0], HotGoodsAllFragment.class,
                bundle));
        list.add(new BaseViewPagerAdapter.PagerInfo(Config.hotgoodsTabTitles[1], HotGoodsAllFragment.class,
                bundle));
        list.add(new BaseViewPagerAdapter.PagerInfo(Config.hotgoodsTabTitles[2], HotGoodsAllFragment.class,
                bundle));
        list.add(new BaseViewPagerAdapter.PagerInfo(Config.hotgoodsTabTitles[3], HotGoodsAllFragment.class,
                bundle));
        list.add(new BaseViewPagerAdapter.PagerInfo(Config.hotgoodsTabTitles[4], HotGoodsAllFragment.class,
                bundle));
        list.add(new BaseViewPagerAdapter.PagerInfo(Config.hotgoodsTabTitles[5], HotGoodsAllFragment.class,
                bundle));

        return list;
    }
}
