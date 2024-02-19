package com.kcylog.web.monitor;

import com.google.gson.Gson;
import com.kcylog.system.common.MqMessage;
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
     */
    @RabbitListener(queuesToDeclare = @Queue(name = "${spring.rabbitmq.queue}"))
    public void consumerSimpleMessage(Message message, Channel channel) throws IOException {
        try {
            // 手动确认消息消费成功
            // 通过Message对象解析消息
            String messageStr = new String(message.getBody());
            Gson gson = new Gson();
            MqMessage mqMessage = gson.fromJson(messageStr, MqMessage.class);
            String opType = mqMessage.getOpType();
            switch(opType){
                case "TASK_TEMP_ARRANGE" :
                    //任务临时安排
                    break;
                case "TASK_ARRANGE" :
                    //任务正式安排
                    break;
                case "FIRST_CHECK" :
                    //一检
                    break;
                case "SECOND_CHECK" :
                    //二检
                    break;
                case "DELIVERY_REGISTER" :
                    //送件登记
                    break;
                case "STAMP" :
                    //盖章
                    break;
                case "FILE_RELEASE" :
                    //出件
                    break;
                case "MODIFY_WORKLOAD" :
                    //修改工作量
                    break;
            }
            System.out.println("通过Message:{}" + mqMessage.getOpType());
            System.out.println("通过Message:{}" + mqMessage.getProjectId());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 手动确认消息消费成功
        }catch (IOException e) {
            // 处理其他确认失败的情况
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true); // 手动确认消息消费失败
        }
    }
}
