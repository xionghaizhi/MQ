## 项目说明
一、MQ 基础学习

二、学习线路  fanout->simpletask->fanout->routing->topics

## 项目组织结构规范说明：
* ├─src 源码根目录
* │  ├─main 主程序目录
* │  │  ├─java  源程序目录
* │  │  │  ├─study  项目包一级结构目录
* │  │  │  │  ├─ MQ 项目包二级结构目录
* │  │  │  │  │  ├─fanout  基础链接
* │  │  │  │  │  │  ├─Customer   消费者
* │  │  │  │  │  │  ├─Producer   生产者
* │  │  │  │  │  ├─fanout  发布/订阅
* │  │  │  │  │  │  ├─EmitLog        信息发送端代码
* │  │  │  │  │  │  ├─ReceiveLogs1   消费者代码
* │  │  │  │  │  ├─ routing   路由方式
* │  │  │  │  │  │  ├─ReceiveLogsDirect1   消费者代码1
* │  │  │  │  │  │  ├─ReceiveLogsDirect2   消费者代码2
* │  │  │  │  │  │  ├─RoutingSendDirect    发送端代码
* │  │  │  │  │  ├─ simpletask   基础任务分发
* │  │  │  │  │  │  ├─Producer   生产者
* │  │  │  │  │  │  ├─Work1      消费者代码1
* │  │  │  │  │  │  ├─Work2      消费者代码2
* │  │  │  │  │  ├─ topics   模糊匹配
* │  │  │  │  │  │  ├─ReceiveLogsTopic1    消费者代码1
* │  │  │  │  │  │  ├─ReceiveLogsTopic2    消费者代码2
* │  │  │  │  │  │  ├─TopicSend            发送端
* │  │  ├─resource  全局项目资源配置入口目录
* │  ├─test 测试用例程序目录
* ├─your_project_name.sql 数据库建表语句备份文件


