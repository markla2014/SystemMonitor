package com.hyun.common;

import java.util.Arrays;

public class ServerMonitorConstant {
    public static final int FETCH_PAGE = 1;
    public static final int PAGE_SIZE = 20;
    public static final String SUCCESSFUL = "successful";
    public static final String FAIL = "账户或密码不对";
    public static final int CONNECT_POOLSIZE = 3;

    public static int getMath(int a, int b) {
        int temp = a % b;
        int tem1 = a / b;
        if (temp != 0) {
            tem1++;
        }
        return tem1;
    }

    public static boolean checkArray(String[] arr, String targetValue) {

        return Arrays.asList(arr).contains(targetValue);

    }

}
