package com.hannew.connect.location.application.port.out;

import com.hannew.connect.location.domain.PlaceSearchResult;
import reactor.core.publisher.Mono;


public interface LoadKakaoPort {
    Mono<PlaceSearchResult> searchByKeyword(String keyword, int limit);
}
