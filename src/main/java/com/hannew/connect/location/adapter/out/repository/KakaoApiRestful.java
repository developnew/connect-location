package com.hannew.connect.location.adapter.out.repository;

import com.hannew.connect.location.adapter.out.EntityToDomainMapper;
import com.hannew.connect.location.adapter.out.KakaoResult;
import com.hannew.connect.location.application.port.out.LoadKakaoPort;
import com.hannew.connect.location.domain.PlaceSearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class KakaoApiRestful implements LoadKakaoPort {

    private final WebClient kakaoLocationWebClient;

    public Mono<PlaceSearchResult> searchByKeyword(String keyword, int limit) {

        return kakaoLocationWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", keyword)
                        .queryParam("size", 5)
                        .build())
                .retrieve()
                .bodyToMono(KakaoResult.class)
                .map(result -> EntityToDomainMapper.INSTANCE.kakaoEntityToDomainVO(result))
                .onErrorResume(ex -> Mono.empty());
    }
}
