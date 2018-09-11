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

public class LoginActivity extends AppCompatActivity  {

    private LoginViewModel mLoginViewModel;
    ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        mBinding.setLoginCallback(new LoginCallback() {
            @Override
            public void login() {
                String account = mBinding.etAccount.getText().toString();
                String password = mBinding.etPassword.getText().toString();
                LoginInfoModle loginInfoModle = new LoginInfoModle();
                loginInfoModle.setAccount(account);
                loginInfoModle.setPassword(password);

                mLoginViewModel.login(loginInfoModle);
            }
        });

        mLoginViewModel.getLoginInfo().observe(this, new Observer<LoginInfoModle>() {
            @Override
            public void onChanged(@Nullable LoginInfoModle loginInfoModle) {
                Toast.makeText(LoginActivity.this,loginInfoModle.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


}
