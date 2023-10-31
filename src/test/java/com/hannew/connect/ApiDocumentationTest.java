package com.hannew.connect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hannew.connect.location.adapter.in.PlaceSearchController;
import com.hannew.connect.location.adapter.in.PopularKeywordController;
import com.hannew.connect.location.application.helper.HTMLTagRemover;
import com.hannew.connect.location.application.service.PlaceSearchService;
import com.hannew.connect.location.application.service.PopularKeywordService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = {
        PlaceSearchController.class,
        PopularKeywordController.class
})
@Import({HTMLTagRemover.class})
@AutoConfigureRestDocs
public abstract class ApiDocumentationTest {
    @Autowired
    protected WebTestClient webTestClient;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    protected PlaceSearchService placeSearchService;
    @MockBean
    protected PopularKeywordService popularKeywordService;
}