package com.paxing.mq.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.rabbitmq.client.*;

import java.util.Map;

/**
 * @author wayne-zhang
 * @date 2017/9/4 20:11.
 */
public class RabbitMQService {
    private String mqName = null;
    private Channel channel = null;
    private Connection connection = null;
    private ConnectionFactory factory = null;
    private QueueingConsumer consumer = null;

    public RabbitMQService(String host, String mqName) {
        try {
            this.factory = new ConnectionFactory();
            this.factory.setHost(host);
            this.connection = this.factory.newConnection();
            this.channel = this.connection.createChannel();
            this.mqName = mqName;
            this.channel.queueDeclare(mqName, false, false, false, (Map) null);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public RabbitMQService(String host, String vhost, String username, String password, String mqName) {
        try {
            this.factory = new ConnectionFactory();
            this.factory.setHost(host);
            this.factory.setVirtualHost(vhost);
            this.factory.setUsername(username);
            this.factory.setPassword(password);
            this.connection = this.factory.newConnection();
            this.channel = this.connection.createChannel();
            this.mqName = mqName;
            this.channel.queueDeclare(mqName, false, false, false, (Map) null);
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    public void destroy() {
        try {
            this.channel.close();
            this.connection.close();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public void append(Object value) {
        try {
            this.channel.basicPublish("", this.mqName, (AMQP.BasicProperties) null, JSON.toJSONBytes(value, new SerializerFeature[]{SerializerFeature.WriteClassName}));
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public Object get() {
        try {
            if (this.consumer == null) {
                this.channel.queueDeclare(this.mqName, false, false, false, (Map) null);
                this.consumer = new QueueingConsumer(this.channel);
                this.channel.basicConsume(this.mqName, true, this.consumer);
            }

            QueueingConsumer.Delivery delivery = this.consumer.nextDelivery();
            return delivery != null ? JSON.parse(new String(delivery.getBody())) : delivery;
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public Object get(long timeout) {
        try {
            if (this.consumer == null) {
                this.channel.queueDeclare(this.mqName, false, false, false, (Map) null);
                this.consumer = new QueueingConsumer(this.channel);
                this.channel.basicConsume(this.mqName, true, this.consumer);
            }

            QueueingConsumer.Delivery delivery = this.consumer.nextDelivery(timeout);
            if (delivery != null) {
                this.channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                return JSON.parse(new String(delivery.getBody()));
            } else {
                return delivery;
            }
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }
}
