package cm.permission;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;



import java.util.List;

public class PermissionDialog {

    private AlertDialog mPermissionDialog;

    public PermissionDialog(final Context activityContext) {
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(activityContext)
                    .setCancelable(false)
                    .setTitle("友情提示")
                    .setMessage(activityContext.getString(R.string.app_name)
                            + "运行缺少权限。请点击\"设置\"-\"权限\"打开所需权限。")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (listener != null) {
                                listener.onCancel();
                            }
                        }
                    }).setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            goToInstalledAppDetails(activityContext);
                            dialog.dismiss();
                        }
                    }).create();
        }
    }

    public void setMessage(List<PermissionInfo> list) {
        StringBuilder message = new StringBuilder(mPermissionDialog.getContext().getString(R.string.app_name) + "运行缺少");
        for (int i = 0; i < list.size(); i++) {
            PermissionInfo permissionInfo = list.get(i);
            switch (permissionInfo.getName()) {
                case Manifest.permission.READ_EXTERNAL_STORAGE:
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    message.append("存储空间");
                    break;
                case Manifest.permission.SEND_SMS:
                case Manifest.permission.RECEIVE_SMS:
                case Manifest.permission.READ_SMS:
                case Manifest.permission.RECEIVE_WAP_PUSH:
                case Manifest.permission.RECEIVE_MMS:
                    message.append("短信");
                    break;
                case Manifest.permission.BODY_SENSORS:
                    message.append("身体传感器");
                    break;
                case Manifest.permission.READ_PHONE_STATE:
                case Manifest.permission.CALL_PHONE:
                case Manifest.permission.READ_CALL_LOG:
                case Manifest.permission.WRITE_CALL_LOG:
                case Manifest.permission.USE_SIP:
                case Manifest.permission.PROCESS_OUTGOING_CALLS:
                    message.append("电话");
                    break;
                case Manifest.permission.RECORD_AUDIO:
                    message.append("麦克风");
                    break;
                case Manifest.permission.ACCESS_FINE_LOCATION:
                case Manifest.permission.ACCESS_COARSE_LOCATION:
                    message.append("位置信息");
                    break;
                case Manifest.permission.READ_CONTACTS:
                case Manifest.permission.WRITE_CONTACTS:
                case Manifest.permission.GET_ACCOUNTS:
                    message.append("通讯录");
                    break;
                case Manifest.permission.CAMERA:
                    message.append("相机");
                    break;
                case Manifest.permission.READ_CALENDAR:
                case Manifest.permission.WRITE_CALENDAR:
                    message.append("日历");
                    break;
            }
            if (i != list.size() - 1) {
                message.append("、");
            }
        }
        message.append("权限。请点击\"设置\"-\"权限\"打开所需权限。");
        mPermissionDialog.setMessage(message.toString());
    }

    public void show() {
        if (mPermissionDialog != null && !mPermissionDialog.isShowing()) {
            mPermissionDialog.show();
        }
    }

    public void setOnCancelListener(OnCancelListener listener) {
        this.listener = listener;
    }

    private OnCancelListener listener;

    public interface OnCancelListener {
        void onCancel();
    }

    /**
     * 打开已安装应用的详情
     */
    public static void goToInstalledAppDetails(Context context) {
        Intent intent = new Intent();
        int sdkVersion = Build.VERSION.SDK_INT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra((sdkVersion == Build.VERSION_CODES.FROYO ? "pkg"
                    : "com.android.settings.ApplicationPkgName"), context.getPackageName());
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
