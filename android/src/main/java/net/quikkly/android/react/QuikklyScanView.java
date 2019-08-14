package net.quikkly.android.react;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

public class QuikklyScanView extends RelativeLayout {

    private static final String TAG = "QuikklyScanView";

    private Integer cameraPreviewFit;

    private ThemedReactContext context;

    //private static RelativeLayout wrapper;

    public QuikklyScanView(ThemedReactContext context) {
        super(context);
        this.context = context;
        RelativeLayout wrapper = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.quikkly_scan_view, this, false);
        wrapper.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(wrapper);
    }

    /*
    private synchronized RelativeLayout getRelativeLayout() {
        if(wrapper == null) {
            wrapper = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.quikkly_scan_view, this, false);
        }
        else {
            ((ViewGroup) wrapper.getParent()).removeView(wrapper);
        }
        wrapper.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        return wrapper;
    }
    */
    public void setCameraPreviewFit(Integer cameraPreviewFit) {
        this.cameraPreviewFit = cameraPreviewFit;
        configureFragment();
    }

    public void cleanupView() {
        try {
            QuikklyScanFragment scanFragment =
                (QuikklyScanFragment) this.context.getCurrentActivity().getFragmentManager().findFragmentByTag("quikkly_scan_fragment");
            this.context.getCurrentActivity().getFragmentManager().beginTransaction().remove(scanFragment).commit();
        }
        catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void configureFragment() {
        try {
            QuikklyScanFragment scanFragment =
                    (QuikklyScanFragment) this.context.getCurrentActivity().getFragmentManager().findFragmentByTag("quikkly_scan_fragment");
            if(cameraPreviewFit != null) {
                scanFragment.setCameraPreviewFit(1);
            }
        }
        catch(Exception e) {
            Log.w(TAG,"Unable to configure fragment");
        }
    }
}
