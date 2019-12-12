package com.zr.springboot.config;

import java.util.Objects;

/**
 * 描述
 *
 * @author nicaide
 * @date 2019年12月12日 08:45:00
 */
public class Message {

    private Integer partition;

    private String value;

    public Integer getPartition() {
        return partition;
    }

    public void setPartition(Integer partition) {
        this.partition = partition;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
