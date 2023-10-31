package com.hannew.connect.location.adapter.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {
    @Value("${kakao.location.url}")
    private String kakaoLocationUrl;

    @Value("${kakao.location.key}")
    private String kakaoLocationKey;

    @Value("${kakao.location.value}")
    private String kakaoLocationValue;

    @Value("${naver.location.url}")
    private String naverLocationUrl;

    @Value("${naver.location.client}")
    private String naverLocationClient;

    @Value("${naver.location.secret}")
    private String naverLocationSecret;

    public HttpClient httpClient(int connectTimeout, int readTimeout, int writeTimeout) {
        return HttpClient.create().tcpConfiguration(client ->
                client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
                        .doOnConnected(conn -> conn
                                .addHandlerLast(new ReadTimeoutHandler(readTimeout))
                                .addHandlerLast(new WriteTimeoutHandler(writeTimeout)))
        );

    }

    @Bean
    public ExchangeStrategies exchangeStrategies(ObjectMapper objectMapper) {
        return ExchangeStrategies
                .builder()
                .codecs(clientDefaultCodecsConfigurer -> {
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON));
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON));

                }).build();
    }

    @Bean
    public WebClient kakaoLocationWebClient(ExchangeStrategies exchangeStrategies) {
        return WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .baseUrl(kakaoLocationUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(kakaoLocationKey, kakaoLocationValue)
                .clientConnector(new ReactorClientHttpConnector(httpClient(1000, 3, 3)))
                .build();
    }

    @Bean
    public WebClient naverLocationWebClient(ExchangeStrategies exchangeStrategies) {
        return WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .baseUrl(naverLocationUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-Naver-Client-Id", naverLocationClient)
                .defaultHeader("X-Naver-Client-Secret", naverLocationSecret)
                .clientConnector(new ReactorClientHttpConnector(httpClient(1000, 3, 3)))
                .build();
    }
}
