package com.zr.springboot.config;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author chenyisheng
 * @Date2019/12/4 8:47
 */
public class KeyPartitioner implements Partitioner {

    @Override
    public int partition(String topic, Object keyObj, byte[] keyBytes, Object valueObj, byte[] valueBytes, Cluster cluster) {

        Integer key = (Integer) keyObj;
        List<PartitionInfo> partitionInfos = cluster.availablePartitionsForTopic(topic);
        int pCount = partitionInfos.size();

        return key == null ? 0 : key % pCount;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
