package com.example.webapp;

import com.example.common.RequestData;
import com.example.common.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class RequestHandler {

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private final RSocketRequester rSocketRequester;

    public RequestHandler(RSocketRequester rSocketRequester) {
        this.rSocketRequester = rSocketRequester;
    }

    public Mono<ServerResponse> handleTime(ServerRequest request) {
        log.info("Requesting timestamp...");
        RequestData data = new RequestData();
        data.setText("hello");
        return rSocketRequester.route("time")
                .data(data)
                .retrieveMono(ResponseData.class)
                .flatMap(response -> {
                    String timestamp = response.getResponse();
                    log.info("Received timestamp: {}", timestamp);
                    return ServerResponse.ok().syncBody(timestamp);
                });
    }
}
