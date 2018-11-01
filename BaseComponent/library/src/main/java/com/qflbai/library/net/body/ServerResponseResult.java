package com.qflbai.library.net.body;


/**
 * @author WenXian Bai
 * @Date: 2018/3/27.
 * @Description:
 */

public class ServerResponseResult {

    /**
     * success : true
     * resultCode : 0
     * data : {}
     * timestamp : 1508209804292
     */
    private boolean success;
    private String resultCode;
    private String message;
    private Object data;
    private String timestamp;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
