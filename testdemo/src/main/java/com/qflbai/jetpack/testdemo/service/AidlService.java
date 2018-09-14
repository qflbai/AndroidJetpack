package com.qflbai.jetpack.testdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.qflbai.jetpack.testdemo.aidl.Book;
import com.qflbai.jetpack.testdemo.aidl.IBookManager;
import com.qflbai.lib.utils.log.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class AidlService extends Service {

    private ArrayList<Book> mBooks;

    public AidlService() {
    }

    private IBinder ibinder = new IBookManager.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBooks;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBooks.add(book);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        mBooks = new ArrayList<>();
        LogUtil.w("tag","onCreate");
        return ibinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.w("tag","onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.w("tag","onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }


}
