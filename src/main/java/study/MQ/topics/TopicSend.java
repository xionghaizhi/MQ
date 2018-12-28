package study.MQ.topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*这种应该属于模糊匹配
    * ：可以替代一个词
    #：可以替代0或者更多的词
    现在我们继续看看代码来理解*/

/**
 * @PackageName:student.MQ.topics
 * @ClassName:TopicSend 发送端
 * @Description:TODO
 * @Author: xionghaizhi
 * @create 2018-12-28 16:06
 */
public class TopicSend
{
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args)
        throws IOException, TimeoutException
    {
        Connection connection = null;
        Channel channel = null;
        try
        {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setUsername("etoak");
            factory.setPassword("etoak");
            factory.setVirtualHost("/");
            factory.setPort(5672);
            connection = factory.newConnection();
            channel = connection.createChannel();

            //声明一个匹配模式的交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            //待发送的消息
            String[] routingKeys = new String[] {
                "quick.orange.rabbit",
                "lazy.orange.elephant",
                "quick.orange.fox",
                "lazy.brown.fox",
                "quick.brown.fox",
                "quick.orange.male.rabbit",
                "lazy.orange.male.rabbit"
            };
            //发送消息
            for (String severity : routingKeys)
            {
                String message = "From " + severity + " routingKey' s message!";
                channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
                System.out.println("TopicSend Sent '" + severity + "':'" + message + "'");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            if (connection != null)
            {
                channel.close();
                connection.close();
            }
        }
        finally
        {
            if (connection != null)
            {
                channel.close();
                connection.close();
            }
        }
    }
}
