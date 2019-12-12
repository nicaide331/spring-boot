package com.zr.springboot.commom;

import com.zr.springboot.socket.WebSocket;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Description
 * @Author chenyisheng
 * @Date2019/11/29 16:37
 */
public class Common {

//    static String BOOTSTRAP_SERVERS_CONFIG = "47.106.221.166:8101,120.79.158.226:8101,39.105.161.141:8101";
    public static String BOOTSTRAP_SERVERS_CONFIG = "47.100.138.3:9092,47.105.168.40:9092,123.56.97.36:9092";
    public static String GROUP = "group";
    public static String TOPIC = "topic";
    public static int PARTITION = 0;

    public static Properties getProducerProperties () {

        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS_CONFIG);
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.zr.springboot.config.KeyPartitioner");

        props.put("acks", "1"); //消息生产持久性，获取发送的响应
        props.put("retries", 3);//发送失败重试次数
        props.put("batch.size", 323840);//消息量达到这个大小开始发送数据
        props.put("linger.ms", 10);//消息发送延迟
        props.put("buffer.memory", 3354432);//缓存消息的缓冲区大小
        props.put("max.block.ms", 3000);

        return props;
    }

    public static Properties getConsumerProperties (String groupId) {

        Properties props = new Properties();
        //以下为必须指定参数
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS_CONFIG);

        props.put("group.id", groupId);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        props.put("enable.auto.commit", "false");// 是否自动提交位移
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");//消费位置（服务端无位移或位移过界）

        return props;
    }

    public static AdminClient getAdminClient() {
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);

        return AdminClient.create(config);
    }

    public static Producer<Integer, String> getProducer() {

        return new KafkaProducer<Integer, String>(getProducerProperties());
    }

    public static KafkaConsumer<Integer, String> getConsumer() {

        return new KafkaConsumer<>(getConsumerProperties(GROUP));
    }

    /**
     * 启动消费者
     * @param consumer 消费者
     * @param webSocketSet
     * @param topic 主题
     * @param partition 分区
     */
    public static void startConsumer(KafkaConsumer<Integer, String> consumer, CopyOnWriteArraySet<WebSocket> webSocketSet, String topic, int partition) {

        TopicPartition p = new TopicPartition(topic,partition);
        consumer.assign(Arrays.asList(p));

        try {
            while (true) {
                //获取生产消费的信息
                ConsumerRecords<Integer, String> records = consumer.poll(Long.MAX_VALUE);

                //将消息加到StringBuffer
                StringBuffer massages = new StringBuffer();
                for (ConsumerRecord<Integer, String> record : records){
                    massages.append(record.value());
                    massages.append(".");
                }

                //发送Message
                for (WebSocket webSocket:webSocketSet){
                        webSocket.sendMessage(massages.toString());
                }

                //消费者提交位移
                consumer.commitSync();
            }
        } catch (WakeupException e) {

        }finally {
            consumer.close();
        }
    }

}
