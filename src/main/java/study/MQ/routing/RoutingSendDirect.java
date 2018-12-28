package study.MQ.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sun.corba.se.impl.orbutil.threadpool.TimeoutException;

import java.io.IOException;

/*上面我用采用了广播的模式进行消息的发送，现在我们采用路由的方式对不同的消息进行过滤*/

/**
 * @PackageName:student.MQ.Routing
 * @ClassName:RoutingSendDirect 发送端代码
 * @Description:TODO
 * @Author: xionghaizhi
 * @create 2018-12-28 16:02
 */
public class RoutingSendDirect
{
    private static final String EXCHANGE_NAME = "direct_logs";

    // 路由关键字
    private static final String[] routingKeys = new String[] {"info", "warning", "error"};

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
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");//注意是direct
        //发送信息
        for (String routingKey : routingKeys)
        {
            String message = "RoutingSendDirect Send the message level:" + routingKey;
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
            System.out.println("RoutingSendDirect Send" + routingKey + "':'" + message);
        }
        channel.close();
        connection.close();
    }
}