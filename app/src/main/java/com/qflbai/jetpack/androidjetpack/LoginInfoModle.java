package com.qflbai.jetpack.androidjetpack;

/**
 * @author WenXian Bai
 * @Date: 2018/9/7.
 * @Description:
 */
public class LoginInfoModle {
    private String account;
    private String password;
    private boolean isLoginSuceess;
    private String message;
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoginSuceess() {
        return isLoginSuceess;
    }

    public void setLoginSuceess(boolean loginSuceess) {
        isLoginSuceess = loginSuceess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
