package com.soraohayou.study.handler;

/**
 * @description: 自定义Message
 * @author: admin
 * @date: 2024/8/1
 * @email: 1145338587@qq.com
 */
public class CustomerMessage {

    CustomerMessage next;

    /**
     * 目标Handler
     */
    CustomerHandler mTarget;

    /**
     * 类型
     */
    int type;

    /**
     * 数据
     */
    Object data;

}
