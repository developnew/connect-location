package com.hannew.connect.location.adapter.out;

import com.hannew.connect.location.adapter.out.repository.CacheKeywordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
class CacheKeywordRepositoryTest {

    @Autowired
    private CacheKeywordRepository cacheKeywordRepository;

    @Test
    public void testGetTopKeywords() {
        String keyword = "세광양대창";
        StepVerifier.create(cacheKeywordRepository.updateKeywordCount(keyword)
                        .then(cacheKeywordRepository.getTopKeywords(10).collectList())
                )
                .expectNextMatches(results -> {
                    System.out.println("Results: " + results);
                    return true;
                })
                .verifyComplete();
    }
}