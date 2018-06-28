package com.miracle.basemvp.lifecycle.rx;

import android.content.Context;
import android.net.ParseException;
import android.support.annotation.IntDef;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.miracle.basemvp.R;
import com.miracle.basemvp.base.BaseResponse;
import com.miracle.basemvp.util.CommonDialogUtils;
import com.miracle.basemvp.util.ToastUtils;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


public abstract class RxDefaultObserver<T extends BaseResponse> implements Observer<T> {
    private final String TAG = this.getClass().getSimpleName();
    private Disposable mDisposable;
    private Context mContext;
    private CommonDialogUtils mDialogUtils;

    /**
     * 默认显示Loading
     *
     * @param context mContext
     * @创建者 何旭龙
     */
    public RxDefaultObserver(Context context) {
        this.mContext = context;
        this.mDialogUtils = new CommonDialogUtils(context);
        mDialogUtils.showProgress();
    }

    /**
     * 支持 Loading
     *
     * @param context      当前的Context
     * @param isShowLoading 是否显示loading
     * @创建者 何旭龙
     */
    public RxDefaultObserver(Context context, boolean isShowLoading) {
        this.mContext = context;
        this.mDialogUtils = new CommonDialogUtils(context);
        if (isShowLoading) {
            mDialogUtils.showProgress("Loading...");
        }
    }

    /**
     * 支持 Loading
     *
     * @param context      当前的Activity
     * @param isShowLoading 是否显示loading
     * @param loading       自定义loading样式 需继承CommonDialogUtils
     * @创建者 何旭龙
     */
    public RxDefaultObserver(Context context, boolean isShowLoading, CommonDialogUtils loading) {
        this.mDialogUtils = loading;
        if (isShowLoading) {
            mDialogUtils.showProgress("Loading...");
        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onComplete() {
        if (mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        dismissProgress();
    }

    /**
     * 系统错误
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        dismissProgress();
        if (e instanceof HttpException) {     //   HTTP错误
            setReason(BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            setReason(CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            setReason(CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            setReason(PARSE_ERROR);
        } else {
            setReason(UNKNOWN_ERROR);
        }
        onException();
    }

    /**
     * 服务器响应
     *
     * @param t
     */
    @Override
    public void onNext(T t) {
        Log.d("WJQ", "--------onNext: "+t.getService());
        BaseResponse.ResponseBean response = t.getResponse();
        if (response.isSuccessed()) {//code == 0000 返回成功
            onSucceed(t);
        } else {//失败
            onFailed(response);
        }
    }

    private void onException() {
        @ExceptionReason int reason = getReason();
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtils.showLong(mContext, R.string.connect_error);
                break;
            case CONNECT_TIMEOUT:
                ToastUtils.showLong(mContext, R.string.connect_timeout);
                break;
            case BAD_NETWORK:
                ToastUtils.showLong(mContext, R.string.bad_network);
                break;
            case PARSE_ERROR:
                ToastUtils.showLong(mContext, R.string.parse_error);
                break;
            case UNKNOWN_ERROR:
            default:
                ToastUtils.showLong(mContext, R.string.unknown_error);
                break;
        }
    }

    private void dismissProgress() {
        if (mDialogUtils != null) {
            mDialogUtils.dismissProgress();
        }
    }

    /**
     * 服务器返回数据但结果code不为0000
     *
     * @param errorBean 错误bean
     */
    public void onFailed(BaseResponse.ResponseBean errorBean) {
        ToastUtils.showLong(mContext, errorBean.getMessage());
    }

    /**
     * 返回数据成功 结果code 为 0000
     *
     * @param t
     */
    public abstract void onSucceed(T t);

    /**
     * 请求网络失败原因
     */
    /*enum ExceptionReason {
        *//**
     * 解析数据失败
     *//*
        PARSE_ERROR,
        *//**
     * 网络问题
     *//*
        BAD_NETWORK,
        *//**
     * 连接错误
     *//*
        CONNECT_ERROR,
        *//**
     * 连接超时
     *//*
        CONNECT_TIMEOUT,
        *//**
     * 未知错误
     *//*
        UNKNOWN_ERROR,
    }*/
    /**
     * 请求网络失败原因
     */
    private static final int PARSE_ERROR = 0;
    private static final int BAD_NETWORK = 1;
    private static final int CONNECT_ERROR = 2;
    private static final int CONNECT_TIMEOUT = 3;
    private static final int UNKNOWN_ERROR = 4;

    @IntDef({PARSE_ERROR, BAD_NETWORK, CONNECT_ERROR, CONNECT_TIMEOUT, UNKNOWN_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    @interface ExceptionReason {
    }

    @ExceptionReason
    private int current;

    private void setReason(@ExceptionReason int current) {
        this.current = current;
    }

    private int getReason() {
        return current;
    }
}
