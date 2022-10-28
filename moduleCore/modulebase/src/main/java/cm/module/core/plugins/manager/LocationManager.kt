package cm.module.core.plugins.manager

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import cm.module.core.utils.LocationUtils
import com.tencent.mmkv.MMKV

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2022/7/22 10:25
 * Desc:
 * *****************************************************************
 */
@SuppressLint("UsingALog")
class LocationManager private constructor() {
    private val TAG = LocationManager::class.java.simpleName
    private val MMKV_LOCATION = "Last_Location"
    private var location: Location? = null
    private var locationListener: LocationListener? = null
    private var isQuerying: Boolean = false

    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        @SuppressLint("MissingPermission")
        override fun handleMessage(msg: Message) {
            if (isQuerying) {
                isQuerying = false
                locationListener?.locationFailed()
            }
            LocationUtils.unregister()
        }
    }

    companion object {
        val instance: LocationManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { LocationManager() }
    }

    fun updateLocation(location: Location?) {
        this.location = location
        if (location != null) {
            MMKV.defaultMMKV().encode(MMKV_LOCATION, location)
        }
    }

    fun getLastLocation(): Location? {
        if (location != null) return location
        location = MMKV.defaultMMKV().decodeParcelable(MMKV_LOCATION, Location::class.java)
        return location
    }

    fun refreshLocation(locationListener: LocationListener) {
        this.locationListener = locationListener
        initLocation(true)
    }

    @SuppressLint("MissingPermission")
    fun initLocation(needCallback: Boolean = false) {
        if (needCallback && !isQuerying) {
            mHandler.sendMessageDelayed(Message.obtain(), 3500)
            isQuerying = true
        }
        val register = LocationUtils.register(0, 0,
            object : LocationUtils.OnLocationChangeListener {

                override fun getLastKnownLocation(location: Location?) {
                    Log.i(
                        TAG,
                        "getLastKnownLocation  lon:${location?.longitude} == lat:${location?.latitude}"
                    )
                    if (needCallback) {
                        isQuerying = false
                        locationListener?.location(location)
                    }
                    updateLocation(location)
                    LocationUtils.unregister()
                }

                override fun onLocationChanged(location: Location?) {
                    Log.i(
                        TAG,
                        "onLocationChanged  lon:${location?.longitude} == lat:${location?.latitude}"
                    )
                    if (needCallback) {
                        isQuerying = false
                        locationListener?.location(location)
                    }
                    updateLocation(location)
                    LocationUtils.unregister()

                }

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                    Log.i(TAG, "onStatusChanged  provider:${provider} == status: ${status}")

                }
            })

        if (!register) {
            isQuerying = false
            locationListener?.locationFailed()
        }

    }


    public interface LocationListener {
        fun location(location: Location?)

        fun locationFailed()
    }


    fun isFirstInApp(): Boolean {
        val decodeBool = MMKV.defaultMMKV().decodeBool("is_first_in_app", true)
        if (decodeBool) {
            MMKV.defaultMMKV().encode("is_first_in_app", false)
        }
        return decodeBool
    }

}