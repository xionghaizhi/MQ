package study.MQ.fanout;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.sun.corba.se.impl.orbutil.threadpool.TimeoutException;

import java.io.IOException;

/**
 * @PackageName:student.MQ.fanout
 * @ClassName:ReceiveLogs1 消费者代码
 * @Description:TODO
 * @Author: xionghaizhi
 * @create 2018-12-28 15:55
 */
public class ReceiveLogs1
{
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args)
        throws IOException, TimeoutException, java.util.concurrent.TimeoutException
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("etoak");
        factory.setPassword("etoak");
        factory.setVirtualHost("/");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        //产生一个随机的队列名称
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");//对队列进行绑定

        System.out.println("ReceiveLogs1 Waiting for messages");
        Consumer consumer = new DefaultConsumer(channel)
        {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                byte[] body)
                throws
                IOException
            {
                String message = new String(body, "UTF-8");
                System.out.println("ReceiveLogs1 Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);//队列会自动删除
    }
}
