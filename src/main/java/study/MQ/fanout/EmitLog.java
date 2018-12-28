package study.MQ.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*在上一篇说到的队列都指定了名称，但是现在我们不需要这么做，我们需要所有的日志信息，
    而不只是其中的一个。如果要做这样的队列，我们需要2件事，一个就是获取一个新的空的队列，
    这样我就需要创建一个随机名称的队列，最好让服务器帮我们做出选择，第一个就是我们断开用户的队列，
    应该自动进行删除。ok下面是一副工作图。*/

/**
 * @PackageName:student.MQ.fanout
 * @ClassName:EmitLog 信息发送端代码
 * @Description:TODO
 * @Author: xionghaizhi
 * @create 2018-12-28 15:54
 */
public class EmitLog
{
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args)
        throws IOException, TimeoutException, TimeoutException
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("etoak");
        factory.setPassword("etoak");
        factory.setVirtualHost("/");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");//fanout表示分发，所有的消费者得到同样的队列信息
        //分发信息
        for (int i = 0; i < 5; i++)
        {
            String message = "Hello World" + i;
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println("EmitLog Sent '" + message + "'");
        }
        channel.close();
        connection.close();
    }
}