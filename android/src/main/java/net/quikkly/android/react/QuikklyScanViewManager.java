package net.quikkly.android.react;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import javax.annotation.Nonnull;

public class QuikklyScanViewManager extends SimpleViewManager<QuikklyScanView> {

    private static final String REACT_CLASS = "QuikklyScanView";

    @Nonnull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Nonnull
    @Override
    protected QuikklyScanView createViewInstance(@Nonnull ThemedReactContext context) {
        return new QuikklyScanView(context);
    }
}
