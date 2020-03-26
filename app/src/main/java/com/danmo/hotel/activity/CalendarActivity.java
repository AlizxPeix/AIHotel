package com.danmo.hotel.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.danmo.hotel.R;
import com.danmo.hotel.base.BaseActivity;
import com.danmo.hotel.base.BaseViewPagerAdapter;
import com.danmo.hotel.fragment.sub.calendar.NewsEventFragment;
import com.danmo.hotel.fragment.sub.calendar.NewsPushFragment;
import com.danmo.hotel.fragment.sub.calendar.ReadHistoryFragment;
import com.danmo.hotel.fragment.sub.calendar.TodayNewsFragment;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.List;


public class CalendarActivity extends BaseActivity implements CalendarView.OnDateSelectedListener,
        CalendarView.OnYearChangeListener {

    private Toolbar mToolbar;
    private CalendarView mCalendarView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calendar_layout;
    }

    @Override
    protected void initViews() {
        super.initViews();
        mToolbar = mRootView.findViewById(R.id.canlendar_toolbar);
        setSupportActionBar(mToolbar);
        mCalendarView = findViewById(R.id.calendarView);
        mTabLayout = mRootView.findViewById(R.id.calendar_tab_nav);
        mViewPager = mRootView.findViewById(R.id.calendar_viewpager);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarActivity.this.onBackPressed();
            }
        });
        mToolbar.setTitle(mCalendarView.getCurYear() + "年" + mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");


        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnDateSelectedListener(this);

//        List<Calendar> schemes = new ArrayList<>();
//        int year = mCalendarView.getCurYear();
//        int month = mCalendarView.getCurMonth();
//        schemes.add(getSchemeCalendar(year, month, 3, 0, "假"));
//        schemes.add(getSchemeCalendar(year, month, 6, 0, "事"));
//        schemes.add(getSchemeCalendar(year, month, 9, 0, "议"));
//        schemes.add(getSchemeCalendar(year, month, 13, 0, "记"));
//        schemes.add(getSchemeCalendar(year, month, 14, 0, "记"));
//        schemes.add(getSchemeCalendar(year, month, 15, 0, "假"));
//        schemes.add(getSchemeCalendar(year, month, 18, 0, "记"));
//        schemes.add(getSchemeCalendar(year, month, 25, 0, "假"));
//        mCalendarView.setSchemeDate(schemes);

        BaseViewPagerAdapter adapter = new BaseViewPagerAdapter(this, getSupportFragmentManager(), getPagers());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(0, true);
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, CalendarActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onDateSelected(Calendar calendar) {

    }

    @Override
    public void onYearChange(int year) {

    }

    private List<BaseViewPagerAdapter.PagerInfo> getPagers() {

        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_tab", "tab_calendar");

        List<BaseViewPagerAdapter.PagerInfo> list = new ArrayList<>();
        list.add(new BaseViewPagerAdapter.PagerInfo("要闻推送", NewsPushFragment.class,
                bundle));
        list.add(new BaseViewPagerAdapter.PagerInfo("当日新闻", TodayNewsFragment.class,
                bundle));
        list.add(new BaseViewPagerAdapter.PagerInfo("事件", NewsEventFragment.class,
                bundle));
        list.add(new BaseViewPagerAdapter.PagerInfo("浏览历史", ReadHistoryFragment.class,
                bundle));
        return list;

    }
}
