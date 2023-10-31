package com.hannew.connect.location.application.port.in;

import com.hannew.connect.location.domain.PlaceInfo;
import reactor.core.publisher.Mono;

import java.util.List;

public interface KeywordSearchUseCase {
    Mono<List<PlaceInfo>> searchByKeyword(String keyword, int limit);
}
