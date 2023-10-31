package com.hannew.connect.location.adapter.in;

import com.hannew.connect.location.adapter.out.ResponseEntity;
import com.hannew.connect.location.application.port.in.KeywordSearchUseCase;
import com.hannew.connect.location.domain.enumeration.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class PlaceSearchController {

    private final KeywordSearchUseCase keywordSearchUseCase;

    @GetMapping("/v1/location/search")
    public Mono<ResponseEntity> searchLocation(@RequestParam String keyword) {

        if (keyword.isEmpty()) {
            return Mono.just(ResponseEntity.fail(Result.INCORRECT_REQUEST));
        }

        return keywordSearchUseCase.searchByKeyword(keyword, 5)
                .map(ResponseEntity::success);
    }
}
