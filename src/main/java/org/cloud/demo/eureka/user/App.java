package org.cloud.demo.eureka.user;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import redis.clients.jedis.Jedis;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
 
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	sender();
    }
    
    public static void sender() {
    	// 连接工厂
        ConnectionFactory connectionFactory= new ActiveMQConnectionFactory(
               "admin",
                "admin",
                "tcp://192.168.137.7:61616");;
        //JMS 客户端到JMS Provider 的连接
        //发送消息的是生产者producer mq的服务器是提供者provider 接受消息的是消费者
        Connection connection = null;
        // Session： 一个发送或接收消息的线程
        //可以设置事务和确认方式
        Session session = null;
        // Destination ：消息的目的地;消息发送给谁.
        //可以是一个topic 也可以是一个queue
        Destination destination;
        // MessageProducer：消息生产者
        MessageProducer producer;
        // 构造ConnectionFactory实例对象，此处采用ActiveMq的实现jar
        connectionFactory = new ActiveMQConnectionFactory(
               "admin",
                "admin",
        		"tcp://192.168.137.7:61616");;
        try {
            connection = connectionFactory.createConnection();
            // 启动
            connection.start();
            //createSession 第一个参数transacted 表示是否支持事务 
            //第二个参数 acknowledgeMode 有如下几种
            //1.AUTO_ACKNOWLEDGE 自动确认模式，不需客户端进行确认
            //2.CLIENT_ACKNOWLEDGE   客户端进行确认
            //客户端获得message之后需要进行message.acknowledge();
            //3.DUPS_OK_ACKNOWLEDGE  允许重复消息
            session = connection.createSession(Boolean.TRUE,
                    Session.AUTO_ACKNOWLEDGE);
            // 创建队列名为FirstQueue的目的地
            destination = session.createQueue("FirstQueue");
            // 创建将消息发送到该目的地的消息生产者
            producer = session.createProducer(destination);
            // 持久化模式 NON_PERSISTENT不持久化，PERSISTENT持久化
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            TextMessage message = session
                    .createTextMessage("ActiveMq 发送的消息aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            // 发送消息到目的地方
            producer.send(message);
            //也可以这样
            //producer.send(
            //		message,//要发送的消息
            //		DeliveryMode.NON_PERSISTENT,//是否持久化
            //		1,//消息优先级 1-4位普通消息，5-9位加急消息，不严格按照优先级发送，但会保证加急消息优先发送 默认级别为4
            //		1000L//消息过期时间，默认永不过期，单位毫秒
            //		);
            System.out.println("发送消息***");
            session.commit();
        } catch (Exception e) {
        	try {
        		if(null != session){
    				session.rollback();
        		}
			} catch (JMSException e1) {
				e1.printStackTrace();
			}
            e.printStackTrace();
        } finally {
            try {
                if (null != connection)
                    connection.close();
            } catch (Throwable ignore) {
            }
        }

    }
    
    public void redis() {
    	System.out.println( "Hello World!" );
        //连接本地的 Redis 服务
          Jedis jedis = new Jedis("192.168.137.7",6003);
          jedis.auth("123456");
          System.out.println("连接成功"+jedis.get("aaa"));
          
          //查看服务是否运行
          System.out.println("服务正在运行: "+jedis.ping());
          //存储数据到列表中
          jedis.lpush("site-list", "Runoob");
          jedis.lpush("site-list", "Google");
          jedis.lpush("site-list", "Taobao");
          // 获取存储的数据并输出
          List<String> list = jedis.lrange("site-list", 0 ,0);
          for(int i=0; i<list.size(); i++) {
              System.out.println("列表项为: "+list.get(i));
          }
          // 获取数据并输出
          Set<String> keys = jedis.keys("*"); 
          Iterator<String> it=keys.iterator() ;   
          while(it.hasNext()){   
              String key = it.next();   
              System.out.println(key);   
          }
    }
    
    
}
