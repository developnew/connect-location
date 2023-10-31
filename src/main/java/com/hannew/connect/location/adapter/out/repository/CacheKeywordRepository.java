package com.hannew.connect.location.adapter.out.repository;

import com.hannew.connect.location.application.port.out.FetchTopKeywordsPort;
import com.hannew.connect.location.application.port.out.UpdateKeywordCountPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CacheKeywordRepository implements UpdateKeywordCountPort, FetchTopKeywordsPort {

    private final ReactiveRedisTemplate<String, String> keywordCountRedisTemplate;

    @Override
    public Flux<ZSetOperations.TypedTuple<String>> getTopKeywords(int limit) {
        Range<Long> range = Range.closed(0L, (long) (limit - 1));
        return keywordCountRedisTemplate.opsForZSet().reverseRangeWithScores("keywordsCounts", range);
    }

    @Override
    public Mono<Double> updateKeywordCount(String keyword) {
        return keywordCountRedisTemplate.opsForZSet().incrementScore("keywordsCounts", keyword, 1);
    }
}
