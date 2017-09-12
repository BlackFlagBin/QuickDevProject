package com.blackflagbin.common.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AntiShakeUtil {
    private static List<OneClickUtil> utils = new ArrayList<>();

    public static boolean check(Object o) {
        String flag = null;
        if (o == null) { flag = Thread.currentThread().getStackTrace()[2].getMethodName(); } else {
            flag = o.toString();
        }
        for (OneClickUtil util : utils) {
            if (util.getMethodName().equals(flag)) {
                return util.check();
            }
        }
        OneClickUtil clickUtil = new OneClickUtil(flag);
        utils.add(clickUtil);
        return clickUtil.check();
    }

    public static boolean check() {
        return check(null);
    }

    public static class OneClickUtil {
        public static final int MIN_CLICK_DELAY_TIME = 1000;
        private String methodName;
        private long lastClickTime = 0;

        public OneClickUtil(String methodName) {
            this.methodName = methodName;
        }

        public String getMethodName() {
            return methodName;
        }

        public boolean check() {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (Math.abs(currentTime - lastClickTime) > MIN_CLICK_DELAY_TIME) {
                lastClickTime = currentTime;
                return false;
            } else {
                return true;
            }
        }
    }
}