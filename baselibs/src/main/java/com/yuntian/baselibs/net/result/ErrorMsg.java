package com.yuntian.baselibs.net.result;

import com.blankj.utilcode.util.LogUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * description 处理错误信息.
 * Created by ChuYingYan on 2018/5/2.
 */
public class ErrorMsg {

    private String error = "";
    private int code;

    public static final int ERR_NULL=10000;

    public String getError() {
        return error;
    }

    public int getCode() {
        return code;
    }


    public String handleException(Throwable e) {
        //服务器正常200 ,非99错误信息
        if (e instanceof ServerException) {
            error = e.getMessage();
            code = ((ServerException) e).getCode();
        }//服务器异常信息
        else if (e instanceof HttpException) {
            error = HttpErrorMsg(e);
            code = ((HttpException) e).code();
        } else {
            error = LoaclError(e);
        }
        return error;
    }


    private String HttpErrorMsg(Throwable e) {
        String error = "";
        HttpException ht = (HttpException) e;
        switch (ht.code()) {
            case 400:
                error = "请求参数有误"; // signature Attestation of failure
                break;
            case 403:
                error = "请求被拒绝";
                break;
            case 404:
                error = "资源未找到";
                break;
            case 405:
                error = "请求方式不被允许";
                break;
            case 408:
                error = "请求超时";
                break;
            case 422:
                error = "请求语义错误";
                break;
            case 500:
                error = "服务器逻辑错误";
                break;
            case 502:
                error = "服务器网关错误";
                break;
            case 504:
                error = "服务器网关超时";
                break;
            default:
                error = ht.message();
                break;
        }
        return error;
    }


    private String LoaclError(Throwable e) {
        String error = "";
        if (e instanceof UnknownHostException || e instanceof ConnectException) {
            error = "网络无法连接";
        } else if (e instanceof SocketTimeoutException) {
            error = "网络访问超时";
        } else if (e instanceof RuntimeException) {
            error = "网络访问异常";
        } else {
            error = "网络错误，请检查网络连接";
        }
        if (e != null) {
            LogUtils.d("Error:" + e.getLocalizedMessage());
        }
        return error;
    }


}
