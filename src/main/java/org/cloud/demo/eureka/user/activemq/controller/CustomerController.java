package org.cloud.demo.eureka.user.activemq.controller;

import org.cloud.demo.eureka.user.activemq.common.ActiveManager;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/test/producer", produces = "application/json")
public class CustomerController {
    /**
     * 注入ActiveManager
     */
    @Autowired
    ActiveManager activeManager;

    /**
     * 新增消息队列
     */
    @RequestMapping(value = "/add/queue", method = RequestMethod.GET)
    public void addQueue() {
        Destination destination = new ActiveMQQueue("yilipocQueueTest");
        //传入队列以及值
        activeManager.send(destination, "success");
    }

    /**
     * 新增消息队列
     */
    @RequestMapping(value = "/add/delay/queue", method = RequestMethod.GET)
    public void addDelayQueue() {
        System.out.println(LocalDateTime.now());
        activeManager.delaySend("success", "yilipocDelayQueueTest", 5000L);
    }
}
