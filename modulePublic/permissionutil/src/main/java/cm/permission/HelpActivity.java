package cm.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.HashMap;
import java.util.Map;

public class HelpActivity extends Activity {

    private static Map<String, PermissionUtil> sPermissionUtilMap = new HashMap<>();
    private String mTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            handleIntent(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void handleIntent(Intent intent) {
        String[] permissions = intent.getStringArrayExtra(PermissionUtil.KEY_PERMISSION_LIST);
        mTag = intent.getStringExtra(PermissionUtil.KEY_PERMISSION_TAG);
        int requestCode = intent.getIntExtra("requestCode", PermissionUtil.PERMISSION_REQUEST_CODE);
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtil permissionUtil = sPermissionUtilMap.get(mTag);
        if (permissionUtil != null) {
            permissionUtil.onRequestPermissionResult(this, requestCode, permissions, grantResults);
            sPermissionUtilMap.remove(mTag);
        }
        finish();
    }

    public static void addPermissionUtil(String tag, PermissionUtil permissionUtil) {
        sPermissionUtilMap.put(tag, permissionUtil);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
