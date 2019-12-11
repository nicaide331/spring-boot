package com.zr.springboot.service;

import java.util.concurrent.ExecutionException;

public interface KafkaService {

    boolean sendMassage(Integer key, String value) throws ExecutionException, InterruptedException;

}
