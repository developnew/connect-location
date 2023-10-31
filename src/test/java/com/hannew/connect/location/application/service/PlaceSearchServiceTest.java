package com.hannew.connect.location.application.service;

import com.hannew.connect.ApiDocumentationTest;
import com.hannew.connect.location.domain.PlaceInfo;
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

class PlaceSearchServiceTest extends ApiDocumentationTest {

    @Test
    void searchByKeyword() {

        //given
        String keyword = "이오스안과의원";

        given(placeSearchService.searchByKeyword(keyword, 5))
                .willReturn(Mono.just(placeInfoResponseMock()));

        //when & then
        webTestClient.get().uri(
                uriBuilder -> uriBuilder.path("v1/location/search")
                        .queryParam("keyword", keyword)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(
                        document(
                                "location/search",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                responseFields(
                                        beneathPath("data").withSubsectionId("data"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("장소명"),
                                        fieldWithPath("address").type(JsonFieldType.STRING).description("주소")
                                )
                        )
                );
    }

    private List<PlaceInfo> placeInfoResponseMock() {
        List<PlaceInfo> dataList = new ArrayList<>();
        dataList.add(new PlaceInfo("강남이오스안과의원", "서울 서초구 서초동 1329"));
        return dataList;
    }
}

