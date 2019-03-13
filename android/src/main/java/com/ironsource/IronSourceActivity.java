package com.ironsource;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ironsource.adapters.supersonicads.SupersonicConfig;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.OfferwallListener;

import java.util.HashMap;
import java.util.Map;

public class IronSourceActivity extends Activity implements OfferwallListener {

    private final String TAG = "IronSourceActivity";
    private String APP_KEY = "";
    private String USER_ID = "";
    private String USER_IP = "";
    private String SESSION_ID = "";
    private String TIMESTAMP = "";

    private ProgressBar progressBar;
    public static final String appKey = "AppKey";
    public static final String userId = "userId";
    public static final String userIp = "userIp";
    public static final String sessionId = "sessionId";
    public static final String timestamp = "timestamp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        if (getIntent().hasExtra(appKey) && getIntent().hasExtra(userId)) {
            APP_KEY = getIntent().getStringExtra(appKey);
            USER_ID = getIntent().getStringExtra(userId);
            USER_IP = getIntent().getStringExtra(userIp);
            SESSION_ID = getIntent().getStringExtra(sessionId);
            TIMESTAMP = getIntent().getStringExtra(timestamp);

            //IntegrationHelper.validateIntegration(this);
            initUIElements();
            initIronSource(APP_KEY, USER_ID, USER_IP, SESSION_ID, TIMESTAMP);

            if (IronSource.isOfferwallAvailable()) {
                IronSource.showOfferwall();
            }
        } else {
            Toast.makeText(this, "Something went wong", Toast.LENGTH_SHORT).show();
        }
    }

    private void initIronSource(String appKey, String userId, String ip, String sessionId, String timestamp) {
        IronSource.setOfferwallListener(this);
        IronSource.setUserId(userId);
        setCustomParams(ip, sessionId, timestamp);
        IronSource.init(this, appKey);
    }

    private void setCustomParams(String ip, String sessionId, String timestamp) {
        Map<String, String> params = new HashMap<>();
        params.put("client_session_ip", ip);
        params.put("session_id", sessionId);
        params.put("timestamp", timestamp);
        SupersonicConfig.getConfigObj().setOfferwallCustomParams(params);
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