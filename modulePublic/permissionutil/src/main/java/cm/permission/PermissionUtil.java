package cm.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.Looper;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Android6.0权限申请工具类
 */
public class PermissionUtil {

    final static String KEY_PERMISSION_TAG = "key_permission_tag";
    final static String KEY_PERMISSION_LIST = "key_permission_list";
    final static int PERMISSION_REQUEST_CODE = 201;//请求权限的请求码

    private final static int PERMISSION_GRANTED = 1;//同意
    private final static int PERMISSION_RATIONAL = 2;//拒绝,下次询问
    private final static int PERMISSION_DENIED = 3;//拒绝,下次不询问

    //会重新申请的权限列表
    private List<PermissionInfo> mPermissionListNeedReq = new ArrayList<>();
    //被拒绝的权限列表
    private List<PermissionInfo> mPermissionListDenied = new ArrayList<>();
    //被接受的权限列表
    private List<PermissionInfo> mPermissionListAccepted = new ArrayList<>();

    private List<String> mPermissions = new ArrayList<>();
    private ResultCallBack mResultCallBack;
    private Activity mActivity;

    public static Builder with(Activity activity) {
        return new Builder(activity);
    }

    public static Builder with(Context actContext) {
        try {
            Activity activity = (Activity) actContext;
            return new Builder(activity);
        } catch (Exception e) {
            throw new RuntimeException("actContext must be a activity");
        }
    }

    public static class Builder {

        private Activity activity;
        private PermissionUtil permissionUtil;
        private List<String> permissionsList = new ArrayList<>();

        Builder(Activity activity) {
            this.activity = activity;
            permissionUtil = new PermissionUtil();
        }

        public Builder add(String permissions) {
            this.permissionsList.add(permissions);
            return this;
        }

        public Builder request() {
            return request(null);
        }

        public Builder request(ResultCallBack callBack) {
            permissionUtil.request(activity, permissionsList, callBack);
            return this;
        }

    }

    /**
     * 用于activity中请求权限
     */
    private void request(Activity activity, List<String> permissions, ResultCallBack callBack) {
        this.mActivity = activity;
        this.mResultCallBack = callBack;
        if (!checkSituation(permissions)) {
            return;
        }
        this.request(activity, permissions);
    }

    /**
     * 检查环境是否满足申请权限的要求
     */
    private boolean checkSituation(List<String> permissions) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("request permission only can run in MainThread!");
        }
        if (permissions.size() == 0) {
            return false;
        }
        //版本小于23的时候直接视为已经同意
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (mResultCallBack != null) {
                mResultCallBack.onGranted(permissions.toArray(new String[]{}));
                mResultCallBack.onGrantedAll();
            }
            return false;
        }
        return true;
    }

    /**
     * 请求权限核心方法
     */
    private void request(Activity activity, List<String> permissions) {
        this.mPermissions = permissions;
        if (needToRequest(activity)) {
            //对还没有的权限进行申请
            requestPermissions(activity);
        } else {
            if (mPermissionListDenied.isEmpty() && mPermissionListNeedReq.isEmpty()) {
                if (mResultCallBack != null) {
                    onGranted(mPermissionListAccepted);
                    mResultCallBack.onGrantedAll();
                }
            }
        }
    }

    /**
     * 检查是否需要申请权限
     */
    @TargetApi(Build.VERSION_CODES.M)
    private boolean needToRequest(Activity activity) {
        checkMultiPermissions(activity, mPermissions);
        if (mPermissionListNeedReq.size() > 0 || mPermissionListDenied.size() > 0) {
            mPermissions.clear();
            for (PermissionInfo permissionInfo : mPermissionListNeedReq) {
                mPermissions.add(permissionInfo.getName());
            }
            for (PermissionInfo permissionInfo : mPermissionListDenied) {
                mPermissions.add(permissionInfo.getName());
            }
            return true;
        }
        return false;
    }

    /**
     * 检查多个权限的状态,不会进行权限的申请.(当应用第一次安装的时候,不会有rational的值,此时返回均是denied)
     */
    private void checkMultiPermissions(Activity activity, List<String> permissions) {
        this.mPermissionListNeedReq.clear();
        this.mPermissionListDenied.clear();
        this.mPermissionListAccepted.clear();

        for (String permission : permissions) {

            int result;

            switch (Build.MANUFACTURER) {
                //适配小米权限申请
                case "Xiaomi": {
                    result = checkSinglePermissionForXiaoMi(activity, permission);
                    break;
                }
                default: {
                    result = checkSinglePermission(activity, permission);
                }
            }

            switch (result) {
                case PERMISSION_GRANTED:
                    mPermissionListAccepted.add(new PermissionInfo(permission));
                    break;
                case PERMISSION_RATIONAL:
                    mPermissionListNeedReq.add(new PermissionInfo(permission));
                    break;
                case PERMISSION_DENIED:
                    mPermissionListDenied.add(new PermissionInfo(permission));
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 检查单个权限是否被允许,(当应用第一次安装的时候,不会有rational的值,此时返回均是denied)
     */
    @TargetApi(Build.VERSION_CODES.M)
    private int checkSinglePermission(Activity activity, String permission) {
        if (activity == null) {
            return -1;
        }
        if (activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
            return PERMISSION_GRANTED;
        } else {
            if (activity.shouldShowRequestPermissionRationale(permission)) {
                return PERMISSION_RATIONAL;
            } else {
                return PERMISSION_DENIED;
            }
        }
    }

    /**
     * 检查小米的单个权限是否被允许
     */
    @TargetApi(Build.VERSION_CODES.M)
    private int checkSinglePermissionForXiaoMi(Activity activity, String permission) {
        if (activity == null) {
            return -1;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return checkSinglePermission(activity, permission);
        }
        int auth = ActivityCompat.checkSelfPermission(activity, permission);
        AppOpsManager appOpsManager = (AppOpsManager) activity.getSystemService(Context.APP_OPS_SERVICE);
        int checkOp;
        if(Build.VERSION.SDK_INT <29){
             checkOp = appOpsManager.checkOpNoThrow( AppOpsManager.OPSTR_FINE_LOCATION, Binder.getCallingUid(), activity.getPackageName());
        }else{
             checkOp = appOpsManager.unsafeCheckOpNoThrow( AppOpsManager.OPSTR_FINE_LOCATION, Binder.getCallingUid(), activity.getPackageName());
        }
        if (auth == PackageManager.PERMISSION_GRANTED && checkOp == AppOpsManager.MODE_ALLOWED) {
            return PERMISSION_GRANTED;
        }
        if (auth == PackageManager.PERMISSION_GRANTED && checkOp == AppOpsManager.MODE_IGNORED) {
            return PERMISSION_DENIED;
        }
        return PERMISSION_DENIED;
    }

    /**
     * 通过开启一个新的activity作为申请权限的媒介
     */
    private void requestPermissions(Activity activity) {
        if (activity == null) {
            return;
        }
        String tag = this.toString();
        HelpActivity.addPermissionUtil(tag, this);
        Intent intent = new Intent(activity, HelpActivity.class);
        intent.putExtra(KEY_PERMISSION_LIST, mPermissions.toArray(new String[]{}));
        intent.putExtra(KEY_PERMISSION_TAG, tag);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }

    /**
     * 申请权限结果返回
     */
    @TargetApi(Build.VERSION_CODES.M)
    void onRequestPermissionResult(Activity activity, int requestCode, String[] permissions, int[] grantResults) {
        if (activity == null) {
            return;
        }
        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;
            List<PermissionInfo> needRationalPermissionList = new ArrayList<>();
            List<PermissionInfo> deniedPermissionList = new ArrayList<>();

            for (int i = 0; i < permissions.length; i++) {
                PermissionInfo info = new PermissionInfo(permissions[i]);
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    if (activity.shouldShowRequestPermissionRationale(permissions[i])) {
                        needRationalPermissionList.add(info);
                    } else {
                        deniedPermissionList.add(info);
                    }
                    isAllGranted = false;
                } else {
                    mPermissionListAccepted.add(info);
                }
            }
            if (deniedPermissionList.size() != 0) {
                if (!onDenied(deniedPermissionList)) {
                    //使用默认的PermissionDialog引导用户打开权限
                    showPermissionDialog(mActivity, deniedPermissionList);
                }
                isAllGranted = false;
            }
            if (needRationalPermissionList.size() != 0) {
                showRational(needRationalPermissionList);
                isAllGranted = false;
            }
            if (deniedPermissionList.size() != 0 || needRationalPermissionList.size() != 0) {
                ArrayList<PermissionInfo> notAgreePermissions = new ArrayList<>();
                notAgreePermissions.addAll(deniedPermissionList);
                notAgreePermissions.addAll(needRationalPermissionList);
                if (mResultCallBack != null) {
                    mResultCallBack.onNotAgree(notAgreePermissions);
                }
            }
            if (mPermissionListAccepted.size() != 0 && mResultCallBack != null) {
                onGranted(mPermissionListAccepted);
            }
            if (isAllGranted) {
                if (mResultCallBack != null) {
                    mResultCallBack.onGrantedAll();
                }
            }
        }
    }

    /**
     * 权限被用户许可之后回调的方法
     */
    private void onGranted(List<PermissionInfo> list) {
        if (mResultCallBack == null) {
            return;
        }
        if (list == null || list.size() == 0) return;
        String[] permissions = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            permissions[i] = list.get(i).getName();
        }
        mResultCallBack.onGranted(permissions);
    }

    /**
     * 权限申请被用户否定之后的回调方法,这个主要是当用户点击否定的同时点击了不在弹出,
     * 那么当再次申请权限,此方法会被调用
     *
     * @return true表示自己处理，不使用默认的PermissionDialog引导用户打开权限设置
     */
    private boolean onDenied(List<PermissionInfo> list) {
        if (mResultCallBack == null) {
            return false;
        }
        if (list == null || list.size() == 0) return false;
        String[] permissions = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            permissions[i] = list.get(i).getName();
        }
        return mResultCallBack.onDenied(permissions);
    }

    /**
     * 权限申请被用户否定后的回调方法,这个主要场景是当用户点击了否定,但未点击不在弹出,
     * 那么当再次申请权限的时候,此方法会被调用
     */
    private void showRational(List<PermissionInfo> list) {
        if (mResultCallBack == null) {
            return;
        }
        if (list == null || list.size() == 0) return;
        String[] permissions = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            permissions[i] = list.get(i).getName();
        }
        mResultCallBack.onRationalShow(permissions);
    }

    /**
     * 展示引导用户设置权限的Dialog
     */
    private void showPermissionDialog(Activity activity, List<PermissionInfo> list) {
        if (list.isEmpty()) return;
        if (activity == null) return;
        PermissionDialog permissionDialog = new PermissionDialog(activity);
        permissionDialog.setMessage(list);
        permissionDialog.show();
    }

}
