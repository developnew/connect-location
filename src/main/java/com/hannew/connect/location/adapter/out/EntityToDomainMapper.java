package com.hannew.connect.location.adapter.out;

import com.hannew.connect.location.domain.PlaceSearchResult;
import com.hannew.connect.location.domain.PlaceInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface EntityToDomainMapper {
    EntityToDomainMapper INSTANCE = Mappers.getMapper(EntityToDomainMapper.class);

    @Mapping(target = "placeInfos", expression = "java(mapKakaoResultToPlaceInfos(kakaoResult))")
    PlaceSearchResult kakaoEntityToDomainVO(KakaoResult kakaoResult);

    @Mapping(target = "placeInfos", expression = "java(mapNaverResultToPlaceInfos(naverResult))")
    PlaceSearchResult naverEntityToDomainVO(NaverResult naverResult);

    default List<PlaceInfo> mapKakaoResultToPlaceInfos(KakaoResult kakaoResult) {
        List<PlaceInfo> placeInfos = new ArrayList<>();
        if (kakaoResult.getDocuments() != null && !kakaoResult.getDocuments().isEmpty()) {
            for (KakaoResult.Document document : kakaoResult.getDocuments()) {
                PlaceInfo placeInfo = new PlaceInfo();
                placeInfo.setAddress(document.getAddressName());
                placeInfo.setName(document.getPlaceName());
                placeInfos.add(placeInfo);
            }
        }
        return placeInfos;
    }

    default List<PlaceInfo> mapNaverResultToPlaceInfos(NaverResult naverResult) {
        List<PlaceInfo> placeInfos = new ArrayList<>();
        if (naverResult.getItems() != null && !naverResult.getItems().isEmpty()) {
            for (NaverResult.Item item : naverResult.getItems()) {
                PlaceInfo placeInfo = new PlaceInfo();
                placeInfo.setAddress(item.getAddress());
                placeInfo.setName(item.getTitle());
                placeInfos.add(placeInfo);
            }
        }
        return placeInfos;
    }
}