package com.linchtech.upload.controller;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class ReturnResult implements Serializable {
	
	private static final long serialVersionUID = -4254726102616289056L;
    private Integer status;		// 响应业务状态
    private String msg;			// 响应消息
    private Object data;		// 响应中的数据
    private long total;			//总条数
    private int pages; 			//总页数
    
    public ReturnResult(){
    }
    
    // 办理业务成功(有返回信息和数据)
    public static ReturnResult success(String msg, Object data){
    	return new ReturnResult(200, msg, data);
    }
    
    // 办理业务成功(只返回数据，无信息)
    public static ReturnResult success(Object data){
    	return new ReturnResult(200, null, data);
    }
    
    // 办理业务成功(只返回状态码)
    public static ReturnResult success(){
    	return new ReturnResult(200, null, null);
    }
    
    // 返回数据和相应的分页信息
    public static ReturnResult pageOk(String msg, Object data, long total, int pages){
    	return new ReturnResult(200, msg, data, total, pages);
    }
    
    // 返回数据和相应的分页信息
    public static ReturnResult pageOk(Object data, long total, int pages){
    	return new ReturnResult(200, null, data, total, pages);
    }
    
    // 办理业务失败
    public static ReturnResult fail(String msg){
    	return new ReturnResult(300, msg, new ArrayList());
    }
    
    // 办理业务失败
    public static ReturnResult fail(String msg, Object data){
    	return new ReturnResult(300, msg, data);
    }
    
    // 参数错误
    public static ReturnResult error(String msg) {
        return new ReturnResult(400, msg, null);
    }
    
    // 插入数据出现异常或者运行时产生的异常
    public static ReturnResult exception(String msg) {
        return new ReturnResult(999, msg, new ArrayList());
    }
 
    public ReturnResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    
    public ReturnResult(Integer status, String msg, Object data,String session) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    
    public ReturnResult(Integer status,String msg, Object data, long total, int pages) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.total = total;
        this.pages=pages;
    }
 
    public ReturnResult(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
 
}
