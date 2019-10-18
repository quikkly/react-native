package net.quikkly.android.react;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.facebook.react.uimanager.ThemedReactContext;

public class QuikklyScanView extends RelativeLayout {

    private static final String TAG = "QuikklyScanView";

    private Integer cameraPreviewFit;

    private ThemedReactContext context;

    public QuikklyScanView(ThemedReactContext context) {
        super(context);
        this.context = context;
        RelativeLayout wrapper = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.quikkly_scan_view, this, false);
        wrapper.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(wrapper);
    }

    public void setCameraPreviewFit(Integer cameraPreviewFit) {
        this.cameraPreviewFit = cameraPreviewFit;
        configureFragment();
    }

    public void cleanupView() {
        try {
            QuikklyScanFragment scanFragment = getQuikklyScanFragment();
            Activity activity = context.getCurrentActivity();
            if (activity != null && scanFragment != null) {
                activity.getFragmentManager()
                        .beginTransaction()
                            .remove(scanFragment)
                        .commit();
            }
        }
        catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void configureFragment() {
        try {
            QuikklyScanFragment scanFragment = getQuikklyScanFragment();
            if(scanFragment != null && cameraPreviewFit != null) {
                scanFragment.setCameraPreviewFit(cameraPreviewFit);
            }
        }
        catch(Exception e) {
            Log.w(TAG,"Unable to configure fragment");
        }
    }

    private QuikklyScanFragment getQuikklyScanFragment() {
        Activity activity = context.getCurrentActivity();
        if(activity != null) {
            return (QuikklyScanFragment) activity
                    .getFragmentManager()
                    .findFragmentByTag("quikkly_react_scan_fragment");
        }
        return null;
    }
}
