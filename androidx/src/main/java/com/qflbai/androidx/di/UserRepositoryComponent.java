package com.qflbai.androidx.di;

import com.qflbai.androidx.viewmodel.LoginModel;

import javax.inject.Inject;

import dagger.Component;

/**
 * @author WenXian Bai
 * @Date: 2018/10/26.
 * @Description:
 */
@Component
public interface UserRepositoryComponent {
    void inject(LoginModel loginModel);

}
