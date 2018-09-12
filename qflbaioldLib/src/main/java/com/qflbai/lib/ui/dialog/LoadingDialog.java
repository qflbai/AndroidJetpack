package com.qflbai.lib.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.qflbai.lib.R;
import com.qflbai.lib.ui.dialog.base.BaseDialogFragment;

/**
 * @author WenXian Bai
 * @Date: 2018/3/21.
 * @Description: 加载
 */

public class LoadingDialog extends BaseDialogFragment {
    /**
     * Bundel key
     */
    private static final String TV_CONTENT = "tv_content";
    /**
     * 内容显示textview
     */
    //private @BindView(R.id.tv_message) TextView mTvMessage;
    private String mMessage;

    private static LoadingDialog mLoadingDialog;
    public static LoadingDialog newInstance() {
        mLoadingDialog = new LoadingDialog();
        //Bundle args = new Bundle();
        return mLoadingDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View v = inflater.inflate(R.layout.lib_dialog_loading_full_screen, null);
        TextView tvMessage = v.findViewById(R.id.tv_message);
        if(mMessage!=null){
            tvMessage.setText(""+mMessage);
        }
        return v;
    }

    /**
     * 显示加载
     * @param message
     */
    public void showLoad(FragmentManager fragmentManager, String message){
        mLoadingDialog.show(fragmentManager,TV_CONTENT);
        mMessage = message;
    }

    public void showLoad(FragmentManager fragmentManager){
        mMessage = "";
        mLoadingDialog.show(fragmentManager,TV_CONTENT);
    }

    /**
     * 隐藏加载
     */
    public void dismissLoad(){
        if(mLoadingDialog!=null){
            super.dismiss();
        }
    }
}
