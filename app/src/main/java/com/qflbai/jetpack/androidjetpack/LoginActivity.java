package com.qflbai.jetpack.androidjetpack;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.qflbai.jetpack.androidjetpack.databinding.ActivityLoginBinding;
import com.qflbai.lib.LibBuildConfig;
import com.qflbai.lib.base.BaseActivity;
import com.qflbai.lib.base.BaseLibActivity;
import com.qflbai.lib.base.BaseViewModle;

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding mBinding;
    private LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        netExceptionListen(loginViewModel);
        mBinding.setLoginCallback(new LoginCallback() {
            @Override
            public void login() {
                String account = mBinding.etAccount.getText().toString();
                String password = mBinding.etPassword.getText().toString();
                LoginInfoModle loginInfoModle = new LoginInfoModle();
                loginInfoModle.setAccount(account);
                loginInfoModle.setPassword(password);

                loginViewModel.login(loginInfoModle);
            }
        });

        loginViewModel.getLoginInfo().observe(this, new Observer<LoginInfoModle>() {
            @Override
            public void onChanged(@Nullable LoginInfoModle loginInfoModle) {
                Toast.makeText(LoginActivity.this,loginInfoModle.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


}
