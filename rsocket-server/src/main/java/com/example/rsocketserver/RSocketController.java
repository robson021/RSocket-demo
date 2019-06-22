package com.example.rsocketserver;

import com.example.common.RequestData;
import com.example.common.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class RSocketController {

    private static final Logger log = LoggerFactory.getLogger(RSocketController.class);

    @MessageMapping("time")
    public Mono<ResponseData> getTime(RequestData data) {
        String timestamp = String.valueOf(System.currentTimeMillis());

        log.info("Got current time request with UUID: {} and text: {}. Returning: {}",
                data.getUuid(), data.getText(), timestamp);

        ResponseData response = new ResponseData();
        response.setResponse(timestamp);

        return Mono.just(response);
    }

}
