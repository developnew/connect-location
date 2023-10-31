package com.hannew.connect.location.application.port.out;

import reactor.core.publisher.Mono;

public interface UpdateKeywordCountPort {
    Mono<Double> updateKeywordCount(String keyword);
}
