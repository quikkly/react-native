package net.quikkly.android.react;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.NoSuchKeyException;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;

import net.quikkly.android.Quikkly;
import net.quikkly.core.SkinBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class QuikklyReactModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    private static final int SCAN_ACTIVITY_REQUEST_CODE = 46372;
    private static final String NAME = "QuikklyManager";
    private static final String KEY_VALUE = "value";
    private static final String KEY_TEMPLATE = "template";
    private static final String KEY_SKIN = "skin";
    private static final String KEY_BACKGROUND_COLOR = "backgroundColor";
    private static final String KEY_BORDER_COLOR = "borderColor";
    private static final String KEY_DATA_COLOR = "dataColor";
    private static final String KEY_MASK_COLOR = "maskColor";
    private static final String KEY_OVERLAY_COLOR = "overlayColor";
    private static final String KEY_IMAGE_FILE = "imageFile";
    private static final String KEY_VERSION = "VERSION";
    private static final String TEMPLATE_DEFAULT = "template0001style1";

    private static String getString(ReadableMap options, String key) {
        try {
            return options.getString(key);
        } catch(NoSuchKeyException e) {
            return null;
        }
    }

    private static String[] getStrings(ReadableMap options, String key) {
        if(options.hasKey(key)) {
            // if a string is passed in then return an array
            // with a single value
            if(options.getType(key) == ReadableType.String) {
                return new String[] {options.getString(key)};
            }
            // if its an array then return a new string array
            else if(options.getType(key) == ReadableType.Array){
                ReadableArray array = options.getArray(key);
                if (array != null) {
                    String[] ret = new String[array.size()];
                    for (int i = 0; i < array.size(); i++) {
                        ret[i] = array.getString(i);
                    }
                    return ret;
                }
            }
        }
        return null;
    }

    private Promise pendingPromise;

    public QuikklyReactModule(ReactApplicationContext reactContext) {
        super(reactContext);

        reactContext.addActivityEventListener(this);

        if(!Quikkly.isConfigured()) {
            Quikkly.configureInstance(reactContext, 2, 30);
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Map<String, Object> getConstants() {
        Map<String, Object> constants = new HashMap<>();

        constants.put(KEY_VERSION, Quikkly.versionString());

        return constants;
    }

    @ReactMethod
    public void createImage(ReadableMap options, Promise promise) {
        String value = (options.getType(KEY_VALUE) == ReadableType.Number) ?
                BigDecimal.valueOf(options.getDouble(KEY_VALUE)).toBigInteger().toString() : options.getString(KEY_VALUE);
        String template = getString(options, KEY_TEMPLATE);
        SkinBuilder skinBuilder = new SkinBuilder();

        if(value == null) {
            throw new IllegalArgumentException("'value' must be a number");
        }

        if(template == null) {
            template = TEMPLATE_DEFAULT;
        }

        try {
            ReadableMap skinOptions = options.getMap(KEY_SKIN);

            if(skinOptions != null) {
                String parameter = getString(skinOptions, KEY_BACKGROUND_COLOR);

                if(parameter != null) {
                    skinBuilder.setBackgroundColor(parameter);
                }

                parameter = getString(skinOptions, KEY_BORDER_COLOR);

                if(parameter != null) {
                    skinBuilder.setBorderColor(parameter);
                }

                String[] parameters = getStrings(skinOptions, KEY_DATA_COLOR);

                if(parameters != null) {
                    skinBuilder.setDataColors(parameters);
                }

                parameter = getString(skinOptions, KEY_MASK_COLOR);

                if(parameter != null) {
                    skinBuilder.setMaskColor(parameter);
                }

                parameter = getString(skinOptions, KEY_OVERLAY_COLOR);

                if(parameter != null) {
                    skinBuilder.setOverlayColor(parameter);
                }

                parameter = getString(skinOptions, KEY_IMAGE_FILE);

                if(parameter != null) {
                    skinBuilder.setImageUrl(parameter);
                }
            }
        } catch(NoSuchKeyException e) {
            // Do nothing
        }

        try {
            promise.resolve(Quikkly.getInstance().generateSvg(template, new BigInteger(value), skinBuilder.build()));
        } catch(Exception e) {
            e.printStackTrace();
            promise.reject("QuikklyUnknown", e.getMessage());
        }
    }

    @ReactMethod
    public void scanForResult(ReadableMap options, Promise promise) {
        Activity currentActivity = getCurrentActivity();

        if(currentActivity == null) {
            promise.reject("QuikklyNoContext", "Unable to get current activity");
            return;
        }

        pendingPromise = promise;

        try {
            Intent intent = new Intent(currentActivity, QuikklyScanActivity.class);

            currentActivity.startActivityForResult(intent, SCAN_ACTIVITY_REQUEST_CODE);
        } catch(Exception e) {
            pendingPromise.reject("QuikklyInvalidContext", e.getMessage());
            pendingPromise = null;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SCAN_ACTIVITY_REQUEST_CODE) {
            if(pendingPromise != null) {
                try {
                    switch (resultCode) {
                        case Activity.RESULT_OK:
                            Bundle extras = (data != null) ? data.getExtras() : null;
                            WritableMap result = Arguments.createMap();

                            if(extras != null) {
                                result.putString("value", extras.getString(QuikklyScanActivity.EXTRA_SCAN_VALUE));
                            }

                            pendingPromise.resolve(result);
                            break;
                        case Activity.RESULT_CANCELED:
                            pendingPromise.reject("QuikklyCancelled", "User cancelled UI");
                            break;
                        default:
                            pendingPromise.reject("QuikklyUnknown", "UI closed unexpectedly (" + resultCode + ")");
                            break;
                    }
                } finally {
                    pendingPromise = null;
                }
            }
        }
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        onActivityResult(requestCode, resultCode, data);
    }

    public void onNewIntent(Intent intent) {
    }
}
