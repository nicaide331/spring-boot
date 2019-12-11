package com.zr.springboot.rest;

import com.zr.springboot.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class KafkaResource extends BaseResource{

    @Autowired
    private KafkaService kafkaService;

    @PostMapping("/massages")
    public boolean sendMassage() throws Exception {

        Integer key = 0;
        String value = "value";
        kafkaService.sendMassage(key, value);

        return true;
    }
}
