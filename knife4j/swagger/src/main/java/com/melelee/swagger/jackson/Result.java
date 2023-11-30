package com.melelee.swagger.jackson;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 接口返回数据格式
 */
@Data
@AllArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功标志
     */
    private boolean success = true;

    /**
     * 返回处理消息
     */
    private String message = "操作成功！";

    /**
     * 返回代码
     */
    private Integer code = 200;

    /**
     * 返回数据对象 data
     */
    private T result;
}