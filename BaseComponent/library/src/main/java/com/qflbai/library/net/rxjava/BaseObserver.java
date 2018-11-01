package com.qflbai.library.net.rxjava;

import java.util.ArrayList;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author WenXian Bai
 * @Date: 2018/5/27.
 * @Description:
 */
public abstract class BaseObserver implements Observer<Response<ResponseBody>> {
    /**
     * 是否正在网络请求()
     */
    protected boolean mIsNetRequesting = false;
    /**
     * 网络请求网阀
     */
    protected ArrayList<Disposable> disposables = new ArrayList<>();

    public void addNetManage(Disposable disposable) {
        if (disposables.contains(disposable)) {
            return;
        }
        disposables.add(disposable);
    }

    /**
     * 关闭网络请求
     */
    public void closeNet() {
        if (disposables.size() <= 0) {
            return;
        }
        mIsNetRequesting = false;
        for (Disposable disposable : disposables) {
            disposable.dispose();
        }
        disposables.clear();
    }

}
