package com.qflbai.mvvm.base.viewmodel;

import android.app.Application;

import com.qflbai.mvvm.base.repository.AbsRepository;
import com.qflbai.mvvm.utils.TUtil;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * @author WenXian Bai
 * @Date: 2018/11/2.
 * @Description:
 */
public class BaseViewModel<T extends AbsRepository> extends AndroidViewModel {


    public T mRepository;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mRepository = TUtil.getNewInstance(this, 0);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mRepository != null) {
            mRepository.unDisposable();
        }
    }

}
