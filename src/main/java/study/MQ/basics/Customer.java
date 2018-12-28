package study.MQ.basics;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
/*前面代码我们可以看出和生成者一样的，后面的是获取生产者发送的信息，
    其中envelope主要存放生产者相关信息（比如交换机、路由key等）body是消息实体*/

/**
 * @PackageName:student.MQ
 * @ClassName:Customer 消费者
 * @Description:TODO
 * @Author: xionghaizhi
 * @create 2018-12-28 14:50
 */
public class Customer
{
    private final static String QUEUE_NAME = "rabbitMQ.etoak";

    public static void main(String[] args)
        throws IOException, java.util.concurrent.TimeoutException
    {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ地址
        factory.setHost("localhost");
        factory.setUsername("etoak");
        factory.setPassword("etoak");
        factory.setVirtualHost("/");
        factory.setPort(5672);
        //创建一个新的连接
        Connection connection = factory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        //声明要关注的队列
        channel.queueDeclare(QUEUE_NAME, false, false, true, null);
        System.out.println("Customer Waiting Received messages");
        //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
        // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
        Consumer consumer = new DefaultConsumer(channel)
        {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                AMQP.BasicProperties properties, byte[] body)
                throws IOException
            {
                String message = new String(body, "UTF-8");
                System.out.println("Customer Received '" + message + "'");
            }
        };
        //自动回复队列应答 -- RabbitMQ中的消息确认机制
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}