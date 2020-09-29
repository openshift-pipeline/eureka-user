package org.cloud.demo.eureka.user;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
 
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class app1 {

	public static void main( String[] args )
    {
		receiver();
    }
	public static void receiver() {
    	ConnectionFactory connectionFactory;
        Connection connection = null;
        Session session;
        Destination destination;
        MessageConsumer consumer;
        connectionFactory = new ActiveMQConnectionFactory(
        		"admin",
                "admin",
                "tcp://192.168.137.7:61616");
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("FirstQueue");
            consumer = session.createConsumer(destination);
            while (true) {
                //设置接收者接收消息的时间，
                TextMessage message = (TextMessage) consumer.receive(1000);
                if (null != message) {
                	//如果生产者设置消息确认模式为CLIENT_ACKNOWLEDGE 则需要客户端进行确认
                	/*
                	try {
                    	message.acknowledge();
					} catch (Exception e) {
						//确认消息过程中发生异常进行处理
					}
                	 */
                    System.out.println("收到消息:" + message.getText());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != connection){
                    connection.close();
                }
            } catch (Throwable ignore) {
            }
        }
    }
}
