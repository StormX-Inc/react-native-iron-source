package com.ironsource;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ironsource.adapters.supersonicads.SupersonicConfig;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.OfferwallListener;

public class IronSourceActivity extends Activity implements OfferwallListener {

    private final String TAG = "IronSourceActivity";
    private String APP_KEY = "4ea90fad";
    private String USER_ID = "userId";

    private Placement mPlacement;

    private FrameLayout mBannerParentLayout;
    private ProgressBar progressBar;
    private IronSourceBannerLayout mIronSourceBannerLayout;
    private final String  AppKey="AppKey";
    private final String userId="userId";
    private final String AdsType="AdsType";
    private boolean  isFinished = false;
    private final String IS_REWARDED_VIDEO="IS_REWARDED_VIDEO";
    private final String IS_OFFERWALL="offerwall";
    private final String IS_BANNER="IS_BANNER";
    private final String IS_INTERSTITIAL="IS_INTERSTITIAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        if(getIntent().hasExtra(AppKey) && getIntent().hasExtra(userId)){
            APP_KEY=getIntent().getStringExtra(AppKey);
            USER_ID=getIntent().getStringExtra(userId);

            //IntegrationHelper.validateIntegration(this);
            initUIElements();
            initIronSource(APP_KEY, USER_ID);

            if (IronSource.isOfferwallAvailable()) {
                IronSource.showOfferwall();
            }
        }else {
           Toast.makeText(this,"Something went wong",Toast.LENGTH_SHORT).show();
        }
    }

    private void initIronSource(String appKey, String userId) {
        IronSource.setOfferwallListener(this);
        IronSource.setUserId(userId);
        IronSource.init(this, appKey);
        IronSource.shouldTrackNetworkState(this, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IronSource.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        IronSource.onPause(this);
    }

    /**
     * initialize the UI elements of the activity
     */
    private void initUIElements() {
        mBannerParentLayout = (FrameLayout) findViewById(R.id.banner_footer);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    // --------- IronSource Offerwall Listener ---------

    @Override
    public void onOfferwallAvailable(boolean available) {
        Log.d(TAG, "onOfferwallOpened");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });

        if (available) {
            IronSource.showOfferwall();
        }
    }

    @Override
    public void onOfferwallOpened() {
        Log.d(TAG, "onOfferwallOpened");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onOfferwallShowFailed(IronSourceError ironSourceError) {
        // IronSourceError.getErrorCode();
        // IronSourceError.getErrorMessage();
        Log.d(TAG, "onOfferwallShowFailed" + " " + ironSourceError);
    }

    @Override
    public boolean onOfferwallAdCredited(int credits, int totalCredits, boolean totalCreditsFlag) {
        Log.d(TAG, "onOfferwallAdCredited" + " credits:" + credits + " totalCredits:" + totalCredits + " totalCreditsFlag:" + totalCreditsFlag);
        isFinished = true;
        IronSource.shouldTrackNetworkState(IronSourceActivity.this,false);
        finish();
        return false;
    }

    @Override
    public void onGetOfferwallCreditsFailed(IronSourceError ironSourceError) {
        // IronSourceError.getErrorCode();
        // IronSourceError.getErrorMessage();
        Log.d(TAG, "onGetOfferwallCreditsFailed" + " " + ironSourceError);
    }

    @Override
    public void onOfferwallClosed() {
        Log.d(TAG, "onOfferwallClosed");
        finish();
    }

}
