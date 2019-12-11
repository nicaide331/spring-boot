package com.zr.springboot.service.impl;

import com.zr.springboot.commom.Common;
import com.zr.springboot.service.KafkaService;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * @Description
 * @Author chenyisheng
 * @Date2019/12/11 14:38
 */

@Service
public class KafkaServiceImpl implements KafkaService {

    public static AdminClient admin = Common.getAdminClient();

    public static Producer<Integer, String> producer = Common.getProducer();

    @Override
    public boolean sendMassage(Integer key, String value) throws ExecutionException, InterruptedException {

        producer.send(new ProducerRecord<>(Common.TOPIC, key, value)).get();

        return true;
    }
}
