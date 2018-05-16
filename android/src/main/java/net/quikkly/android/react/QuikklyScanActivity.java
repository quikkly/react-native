package net.quikkly.android.react;

import android.app.Activity;
import android.content.Intent;

import net.quikkly.android.ui.ScanActivity;
import net.quikkly.core.ScanResult;
import net.quikkly.core.Tag;

public class QuikklyScanActivity extends ScanActivity {

    public static final String EXTRA_SCAN_VALUE = "value";

    @Override
    public void onScanResult(ScanResult result) {
        if(result != null && !result.isEmpty()) {
            Intent intent = new Intent();
            Tag tag = result.tags[0];

            intent.putExtra(EXTRA_SCAN_VALUE, tag.getData().toString());
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }
}
