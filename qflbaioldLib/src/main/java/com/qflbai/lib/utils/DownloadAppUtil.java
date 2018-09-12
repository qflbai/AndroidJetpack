package com.qflbai.lib.utils;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.qflbai.lib.LibConfig;
import com.qflbai.lib.SystemUtil;
import com.qflbai.lib.entity.VersionUpdataBean;
import com.qflbai.lib.net.RetrofitManage;
import com.qflbai.lib.net.assist.DownloadFileAssist;
import com.qflbai.lib.net.body.DownloadInfo;
import com.qflbai.lib.net.listener.DownloadListener;
import com.qflbai.lib.net.retrofit.RetrofitService;
import com.qflbai.lib.ui.dialog.AffirmAlertdialog;
import com.qflbai.lib.ui.dialog.listener.OnClickListener;
import com.qflbai.lib.utils.ui.toast.ToastUtil;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author WenXian Bai
 * @Date: 2018/3/29.
 * @Description: 下载APP工具
 */

public class DownloadAppUtil {
    static AppCompatActivity mActivity;

    /**
     * 检测版本号
     */
    public static void checkVersion(final AppCompatActivity activity) {
        mActivity = activity;
        // 下载路径
        String baseurl = "http://e.sun-tech.cn/liangziyunma/appindex/";
        String path = "synchronizeRecording_getVerion.do";
        RetrofitManage retrofitManage = new RetrofitManage();
        RetrofitService service = retrofitManage.createService(baseurl);
        service.getNet(path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<ResponseBody>>() {
                    /**
                     * Consume the given value.
                     *
                     * @param response the value
                     * @throws Exception on error
                     */
                    @Override
                    public void accept(Response<ResponseBody> response) throws Exception {
                        try {
                            String json = response.body().string();
                            Gson gson = new Gson();
                            VersionUpdataBean versionUpdataBean = gson.fromJson(json, VersionUpdataBean.class);
                            List<VersionUpdataBean.RootBean> rootBeanList = versionUpdataBean.getRoot();
                            if (!rootBeanList.contains(SystemUtil.getPackageName(activity))) {
                                ToastUtil.show(mActivity, "当前已是最新版本");
                                return;
                            }
                            for (VersionUpdataBean.RootBean rootBean : rootBeanList) {
                                // 获取包名
                                String fpackagename = rootBean.getFpackagename();
                                // 判断包名
                                if (SystemUtil.getPackageName(mActivity).equals(fpackagename)) {
                                    String fdetail = rootBean.getFdetail();
                                    String frelease = rootBean.getFrelease();
                                    String fname = rootBean.getFname();

                                    long netVersionCode = Integer.parseInt(frelease);

                                    //获取编号
                                    int versionCode = SystemUtil.getVersionCode(mActivity);

                                    // 判断版本
                                    if (versionCode > netVersionCode) {
                                        String furl = rootBean.getFurl();
                                        updataWindow(mActivity, furl, fdetail, fname);
                                    } else {
                                        ToastUtil.show(mActivity, "当前已是最新版本");
                                    }

                                }
                            }

                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }
                });


    }

    /**
     * 更新弹窗
     */
    private static void updataWindow(AppCompatActivity activity, final String url, String newVersionName, final String apkName) {
        StringBuffer sb = new StringBuffer();
        sb.append("当前版本" + ":" + SystemUtil.getVersionName(activity));
        sb.append("\n新版本:" + newVersionName);
        AffirmAlertdialog affirmAlertdialog = new AffirmAlertdialog();
        affirmAlertdialog.setPositiveButton("确定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                downLoadApk(url, apkName);
            }
        });

        affirmAlertdialog.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        affirmAlertdialog.showMessageDialog(activity.getSupportFragmentManager(), sb.toString());
    }


    /**
     * 下载apk
     *
     * @param url
     */
    private static void downLoadApk(String url, String apkName) {
        String savnPath = Environment.getExternalStorageDirectory().getPath() + "/suntech/" + apkName + ".app";
        DownloadFileAssist downloadFileAssist = new DownloadFileAssist();
        downloadFileAssist.downloadFile(url, savnPath, new DownloadListener() {
            @Override
            public void onStarted() {
                if (pd == null) {
                    pd = new ProgressDialog(mActivity);
                    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

                    pd.setTitle("apk下载");

                    pd.setMessage("正在下载中");
                    pd.setMax((int) 100);

                }
                pd.show();
            }

            @Override
            public void onSuccess(File result) {
                pd.dismiss();
                installApk(result);
            }

            @Override
            public void onLoading(DownloadInfo downloadInfo) {
                pd.setProgress((int) downloadInfo.getProgress());
            }

            @Override
            public void onFail(Throwable ex) {
                pd.dismiss();
            }
        });

    }


    static ProgressDialog pd = null;

    private static final int ENTERHOME = 6;

    /**
     * 提示用户安装apk
     *
     * @param file 隐式意图安装
     */
    protected static void installApk(File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        // startActivity(intent);
        // 在当前activity(PackageInstallerActivity)退出的时候，会调用之前的activity(SpalshActivity)的onActivityResult()方法；
        mActivity.startActivityForResult(intent, ENTERHOME);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
