package com.ljw.gmall.payservice;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.*;

@SpringBootApplication
public class GmallPayserviceApplication {



    public static void main(String[] args) {
        SpringApplication.run(GmallPayserviceApplication.class, args);
   }



}
