package com.lcm.mvvm.utils;

import com.hwangjr.rxbus.Bus;

/**
 * ****************************************************************
 * Author: LiChenMing.Chaman
 * Date: 2019/9/17 15:17
 * Desc:
 * *****************************************************************
 */
public class RxBus {

    private static Bus sBus;

    public static synchronized Bus get() {
        if (sBus == null) {
            sBus = new Bus();
        }
        return sBus;
    }
}
