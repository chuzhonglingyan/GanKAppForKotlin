package com.yuntian.baselibs.net.entity.rep;

import com.google.gson.annotations.SerializedName;

/**
 * description  .
 * Created by ChuYingYan on 2018/5/1.
 */
public  class BaseResponse<T> {


    @SerializedName(value = "code", alternate = {"status"})
    private int code = -1;

    @SerializedName(value = "msg", alternate = {"info", "errMsg"})
    private String msg;

    @SerializedName(value = "data", alternate = {"results", "result"})
    private T data;

    private boolean error;


    public boolean success() {
        return 99 == code||!error;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
