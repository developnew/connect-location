package com.hannew.connect.location.application.port.out;

import org.springframework.data.redis.core.ZSetOperations;
import reactor.core.publisher.Flux;

public interface FetchTopKeywordsPort {
    Flux<ZSetOperations.TypedTuple<String>> getTopKeywords(int limit);
}
