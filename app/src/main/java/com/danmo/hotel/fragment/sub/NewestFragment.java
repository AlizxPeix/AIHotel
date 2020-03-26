package com.danmo.hotel.fragment.sub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.danmo.commonapi.CommonApi;
import com.danmo.commonapi.base.Constant;
import com.danmo.commonapi.bean.newest.NewestItem;
import com.danmo.commonapi.bean.newest.NewestTopNode;
import com.danmo.commonapi.event.newest.GetNewestEvent;
import com.danmo.commonutil.recyclerview.adapter.multitype.HeaderFooterAdapter;
import com.danmo.commonutil.recyclerview.layoutmanager.SpeedyLinearLayoutManager;
import com.danmo.hotel.fragment.refresh.RefreshRecyclerFragment;
import com.danmo.hotel.provider.NewestBannerProvider;
import com.danmo.hotel.provider.NewestListProvider;

/**
 * 资讯viewpager的填充首页面:最新
 */
public class NewestFragment extends RefreshRecyclerFragment<NewestTopNode, GetNewestEvent> {

    private Boolean isFirst = true;

    @Override
    public void initData(HeaderFooterAdapter adapter) {
        setLoadMoreEnable(true);
        loadHeader();
    }

    @Override
    protected void setAdapterRegister(Context context, RecyclerView recyclerView, HeaderFooterAdapter adapter) {
        adapter.register(NewestItem.class, new NewestListProvider(getContext()));
    }

    @NonNull
    @Override
    protected RecyclerView.LayoutManager getRecyclerViewLayoutManager() {
        return new SpeedyLinearLayoutManager(getContext());
    }

    @NonNull
    @Override
    protected String request(int offset, int limit) {
        if (isFirst) {
            isFirst = false;
            return CommonApi.getSingleInstance().getNewestList(Constant.NESLIST_URL);
        } else {
            return CommonApi.getSingleInstance().getNewestList(Constant.NESLIST_URL);
        }
    }

    @NonNull
    @Override
    protected String requestHeader() {
        return CommonApi.getSingleInstance().getNewestBannerList(Constant.BANNER_URL);
    }

    @NonNull
    @Override
    protected String requestMiddle() {
        return null;
    }

    @Override
    protected void onLoadHeader(GetNewestEvent event, HeaderFooterAdapter adapter) {
        if (event.getBean().newest != null && event.getBean().newest.item.size() > 0) {
            mAdapter.registerHeader(event.getBean().newest, new NewestBannerProvider(mContext));//添加幻灯片
        }
    }

    @Override
    protected void onLoadMiddle(GetNewestEvent event, HeaderFooterAdapter adapter) {

    }

    @Override
    protected void onRefresh(GetNewestEvent event, HeaderFooterAdapter adapter) {
        adapter.clearDatas();
        adapter.addDatas((event.getBean()).newest.item);
    }

    @Override
    protected void onLoadMore(GetNewestEvent event, HeaderFooterAdapter adapter) {
        adapter.addDatas(event.getBean().newest.item);
    }

    @Override
    protected void onError(GetNewestEvent event, String postType) {
        if (postType.equals(POST_LOAD_MORE)) {
            toast("加载更多失败");
        } else if (postType.equals(POST_REFRESH)) {
            toast("刷新数据失败");
        }
    }
}
