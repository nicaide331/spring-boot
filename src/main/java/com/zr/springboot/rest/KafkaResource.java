package com.zr.springboot.rest;

import com.zr.springboot.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@CrossOrigin
@Component
@Path("/kafka")
//@RestController
public class KafkaResource extends BaseResource{

    @Autowired
    private KafkaService kafkaService;

    @POST
    @Path("/massages")
//    @PostMapping("/massages")
    public boolean sendMassage(@QueryParam("key")Integer key, @QueryParam("value")String value) throws Exception {
        kafkaService.sendMassage(key, value);
        return true;
    }
}
