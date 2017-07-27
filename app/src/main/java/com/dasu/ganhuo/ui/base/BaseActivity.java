package com.dasu.ganhuo.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dasu.ganhuo.R;
import com.dasu.ganhuo.mode.network.NetBroadcastReceiver;
import com.dasu.ganhuo.mode.network.NetStateListener;
import com.dasu.ganhuo.ui.view.LoadingView;
import com.dasu.ganhuo.utils.NetworkUtils;

import static android.provider.Settings.ACTION_WIRELESS_SETTINGS;

/**
 * Created by dasu on 2017/4/01.
 *
 * BaseActivity
 */
public abstract class BaseActivity extends AppCompatActivity implements NetStateListener{

    private View mNoNetworkTipView;
    protected Context mContext;
    private View mLoadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        ActivityStack.getInstance().pushActivity(this);
        NetBroadcastReceiver.addListener(this);
        mLoadingView = new LoadingView(mContext);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.getInstance().popActivity(this);
        NetBroadcastReceiver.removeListener(this);
    }

    protected void showLoadingView(ViewGroup viewGroup) {
        ViewGroup mainGroup = viewGroup;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        removeLoadingView();
        if (mainGroup != null) {
            mainGroup.addView(mLoadingView, 0, params);
        }
    }

    protected void removeLoadingView() {
        if (mLoadingView.getParent() != null) {
            ViewGroup parent = (ViewGroup) mLoadingView.getParent();
            parent.removeView(mLoadingView);
        }
    }

    @Override
    public void onNetChanged(boolean isConnected) {
        if (mNoNetworkTipView == null) {
            return;
        }
        if (isConnected) {
            mNoNetworkTipView.setVisibility(View.GONE);
        } else {
            mNoNetworkTipView.setVisibility(View.VISIBLE);
        }
    }

}
