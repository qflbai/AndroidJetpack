package com.qflbai.library.net.download;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import com.alibaba.fastjson.JSON;
import com.qflbai.library.net.RetrofitManage;
import com.qflbai.library.net.assist.DownloadFileAssist;
import com.qflbai.library.net.body.DownloadInfo;
import com.qflbai.library.net.body.VersionUpdataBean;
import com.qflbai.library.net.listener.DownloadListener;
import com.qflbai.library.net.retrofit.RetrofitService;
import com.qflbai.library.utils.SystemUtil;

import java.io.File;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author WenXian Bai
 * @Date: 2018/3/29.
 * @Description: 下载APP工具
 */

public class DownloadAppUtil {
    private static AppCompatActivity mActivity;
    private static Disposable mSubscribe;

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
        mSubscribe = service.getNet(path)
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
                            VersionUpdataBean versionUpdataBean = JSON.parseObject(json, VersionUpdataBean.class);

                            List<VersionUpdataBean.RootBean> rootBeanList = versionUpdataBean.getRoot();
                            boolean isHaveNewVersion = false;
                            String fdetail = "";
                            String frelease = "";
                            String fname = "";
                            String furl = "";
                            for (VersionUpdataBean.RootBean rootBean : rootBeanList) {
                                // 获取包名
                                String fpackagename = rootBean.getFpackagename();
                                // 判断包名
                                if (SystemUtil.getPackageName(mActivity).equals(fpackagename)) {
                                    fdetail = rootBean.getFdetail();
                                    frelease = rootBean.getFrelease();
                                    fname = rootBean.getFname();

                                    int netVersionCode = Integer.parseInt(frelease);

                                    //获取编号
                                    int versionCode = SystemUtil.getVersionCode(mActivity);

                                    // 判断版本
                                    if (versionCode < netVersionCode) {
                                        isHaveNewVersion = true;
                                        furl = rootBean.getFurl();
                                    }
                                    break;
                                }
                            }

                            if (isHaveNewVersion) {
                                if (furl.isEmpty() || fdetail.isEmpty() || fname.isEmpty()) {
                                    return;
                                }
                                updataWindow(mActivity, furl, fdetail, fname);
                            } else {
                                //ToastUtil.show(mActivity, "当前已是最新版本");
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

    }


    /**
     * 下载apk
     *
     * @param url
     */
    private static void downLoadApk(String url, String apkName) {

        String savnParentPath = Environment.getExternalStorageDirectory().getPath() + "/suntech";
        File parentFile = new File(savnParentPath);
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        String savnPath = savnParentPath + File.separator + apkName + ".apk";
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
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(
                    mActivity.getApplicationContext(),
                    mActivity.getPackageName() + ".fileprovider",
                    file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }
        mActivity.startActivity(intent);
    }
}
