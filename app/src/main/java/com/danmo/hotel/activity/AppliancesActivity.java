package com.danmo.hotel.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.danmo.hotel.R;
import com.danmo.hotel.base.BaseActivity;


public class AppliancesActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_Kongtiao,iv_Dianshi,iv_Yugan,iv_Fan;
    private Toolbar mToolbar;
    private static int REQUEST_CODE_LOGIN = 1;
    @Override
    protected int getLayoutId() {
        return R.layout.userinfo_control_appliances;
    }

    @Override
    protected void initViews() {
        super.initViews();
        iv_Kongtiao = findViewById(R.id.iv_kongtiao);
        iv_Dianshi = findViewById(R.id.iv_dianshi);
        iv_Yugan = findViewById(R.id.iv_yugan);
        iv_Fan = findViewById(R.id.iv_fan);
        mToolbar = mRootView.findViewById(R.id.flexible_toolbar);
        setSupportActionBar(mToolbar);

        iv_Kongtiao.setOnClickListener(this);
        iv_Dianshi.setOnClickListener(this);
        iv_Yugan.setOnClickListener(this);
        iv_Fan.setOnClickListener(this);

    }

    public static void startActivity(Fragment fragment) {
        Intent intent = new Intent(fragment.getActivity(), AppliancesActivity.class);
        fragment.startActivityForResult(intent, REQUEST_CODE_LOGIN);
    }
    @Override
    public void onClick(View v) {
        if (v == iv_Dianshi) {
            //startActivity(new Intent(this,CameraActivity.class));
        } else if (v == iv_Kongtiao) {
            //startActivity(new Intent(this, FaceAttrPreviewActivity.class));
        } else if (v == iv_Yugan) {
            //startActivity(new Intent(this, FaceAttrPreviewActivity.class));
        } else if (v == iv_Fan) {
            //startActivity(new Intent(this, FaceAttrPreviewActivity.class));
        } else if (v == mToolbar) {
            AppliancesActivity.this.onBackPressed();
        }

    }


}
