package com.xinhecuican.qrcodescan.utils;

import com.xinhecuican.qrcodescan.MainAbility;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class LogLevel {
    public static final HiLogLabel TAG = new HiLogLabel(HiLog.DEBUG, 0x201, MainAbility.class.getName());
    public static final HiLogLabel ERROR = new HiLogLabel(HiLog.ERROR, 0x201, MainAbility.class.getName());
    public static final HiLogLabel INFO = new HiLogLabel(HiLog.INFO, 0x201, MainAbility.class.getName());

    public static void error(String message)
    {
        HiLog.error(ERROR, message);
    }
    public static void info(String message){HiLog.info(INFO, message);}
}
