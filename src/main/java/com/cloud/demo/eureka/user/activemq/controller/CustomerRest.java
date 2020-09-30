package com.cloud.demo.eureka.user.activemq.controller;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerRest {
    /**
     * 监听方
     */
    //监听注解
    @JmsListener(destination = "yilipocQueueTest")
    public void getQueue(String info) {
        System.out.println("成功监听yilipocQueueTest消息队列，传来的值为:" + info);
    }

    //
    @JmsListener(destination = "yilipocDelayQueueTest")
    public void getDelayQueue(String info) {
        System.out.println("成功监听yilipocDelayQueueTest消息队列，传来的值为:" + info + "当前时间为" + LocalDateTime.now());
    }
}
