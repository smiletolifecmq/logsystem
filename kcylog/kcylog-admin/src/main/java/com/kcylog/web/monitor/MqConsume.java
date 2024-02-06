package com.kcylog.web.monitor;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MqConsume {
    /**
     * 监听一个简单的队列，队列不存在时候会创建
     * @param content 消息
     */
    @RabbitListener(queuesToDeclare = @Queue(name = "FQ_INVOKE_QUEUE"))
    public void consumerSimpleMessage(String content, Message message, Channel channel) throws IOException {
        try {
            // 手动确认消息消费成功
            // 通过Message对象解析消息
            String messageStr = new String(message.getBody());
            System.out.println("通过Message:{}" + messageStr);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 手动确认消息消费成功
        }catch (IOException e) {
            // 处理其他确认失败的情况
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true); // 手动确认消息消费失败
        }
    }
}
