package net.quikkly.android.react;

import android.content.Context;
import android.util.Log;
import android.view.ViewParent;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import net.quikkly.android.ScanResultListener;
import net.quikkly.android.ui.ScanFragment;
import net.quikkly.core.ScanResult;

public class QuikklyScanFragment extends ScanFragment implements ScanResultListener {

    private static final String TAG = QuikklyScanFragment.class.getSimpleName();

    public QuikklyScanFragment() {
        setScanResultListener(this);
    }

    @Override
    public void onScanResult(ScanResult scanResult) {
        if(scanResult != null && scanResult.tags != null && scanResult.tags.length > 0) {
            QuikklyScanView view = getScanView();
            Context context = (view != null) ? view.getContext() : null;

            if (context instanceof ReactContext) {
                ReactContext reactContext = (ReactContext) context;
                WritableMap event = Arguments.createMap();
                String value = scanResult.tags[0].getData().toString();
                event.putString("value", value);

                reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(view.getId(), "scanCode", event);
                Log.d(TAG, "Found code " + value);
            } else {
                Log.e(TAG, "Unable to find react context");
            }
        }
    }

    private QuikklyScanView getScanView() {
        ViewParent view = (ViewParent)getView();

        while(view != null) {
            if(view instanceof QuikklyScanView) {
                return (QuikklyScanView)view;
            }

            view = view.getParent();
        }

        return null;
    }
}
