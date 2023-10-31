package com.hannew.connect.location.adapter.in;

import com.hannew.connect.location.adapter.out.ResponseEntity;
import com.hannew.connect.location.application.port.in.PopularKeywordUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class PopularKeywordController {

    private final PopularKeywordUseCase popularKeywordUseCase;

    @GetMapping("/v1/location/popular_keywords")
    public Mono<ResponseEntity> getTopKeywords() {
        return popularKeywordUseCase.getTopKeywords(10)
                .map(ResponseEntity::success);
    }
}
