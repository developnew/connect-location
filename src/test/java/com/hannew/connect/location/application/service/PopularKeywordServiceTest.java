package com.hannew.connect.location.application.service;

import com.hannew.connect.ApiDocumentationTest;
import com.hannew.connect.location.domain.PopularKeywordResult;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static com.hannew.connect.document.ApiDocumentUtils.getDocumentRequest;
import static com.hannew.connect.document.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

class PopularKeywordServiceTest extends ApiDocumentationTest {

    @Test
    void getTopKeywords() {

        given(popularKeywordService.getTopKeywords(10))
                .willReturn(Mono.just(topKeywordsMock()));

        //when & then
        webTestClient.get().uri("v1/location/popular_keywords")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(
                        document(
                                "location/popularKeywords",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                responseFields(
                                        beneathPath("data").withSubsectionId("data"),
                                        fieldWithPath("keyword").type(JsonFieldType.STRING).description("검색키워드"),
                                        fieldWithPath("count").type(JsonFieldType.NUMBER).description("검색횟수")
                                )
                        )
                );

    }

    private List<PopularKeywordResult> topKeywordsMock() {
        List<PopularKeywordResult> keywords = new ArrayList<>();

        keywords.add(new PopularKeywordResult("강남양대창", 2.0));
        keywords.add(new PopularKeywordResult("강남곱창", 1.0));
        keywords.add(new PopularKeywordResult("강남안과", 1.0));
        keywords.add(new PopularKeywordResult("강남내과", 1.0));

        return keywords;
    }
}