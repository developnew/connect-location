package com.hannew.connect.location.application.service;

import com.hannew.connect.location.domain.PopularKeywordResult;
import com.hannew.connect.location.application.port.in.PopularKeywordUseCase;
import com.hannew.connect.location.application.port.out.FetchTopKeywordsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PopularKeywordService implements PopularKeywordUseCase {

    private final FetchTopKeywordsPort fetchTopKeywordsPort;
    @Override
    public Mono<List<PopularKeywordResult>> getTopKeywords(int limit) {
        return fetchTopKeywordsPort.getTopKeywords(limit)
                .collectList()
                .map(list -> list.stream()
                        .map(tuple -> new PopularKeywordResult(tuple.getValue(), tuple.getScore()))
                        .collect(Collectors.toList())
                );
    }
}
