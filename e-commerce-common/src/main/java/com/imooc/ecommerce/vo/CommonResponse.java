package com.imooc.ecommerce.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用响应对象
 * {
 *     "code": 200,
 *     "message": "success",
 *     "data": {}
 * }
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> implements Serializable {
    /*错误码*/
    private Integer code;

    /*错误信息*/
    private String message;

    /*响应的泛型数据*/
    private T data;

    public CommonResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
