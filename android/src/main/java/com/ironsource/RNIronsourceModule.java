package com.ironsource;

import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RNIronsourceModule extends ReactContextBaseJavaModule {

    public RNIronsourceModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNIronsource";
    }

    @ReactMethod
    public void startIronSource(String appkey, String userid, String adtype, String ip, String session, String timestamp) {
        ReactApplicationContext context = getReactApplicationContext();
        Intent intent = new Intent(context, IronSourceActivity.class);
        intent.putExtra(IronSourceActivity.appKey, appkey);
        intent.putExtra(IronSourceActivity.userId, userid);
        intent.putExtra(IronSourceActivity.userIp, ip);
        intent.putExtra(IronSourceActivity.sessionId, session);
        intent.putExtra(IronSourceActivity.timestamp, timestamp);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
