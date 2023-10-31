package com.hannew.connect.location.application.port.in;

import com.hannew.connect.location.domain.PopularKeywordResult;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PopularKeywordUseCase {
    Mono<List<PopularKeywordResult>> getTopKeywords(int limit);
}
