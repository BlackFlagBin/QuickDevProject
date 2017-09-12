package com.blackflagbin.common.http.progress;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.blackflagbin.common.R;

public class ProgressDialogHandler extends Handler {

    public static final int SHOW_PROGRESS_DIALOG    = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private Dialog pd;

    private Context                context;
    private boolean                cancelable;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(
            Context context, ProgressCancelListener mProgressCancelListener, boolean cancelable) {
        super();
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }

    private void initProgressDialog() {
        if (pd == null) {
            pd = new Dialog(context, R.style.default_dialog);
            //返回键可以取消Dialog
            pd.setCancelable(cancelable);
            //点击外侧不可取消
            pd.setCanceledOnTouchOutside(false);

            if (cancelable) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }

            if (pd != null && !pd.isShowing()) {
                pd.show();
                pd.setContentView(R.layout.dialog_loading);
            }
        }
    }

    private void dismissProgressDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
            pd = null;
        }
    }

}
