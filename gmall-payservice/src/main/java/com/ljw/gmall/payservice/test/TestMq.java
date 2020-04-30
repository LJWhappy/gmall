package com.ljw.gmall.payservice.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;

public class TestMq {
    public static void main(String[] args) {

        ConnectionFactory connect = new ActiveMQConnectionFactory("tcp://localhost:61616");
        try {
            Connection connection = connect.createConnection();
            connection.start();
            //第一个值表示是否使用事务，如果选择true，第二个值相当于选择0
            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);//开始事务的消息

            //Queue testqueue = session.createQueue("TEST1");//队列

            Topic testTopic = session.createTopic("test2");//话题

            MessageProducer producer = session.createProducer(testTopic);
            TextMessage textMessage=new ActiveMQTextMessage();
            textMessage.setText("merry  me！");
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            producer.send(textMessage);
            session.commit();
            //session.rollback();
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
