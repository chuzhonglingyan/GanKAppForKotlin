package com.yuntian.baselibs.net.result;

/**
 * description 服务器请求异常 .
 * Created by ChuYingYan on 2018/5/1.
 */
public class ServerException extends Exception {

    private int code;

    public int getCode() {
        return code;
    }

    public ServerException(String msg){
        super(msg);
    }

    public ServerException(String msg, int code){
        super(msg);
        this.code=code;
    }


}
