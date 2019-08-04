package net.quikkly.android.react;

import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

public class QuikklyScanView extends RelativeLayout {

    private static final String TAG = "QuikklyScanView";

    private Integer cameraPreviewFit;

    private ThemedReactContext context;

    public QuikklyScanView(ThemedReactContext context) {
        super(context);
        this.context = context;
        RelativeLayout wrapper = (RelativeLayout)LayoutInflater.from(context).inflate(R.layout.quikkly_scan_view, this, false);

        wrapper.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(wrapper);
    }

    public void setCameraPreviewFit(Integer cameraPreviewFit) {
        this.cameraPreviewFit = cameraPreviewFit;
        configureFragment();
    }

    private void configureFragment() {
        try {
            QuikklyScanFragment scanFragment =
                    (QuikklyScanFragment) this.context.getCurrentActivity().getFragmentManager().findFragmentByTag("quikkly_scan_fragment");
            if(cameraPreviewFit != null) {
                scanFragment.setCameraPreviewFit(cameraPreviewFit);
            }
        }
        catch(Exception e) {
            Log.w(TAG,"Unable to configure fragment");
        }
    }
}
