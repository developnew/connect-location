package com.hannew.connect.location.application.service;

import com.hannew.connect.location.application.port.in.KeywordSearchUseCase;
import com.hannew.connect.location.application.port.out.LoadKakaoPort;
import com.hannew.connect.location.application.port.out.LoadNaverPort;
import com.hannew.connect.location.application.port.out.UpdateKeywordCountPort;
import com.hannew.connect.location.domain.PlaceInfo;
import com.hannew.connect.location.domain.PlaceSearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;

import static com.hannew.connect.location.application.helper.HTMLTagRemover.removeHtmlTagsAndSpace;

@Service
@RequiredArgsConstructor
public class PlaceSearchService implements KeywordSearchUseCase {

    private final LoadKakaoPort loadKakaoPort;
    private final LoadNaverPort loadNaverPort;
    private final UpdateKeywordCountPort updateKeywordCountPort;

    @Override
    public Mono<List<PlaceInfo>> searchByKeyword(String keyword, int limit) {

        return updateKeywordCountPort.updateKeywordCount(keyword).flatMap(then ->
                Mono.zip(loadKakaoPort.searchByKeyword(keyword, limit),
                                loadNaverPort.searchByKeyword(keyword, limit))
                        .flatMap(tuple -> {
                            PlaceSearchResult kakaoResult = tuple.getT1();
                            PlaceSearchResult naverResult = tuple.getT2();

                            Map<String, PlaceInfo> kakaoMap = new LinkedHashMap<>();
                            Map<String, PlaceInfo> naverMap = new LinkedHashMap<>();

                            for (PlaceInfo kakaoPlace : kakaoResult.getPlaceInfos()) {
                                kakaoMap.put(removeHtmlTagsAndSpace(kakaoPlace.getName()), kakaoPlace);
                            }

                            for (PlaceInfo naverPlace : naverResult.getPlaceInfos()) {
                                naverMap.put(removeHtmlTagsAndSpace(naverPlace.getName()), naverPlace);
                            }

                            //Add Common elements.
                            HashMap<String, PlaceInfo> finalResults = new LinkedHashMap<>();
                            for (Map.Entry<String, PlaceInfo> entry : kakaoMap.entrySet()) {
                                if (naverMap.containsKey(entry.getKey()) && !finalResults.containsKey(entry.getKey())) {
                                    finalResults.put(entry.getKey(), entry.getValue());
                                }
                            }
                            //Add KaKao results.
                            for (Map.Entry<String, PlaceInfo> entry : kakaoMap.entrySet()) {
                                if (!finalResults.containsKey(entry.getKey())) {
                                    finalResults.put(entry.getKey(), entry.getValue());
                                }
                            }
                            //Add Naver results.
                            for (Map.Entry<String, PlaceInfo> entry : naverMap.entrySet()) {
                                if (!finalResults.containsKey(entry.getKey())) {
                                    finalResults.put(entry.getKey(), entry.getValue());
                                }
                            }
                            return Mono.just(new ArrayList<>(finalResults.values()));
                        }));
    }
}
