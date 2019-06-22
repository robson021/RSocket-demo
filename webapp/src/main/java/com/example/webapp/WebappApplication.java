package com.example.webapp;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class WebappApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebappApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> routes(RequestHandler handler) {
        return RouterFunctions.route()
                .GET("/", request -> ok().syncBody("hello"))
                .GET("/time", handler::handleTime)
                .build();
    }

    @Bean
    public RSocket rSocket() {
        return RSocketFactory.connect()
                .mimeType(MimeTypeUtils.APPLICATION_JSON_VALUE, MimeTypeUtils.APPLICATION_JSON_VALUE)
                .frameDecoder(PayloadDecoder.ZERO_COPY)
                .transport(TcpClientTransport.create(7000))
                .start()
                .block();
    }

    @Bean
    RSocketRequester rSocketRequester(RSocketStrategies rSocketStrategies) {
        MimeType dataMimeType = MimeTypeUtils.APPLICATION_JSON;
        MimeType metadataMimeType = MimeTypeUtils.parseMimeType("message/x.rsocket.composite-metadata.v0");
        return RSocketRequester.wrap(rSocket(), dataMimeType, metadataMimeType, rSocketStrategies);
    }
}
