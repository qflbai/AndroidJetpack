package com.qflbai.library.net.assist;


import com.qflbai.library.net.RetrofitManage;
import com.qflbai.library.net.body.DownloadInfo;
import com.qflbai.library.net.listener.DownloadListener;
import com.qflbai.library.net.retrofit.RetrofitService;

import java.io.File;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import com.qflbai.library.utils.FileUtil;

/**
 * @author WenXian Bai
 * @Date: 2018/5/21.
 * @Description:
 */
public class DownloadFileAssist {
    public Disposable mDisposable;

    /**
     * 下载文件
     *
     * @param baseUrl
     * @param pathUrl
     * @param savePath
     * @param downloadListener
     */
    public void downloadFile(String baseUrl, String pathUrl, final String savePath, final DownloadListener downloadListener) {

        final DownloadListener netDownloadListener1 = new DownloadListener() {
            @Override
            public void onStarted() {
                downloadListener.onStarted();
            }

            @Override
            public void onSuccess(File result) {
                cancalDown();
                downloadListener.onSuccess(result);
            }

            @Override
            public void onLoading(DownloadInfo downloadInfo) {

                downloadListener.onLoading(downloadInfo);
            }

            @Override
            public void onFail(Throwable ex) {
                cancalDown();
                downloadListener.onFail(ex);
            }
        };
        RetrofitManage retrofitManage = new RetrofitManage();
        RetrofitService service = retrofitManage.createDownloadService(baseUrl, netDownloadListener1);
        Observable<ResponseBody> download = service.download(pathUrl);
        download.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, InputStream>() {
                    @Override
                    public InputStream apply(ResponseBody responseBody) throws Exception {
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.computation())
                .map(new Function<InputStream, File>() {
                    @Override
                    public File apply(InputStream inputStream) throws Exception {
                        File file = FileUtil.writeFile(inputStream, savePath);
                        return file;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<File>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                        netDownloadListener1.onStarted();
                    }

                    @Override
                    public void onNext(File file) {
                        netDownloadListener1.onSuccess(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        netDownloadListener1.onFail(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 下载文件
     *
     * @param url
     * @param savePath
     * @param downloadListener
     */
    public void downloadFile(String url, final String savePath, final DownloadListener downloadListener) {

        final DownloadListener netDownloadListener1 = new DownloadListener() {
            @Override
            public void onStarted() {
                downloadListener.onStarted();
            }

            @Override
            public void onSuccess(File result) {
                cancalDown();
                downloadListener.onSuccess(result);
            }

            @Override
            public void onLoading(DownloadInfo downloadInfo) {

                downloadListener.onLoading(downloadInfo);
            }

            @Override
            public void onFail(Throwable ex) {
                cancalDown();
                downloadListener.onFail(ex);
            }
        };
        RetrofitManage retrofitManage = new RetrofitManage();
        RetrofitService service = retrofitManage.createDownloadService(netDownloadListener1);
        Observable<ResponseBody> download = service.download(url);
        download.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, InputStream>() {
                    @Override
                    public InputStream apply(ResponseBody responseBody) throws Exception {
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.computation())
                .map(new Function<InputStream, File>() {
                    @Override
                    public File apply(InputStream inputStream) throws Exception {
                        File file = FileUtil.writeFile(inputStream, savePath);
                        return file;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<File>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                        netDownloadListener1.onStarted();
                    }

                    @Override
                    public void onNext(File file) {
                        netDownloadListener1.onSuccess(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        netDownloadListener1.onFail(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void cancalDown() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
