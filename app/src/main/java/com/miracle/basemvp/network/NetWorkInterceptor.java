package com.miracle.basemvp.network;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.miracle.basemvp.util.SecretTool;
import com.miracle.basemvp.util.Utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.prefs.Preferences;
//
import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import static com.miracle.basemvp.util.Utils.checkNullException;

/**
 * 网络拦截器
 */
public class NetWorkInterceptor implements Interceptor {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;

    @Inject
    public NetWorkInterceptor(Context context) {
        this.mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        //获取到request
        Request request = chain.request();
        //获取请求体
        RequestBody requestBody = request.body();
        //获取请求方式
        String requestMethod = request.method();
        //获取请求URL
        String requestUrl = request.url().toString();
        if (requestBody != null) {
            String paramsStr = bodyToString(requestBody);
            Log.e(TAG, "请求地址: \n  " + requestUrl + "  请求参数:\n " + paramsStr);
        }
        if (requestMethod.equals("POST")) {
            //未添加公共参数的Json参数
            String oldParamsJson = bodyToString(request.body());
            //从AppComponent中获取Gson
//            Gson gson = Utils.obtainAppComponentFromContext(BaseApplication.getContext()).gson();
            //用Gson解析成为Map对象为添加公共参数做准备t
//            TreeMap rootMap = gson.fromJson(oldParamsJson, TreeMap.class);
//            checkNullException(rootMap, "传入Json不能为null");
            //添加公共参数
            HashMap<String, Object> requestMap = new HashMap<>();
            //获取当前时间戳
            Long timestamp = System.currentTimeMillis();
            //获取token
//            String token = Preferences.getValue(mContext, TOKEN);
            //生成加密前Sign
//            String validateSign = SERVERKEY + "=" + Preferences.getValue(mContext, SERVERKEY) +
// "&" //密钥
//                    + TOKEN + "=" + token + "&" // 用户标识
//                    + SERVICE + "=" + requestUrl + "&" // 服务名称
//                    + TIMESTAMP + "=" + timestamp + "&" // 时间戳
//                    + REQUEST + "=" + oldParamsJson; // request
//            requestMap.put("request", rootMap);
            requestMap.put("service", requestUrl);
//            requestMap.put("sign", SecretTool.generatingSign(validateSign));
            requestMap.put("timestamp", timestamp);
//            if (!TextUtils.isEmpty(token)) {
//                requestMap.put("token", token);
//            }
            //添加公共参数后的Json参数
//            String newJsonParams = gson.toJson(requestMap);
//            Log.d(TAG, "请求参数  \n" + newJsonParams);
//            //构建request 请求
//            request = request.newBuilder()
//                    .removeHeader("Content-Length")//移除原始的Content-Length
//                    .addHeader("Content-Length", "" + newJsonParams.getBytes().length)
//                    //添加组装后的参数Content-Length
//                    .post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
//                            newJsonParams))
//                    .build();
        }
        //////////////////////拼接后的参数///////////////////////////////////
        String paramsAfter = bodyToString(request.body());
        Log.e(TAG, "拼接后的参数 " + paramsAfter);

        Response response = chain.proceed(request);
        Log.e(TAG, "服务器返回参数: " + response.toString());
        //添加打印服务器返回的数据
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            long contentLength = responseBody.contentLength();
            BufferedSource source = responseBody.source();
            source.request(Integer.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            if (contentLength != 0) {
                Log.e(TAG, "服务器返回数据：" + buffer.clone().readString(Charset.forName("UTF-8")));
            }
        }
        return response
                .newBuilder()
                .build();
    }

    private String bodyToString(final RequestBody request) {
        try {
            final Buffer buffer = new Buffer();
            if (request != null) {
                request.writeTo(buffer);
            } else {
                return "";
            }
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
