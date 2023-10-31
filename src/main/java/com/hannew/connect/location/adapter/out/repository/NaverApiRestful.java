package com.hannew.connect.location.adapter.out.repository;

import com.hannew.connect.location.adapter.out.EntityToDomainMapper;
import com.hannew.connect.location.adapter.out.NaverResult;
import com.hannew.connect.location.application.port.out.LoadNaverPort;
import com.hannew.connect.location.domain.PlaceSearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class NaverApiRestful implements LoadNaverPort {

    private final WebClient naverLocationWebClient;

    public Mono<PlaceSearchResult> searchByKeyword(String keyword, int limit) {

        return naverLocationWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", keyword)
                        .queryParam("display", 5)
                        .build())
                .retrieve()
                .bodyToMono(NaverResult.class)
                .map(result -> EntityToDomainMapper.INSTANCE.naverEntityToDomainVO(result))
                .onErrorResume(ex -> Mono.empty());
    }
}
