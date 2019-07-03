package net.quikkly.android.react;

import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.facebook.react.uimanager.ThemedReactContext;

public class QuikklyScanView extends RelativeLayout {

    public QuikklyScanView(ThemedReactContext context) {
        super(context);

        RelativeLayout wrapper = (RelativeLayout)LayoutInflater.from(context).inflate(R.layout.quikkly_scan_view, this, false);

        wrapper.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(wrapper);
    }
}
