package com.miracle.basemvp.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * @desc loading
 * @create Hexl
 * @date
 * @modify
 * @modifyDate
 */
public class CommonDialogUtils {

    private ProgressDialog mProgressDialog;

    public CommonDialogUtils(Context context) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    public void showProgress() {
        mProgressDialog.show();
    }

    public void showProgress(String loadMsg) {
        mProgressDialog.show();
        mProgressDialog.setMessage(loadMsg);
    }

    public void dismissProgress() {
        mProgressDialog.dismiss();
    }
}
