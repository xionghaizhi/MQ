package study.MQ.simpletask;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/*  一个队列的优点就是很容易处理并行化的工作能力，但是如果我们积累了大量的工作，
    我们就需要更多的工作者来处理，这里就要采用分布机制了。*/

/**
 * @PackageName:student.MQ
 * @ClassName:Producer 生产者
 * @Description:TODO
 * @Author: xionghaizhi
 * @create 2018-12-28 14:49
 */
public class Producer
{
    public final static String TASK_QUEUE_NAME = "rabbitMQ.etoak2";

    public static void main(String[] args)
    {
        try
        {
            //创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            //设置RabbitMQ相关信息
            factory.setHost("localhost");
            factory.setUsername("etoak");
            factory.setPassword("etoak");
            factory.setVirtualHost("/");
            factory.setPort(5672);

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
     /* 注1：queueDeclare 第一个参数表示队列名称、第二个参数为是否持久化（true表示是，队列将在服务器重启时生存）、
     第三个参数为是否是独占队列（创建者可以使用的私有队列，断开后自动删除）、第四个参数为当所有消费者客户端连接断开时是否自动删除队列、
     第五个参数为队列的其他参数
    注2：basicPublish第一个参数为交换机名称、第二个参数为队列映射的路由key、第三个参数为消息的其他属性、第四个参数为发送信息的主体*/
            //发送消息到队列中
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            //分发信息
            for (int i = 0; i < 10; i++)
            {
                String message = "Hello RabbitMQ" + i;
                channel.basicPublish("", TASK_QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                System.out.println("NewTask send '" + message + "'");
            }
            //关闭通道和连接
            channel.close();
            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
    }
}