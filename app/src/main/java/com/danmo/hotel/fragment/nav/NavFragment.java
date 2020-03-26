package com.danmo.hotel.fragment.nav;

import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.danmo.hotel.R;
import com.danmo.hotel.base.BaseFragment;
import com.danmo.hotel.base.ViewHolder;
import com.danmo.hotel.fragment.UserFragment;
import com.danmo.hotel.fragment.applicationFragment;
import com.danmo.hotel.util.Config;

import java.util.List;

/**
 * 底部导航栏内容
 */
public class NavFragment extends BaseFragment implements View.OnClickListener {
//    private NavigationButton mNavNews;
//    private NavigationButton mNavTweet;
//    private NavigationButton mNavExplore;
    private NavigationButton mNavApplication;
    private NavigationButton mNavMe;

    private int mContainerId;
    private NavigationButton mCurrentNavButton;
    private OnNavigationReselectListener mOnNavigationReselectListener;

    public NavFragment() {
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nav;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
//        mNavNews = holder.get(R.id.nav_item_news);
//        mNavTweet = holder.get(R.id.nav_item_tweet);
//        mNavExplore = holder.get(R.id.nav_item_explore);
        mNavApplication = holder.get(R.id.nav_item_application);
        mNavMe = holder.get(R.id.nav_item_me);

//        holder.setOnClickListener(this, R.id.nav_item_news);
//        holder.setOnClickListener(this, R.id.nav_item_tweet);
//        holder.setOnClickListener(this, R.id.nav_item_explore);
        holder.setOnClickListener(this, R.id.nav_item_application);
        holder.setOnClickListener(this, R.id.nav_item_me);

        ShapeDrawable lineDrawable = new ShapeDrawable(new BorderShape(new RectF(0, 1, 0, 0)));
        lineDrawable.getPaint().setColor(getResources().getColor(R.color.list_divider_color));
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{
                new ColorDrawable(getResources().getColor(R.color.white)),
                lineDrawable
        });
        root.setBackgroundDrawable(layerDrawable);

        if (Config.navigationTitles.length < 2) {
            return;
        }
//        mNavNews.init(R.drawable.tab_icon_news,
//                Config.navigationTitles[0],
//                NewsFragment.class);
//
//        mNavTweet.init(R.drawable.tab_icon_hotgoods,
//                Config.navigationTitles[1],
//                HotGoodsFragment.class);
//
//        mNavExplore.init(R.drawable.tab_icon_quan,
//                Config.navigationTitles[2],
//                CommunityFragment.class);
        mNavApplication.init(R.drawable.tab_icon_application,
                Config.navigationTitles[0],
                applicationFragment.class);

        mNavMe.init(R.drawable.tab_icon_me,
                Config.navigationTitles[1],
                UserFragment.class);

        clearOldFragment();
        doSelect(mNavApplication);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof NavigationButton) {
            NavigationButton nav = (NavigationButton) v;
            doSelect(nav);
        }
//        } else if (v.getId() == R.id.nav_item_tweet_pub) {
//            TweetPublishActivity.show(getContext(), mRoot.findViewById(R.id.nav_item_tweet_pub));
//        }
    }

    public void setup(int contentId, OnNavigationReselectListener listener) {
        mContainerId = contentId;
        mOnNavigationReselectListener = listener;
    }

    public void select(int index) {
        if (mNavMe != null)
            doSelect(mNavMe);
    }

    private void clearOldFragment() {

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
        if (transaction == null || fragments == null || fragments.size() == 0)
            return;
        boolean doCommit = false;
        for (Fragment fragment : fragments) {
            if (fragment != this && fragment != null) {
                transaction.remove(fragment);
                doCommit = true;
            }
        }
        if (doCommit)
            transaction.commitNow();
    }

    private void doSelect(NavigationButton newNavButton) {
        NavigationButton oldNavButton = null;
        if (mCurrentNavButton != null) {
            oldNavButton = mCurrentNavButton;
            if (oldNavButton == newNavButton) {
                onReselect(oldNavButton);
                return;
            }
            oldNavButton.setSelected(false);
        }
        newNavButton.setSelected(true);
        doTabChanged(oldNavButton, newNavButton);
        mCurrentNavButton = newNavButton;
    }

    private void doTabChanged(NavigationButton oldNavButton, NavigationButton newNavButton) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        if (oldNavButton != null) {
            if (oldNavButton.getFragment() != null) {
                ft.detach(oldNavButton.getFragment());
            }
        }
        if (newNavButton != null) {
            if (newNavButton.getFragment() == null) {
                Fragment fragment = Fragment.instantiate(mContext,
                        newNavButton.getClx().getName(), null);
                ft.add(mContainerId, fragment, newNavButton.getTag());
                newNavButton.setFragment(fragment);
            } else {
                ft.attach(newNavButton.getFragment());
            }
        }
        ft.commit();
    }

    private void onReselect(NavigationButton navigationButton) {
        OnNavigationReselectListener listener = mOnNavigationReselectListener;
        if (listener != null) {
            listener.onReselect(navigationButton);
        }
    }

    public interface OnNavigationReselectListener {
        void onReselect(NavigationButton navigationButton);
    }
}
