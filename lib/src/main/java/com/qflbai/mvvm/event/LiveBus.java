package com.qflbai.mvvm.event;

import android.text.TextUtils;

import com.qflbai.mvvm.utils.TUtil;

import java.util.concurrent.ConcurrentHashMap;
import androidx.lifecycle.MutableLiveData;

/**
 * @author WenXian Bai
 * @Date: 2018/11/2.
 * @Description: 总线
 */
public class LiveBus {

    private static volatile LiveBus instance;

    private final ConcurrentHashMap<Object, LiveBusData<Object>> mLiveBus;

    private LiveBus() {
        mLiveBus = new ConcurrentHashMap<>();
    }

    public static LiveBus getDefault() {
        if (instance == null) {
            synchronized (LiveBus.class) {
                if (instance == null) {
                    instance = new LiveBus();
                }
            }
        }
        return instance;
    }


    public <T> MutableLiveData<T> subscribe(Object eventKey) {
        TUtil.checkNotNull(eventKey);
        return subscribe(eventKey, "");
    }

    public <T> MutableLiveData<T> subscribe(Object eventKey, String tag) {
        TUtil.checkNotNull(eventKey);
        return (MutableLiveData<T>) subscribe(eventKey, tag, Object.class);
    }

    public <T> MutableLiveData<T> subscribe(Object eventKey, Class<T> tClass) {
        return subscribe(eventKey, null, tClass);
    }

    public <T> MutableLiveData<T> subscribe(Object eventKey, String tag, Class<T> tClass) {
        TUtil.checkNotNull(eventKey);
        TUtil.checkNotNull(tClass);
        String key;
        if (!TextUtils.isEmpty(tag)) {
            key = eventKey + tag;
        } else {
            key = (String) eventKey;
        }

        if (!mLiveBus.containsKey(key)) {
            mLiveBus.put(key, new LiveBusData<>(true));
        } else {
            LiveBusData liveBusData = mLiveBus.get(key);
            liveBusData.isFirstSubscribe = false;
        }

        return (MutableLiveData<T>) mLiveBus.get(key);
    }

    public <T> MutableLiveData<T> postEvent(Object eventKey, T value) {
        TUtil.checkNotNull(eventKey);
        return postEvent(eventKey, null, value);
    }

    public <T> MutableLiveData<T> postEvent(Object eventKey, String tag, T value) {
        TUtil.checkNotNull(eventKey);
        String key;
        if (!TextUtils.isEmpty(tag)) {
            key = eventKey + tag;
        } else {
            key = (String) eventKey;
        }
        MutableLiveData<T> mutableLiveData = subscribe(key);
        mutableLiveData.postValue(value);
        return mutableLiveData;
    }


    public static class LiveBusData<T> extends MutableLiveData<T> {

        private boolean isFirstSubscribe;

        public LiveBusData(boolean isFirstSubscribe) {
            this.isFirstSubscribe = isFirstSubscribe;
        }


    }


    public void clear(Object eventKey) {
        clear(eventKey, null);
    }

    public void clear(Object eventKey, String tag) {
        if (mLiveBus != null && mLiveBus.size() > 0) {
            String clearkey;
            if (!TextUtils.isEmpty(tag)) {
                clearkey = eventKey + tag;
            } else {
                clearkey = (String) eventKey;
            }
            mLiveBus.remove(clearkey);
        }

    }

}
