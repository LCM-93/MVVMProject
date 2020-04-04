package cm.mvvm.core.utils;

import android.content.Intent;
import android.net.Uri;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2020-04-04 13:36
 * Desc:
 * WGS-84：是国际标准，GPS坐标（Google Earth使用、或者GPS模块）
 * GCJ-02：中国坐标偏移标准，Google
 * Map、高德、腾讯使用 BD-09：百度坐标偏移标准，Baidu Map使用
 * pi: 圆周率。
 * a: 卫星椭球坐标投影到平面地图坐标系的投影因子。
 * ee: 椭球的偏心率。
 * x_pi: 圆周率转换量。
 * transformLat(lat, lon): 转换方法，比较复杂，不必深究了。输入：横纵坐标，输出：转换后的横坐标。
 * transformLon(lat, lon): 转换方法，同样复杂，自行脑补吧。输入：横纵坐标，输出：转换后的纵坐标。
 * wgs2gcj(lat, lon): WGS坐标转换为GCJ坐标。
 * gcj2bd(lat, lon): GCJ坐标转换为百度坐标。
 * *****************************************************************
 */
public class MapUtils {
    private static Double pi = Math.PI;  //  圆周率。
    private static Double a = 6378245.0;  //卫星椭球坐标投影到平面地图坐标系的投影因子。
    private static Double ee = 0.00669342162296594323;  //椭球的偏心率
    private static Double x_pi = pi * 3000.0 / 180.0; //圆周率转换量

    /**
     * 打开百度地图
     */
    public static void openBDMap(double lat,double lon,String poiName){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String bdUriStr = "baidumap://map/marker?location="+lat+","+lon+"&src="+ AppUtils.getAppPackageName() +"&coord_type=bd09ll&title="+poiName;
            intent.setData(Uri.parse(bdUriStr));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Utils.getApp().startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort("请安装百度地图");
        }
    }

    /**
     * 打开高德地图
     * @param lat
     * @param lon
     * @param poiName
     */
    public static void openGDMap(double lat,double lon,String poiName){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String gdUriStr = "androidamap://viewMap?sourceApplication="+ AppUtils.getAppName()+"&poiname="+poiName+"&lat="+lat+"&lon="+lon+"&dev=0";
            intent.setData(Uri.parse(gdUriStr));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Utils.getApp().startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort("请安装高德地图");
        }
    }

    /**
     * 高德坐标转百度坐标
     * @param lat
     * @param lon
     * @return
     */
    public static double[] gd2bd(double lat,double lon){
        double x = lon;
        double y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        double bd_lon = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return new double[]{bd_lat,bd_lon};
    }

    /**
     * 百度坐标转高德坐标
     * @param lat
     * @param lon
     * @return
     */
    public static double[] bd2gd(double lat, double lon) {
        double x = lon - 0.0065;
        double y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double gd_lon = z * Math.cos(theta);
        double gd_lat = z * Math.sin(theta);
        return new double[]{gd_lat,gd_lon};
    }

    /**
     * wgs坐标转百度坐标
     *
     * @param lat
     * @param lon
     * @return
     */
    public static double[] wgs2bd(double lat, double lon) {
        double[] wgs2gcj = wgs2gcj(lat, lon);
        double[] gcj2bd = gcj2bd(wgs2gcj[0], wgs2gcj[1]);
        return gcj2bd;
    }

    /**
     * gcj坐标转百度坐标
     *
     * @param lat
     * @param lon
     * @return
     */
    public static double[] gcj2bd(double lat, double lon) {
        double x = lon, y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        double bd_lon = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return new double[]{bd_lat, bd_lon};
    }

    /**
     * 百度坐标转gcj坐标
     *
     * @param lat
     * @param lon
     * @return
     */
    public static double[] bd2gcj(double lat, double lon) {
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double gg_lon = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return new double[]{gg_lat, gg_lon};
    }

    /**
     * wgs坐标 转 gcj坐标
     *
     * @param lat
     * @param lon
     * @return
     */
    public static double[] wgs2gcj(double lat, double lon) {
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        double[] loc = {mgLat, mgLon};
        return loc;
    }

    private static double transformLat(double lat, double lon) {
        double ret = -100.0 + 2.0 * lat + 3.0 * lon + 0.2 * lon * lon + 0.1 * lat * lon + 0.2 * Math.sqrt(Math.abs(lat));
        ret += (20.0 * Math.sin(6.0 * lat * pi) + 20.0 * Math.sin(2.0 * lat * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lon * pi) + 40.0 * Math.sin(lon / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lon / 12.0 * pi) + 320 * Math.sin(lon * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLon(double lat, double lon) {
        double ret = 300.0 + lat + 2.0 * lon + 0.1 * lat * lat + 0.1 * lat * lon + 0.1 * Math.sqrt(Math.abs(lat));
        ret += (20.0 * Math.sin(6.0 * lat * pi) + 20.0 * Math.sin(2.0 * lat * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * pi) + 40.0 * Math.sin(lat / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lat / 12.0 * pi) + 300.0 * Math.sin(lat / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }

}
