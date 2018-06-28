package com.miracle.basemvp.base;

import okhttp3.Interceptor;

/**
 *
 */
public class BaseRequest {

    /**
     * 1 拼接参数
     * 0 不拼接参数
     * 默认返回"1"在{@link com.air.basemvp.http.NetWorkInterceptor#intercept(Interceptor.Chain)} 进行拼接参数
     */
    private String tokenFlag = "1";

    /**
     * {@link BaseRequest{@link #setTokenFlag(String)}}
     *
     * @param tokenFlag
     */
    public void setTokenFlag(String tokenFlag) {
        this.tokenFlag = tokenFlag;
    }
}

