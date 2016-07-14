package com.hyun.exception;

import java.io.Serializable;

public class GwtException extends Exception {

    private static final long serialVersionUID = 1L;

    public static final int ERRCODE_SUCCESS = 0;

    public static final int ERRCODE_INVALID_PASSWD = 1;

    public static final int ERRCODE_SERVER_UNREACHABLE = 2;

    public static final int ERRCODE_SESSION_TIMEOUT = 3;

    public static final int ERRCODE_INVALID_SQL = 4;

    public static final int ERRCODE_SERVER_EXCEPT = 5;

    public static final int ERRCODE_OPERATION_NOTSUPPORTED = 6;

    public static final int ERRCODE_RESOUCE_UNAVAILABLE = 8;

    public static final int ERRCODE_UNKNOWN_EXCEPT = 9;

    public static final String ERRSTRING_SUCCESS = "成功操作";

    public static final String ERRSTRING_INVALID_PASSWD = "用户名/密码不正确";

    public static final String ERRSTRING_SERVER_UNREACHABLE = "服务器网络不可达";

    public static final String ERRSTRING_SESSION_TIMEOUT = "会话超时";

    public static final String ERRSTRING_INVALID_SQL = "非法SQL语句";

    public static final String ERRSTRING_SERVER_EXCEPT = "服务器异常";

    public static final String ERRSTRING_OPERATION_NOTSUPPORTED = "不支持的操作";

    public static final String ERRSTRING_RESOURCE_UNAVAILABLE = "无可用资源";

    public static final String ERRSTRING_UNKNOWN_EXCEPT = "未知类型错误";

    public static String code2message(int eID) {
        switch (eID) {
        case ERRCODE_SUCCESS:
            return ERRSTRING_SUCCESS;
        case ERRCODE_INVALID_PASSWD:
            return ERRSTRING_INVALID_PASSWD;
        case ERRCODE_SERVER_UNREACHABLE:
            return ERRSTRING_SERVER_UNREACHABLE;
        case ERRCODE_SESSION_TIMEOUT:
            return ERRSTRING_SESSION_TIMEOUT;
        case ERRCODE_INVALID_SQL:
            return ERRSTRING_INVALID_SQL;
        case ERRCODE_SERVER_EXCEPT:
            return ERRSTRING_SERVER_EXCEPT;
        case ERRCODE_OPERATION_NOTSUPPORTED:
            return ERRSTRING_OPERATION_NOTSUPPORTED;
        case ERRCODE_RESOUCE_UNAVAILABLE:
            return ERRSTRING_RESOURCE_UNAVAILABLE;
        case ERRCODE_UNKNOWN_EXCEPT:
        default:
            return ERRSTRING_UNKNOWN_EXCEPT;
        }
    }

    public int exceptionID = ERRCODE_SUCCESS;

    public GwtException() {
        this(ERRCODE_UNKNOWN_EXCEPT);
    }

    public GwtException(int eID) {
        this(eID, code2message(eID));
    }

    public GwtException(String msg) {
        this(ERRCODE_UNKNOWN_EXCEPT, msg);
    }

    public GwtException(int eID, String msg) {
        super(msg);
        this.exceptionID = eID;
    }

    public GwtException(Throwable t) {
        super(t.getMessage());
        if (t instanceof GwtException) {
            exceptionID = ((GwtException) t).getExceptionID();
        }
    }

    public int getExceptionID() {
        return exceptionID;
    }

    public String toString() {
        return code2message(exceptionID) + ":\n" + getMessage();
    }

}
