## Connect-Location

### 요구 사항 :
1. 장소 검색 - 카카오,네이버 API를 통해 각각 최대 5개씩, 총 10개의 키워드 관련 장소를 검색합니다.
2. 검색 키워드 목록 - 사용자들이 많이 검색한 순서대로 최대 10개 목록을 제공합니다.


### 프로젝트 문서 확인 :
1. ./gradlew test
2. 생성된 RestDoc adoc문서를 확인합니다. 다음 경로에서 확인할 수 있습니다.
```
com.hannew.connect.documentation.location.adoc
```
3.장소검색
```
$ curl 'http://localhost:8080/v1/location/search?keyword=%EC%9D%B4%EC%98%A4%EC%8A%A4%EC%95%88%EA%B3%BC%EC%9D%98%EC%9B%90' -i -X GET \
-H 'Accept: application/json'
```
4.인기검색어
```
$ curl 'http://localhost:8080/v1/location/popular_keywords' -i -X GET \
-H 'Accept: application/json'
```

### 구현 방법 :
1. 패키지 구조
```plaintext
com.connect.location
|-- adapter
| |-- configurations  // 외부설정 관련
| |-- in  // controller해당
| |-- out  // 아웃고잉포트 구현, Entity 및 도메인 모델 변환 Mapper도 포함하였습니다.
| | |-- repository // Cache 및 외부 API repository가 있습니다.
|-- application 
| |-- service // 비지니스 로직 집중
| |-- port  //adapter와 상호작용하기 위해 제공하는 I/F
| | |-- in  // 인커밍포트 인터페이스, 유즈케이스 
| | |-- out // 외부 접근할때 사용하는 인터페이스, 실제 구현은 adapter에 있습니다.
|-- domain //도메인 모델 (VO)
```
위와 같이 설계한 이유는 
- 각 계층이 명확하게 정의되어있기 때문에 새로운 기능이나 요구사항을 쉽게 수용할 수 있습니다.
- 예를 들어 Adapter에는 외부 시스템과의 통신과 관련된 모든 로직 기능을 담당합니다. application 코어는 어댑터가 아닌 포트를 호출함으로써 어댑터에 의존성을 갖지 않도록 하였습니다.
- Service에서는 Port I/F 주입받아 사용하기 때문에 구글 API로 대체되더라도 향후 Adapter만 교체하길 기대하였습니다.

2. Webflux 선택
- WebFlux는 논블로킹 모델을 채택하여 동시 연결 수에 대해 더 큰 확장성을 제공합니다. 따라서 많은 양의 요청을 처리해야 하는 경우 MVC에 비해 더 뛰어난 성능을 보입니다. 외부 API 호출을 효율적으로 처리하기 위해 Webflux의 WebClient를 이용하였습니다.
    
3. 장소검색
- 카카오와 네이버 API는 Mono.zip을 사용하여 병렬로 실행되도록 했으며 하나라도 오류가 발생하면 전체 작업이 실패할 수 있으므로 빈 리스트를 리턴하도록 처리하였습니다.
- 아무래도 여러 Mono를 조합하면 코드 가독성이 좋지 않기 때문에 entity -> domain vo 매핑은 mapstruct를 활용, 유틸은 별도로 분리하여 가독성을 유지하도록 노력하였습니다.

4. 인기 검색어 조회
- Redis의 incrementScore메소드는 원자성을 보장하는 명령 중 하나이기에 해당 메서드를 사용하여 검색횟수 증가로직을 구현하였습니다.
- 'Github에서 프로젝트를 받은 뒤  특정 데몬 설치하지 않고 바로 실행될 수 있어야 한다고 생각합니다.'라는 마인드에 공감하여 Embedded Redis를 사용하였습니다. (참고 - https://jojoldu.tistory.com/297)

5. 테스트 코드 작성
 - RestDocs을 사용해 테스트코드와 문서화가 통합될 수 있도록 하였습니다. API 변경되면 문서가 통합되어 있으므로 코드 변경에 따라 문서도 쉽게 업데이트 할수 있습니다. 


### 개선점 :
1. 페이징 처리
- 현재는 최대 10개의 키워드를 리턴하지만 더 많은 결과값을 요구할 수도 있으므로 페이징 처리가 필요합니다. 또한, 카카오 및 네이버 API 역시 각각 5개가 아닌 더 많은 요청 개수로 변경될 수 있기 때문에 다음 페이지까지 조회를 확장하고 조합하도록 구현되어야 합니다. .expand(Function) 활용하여 반복호출하여 응답값을 수집할 수 있다고 하나, 시간 관계상 구현하지 못하였습니다. 
2. Jdk21이 Non-blocking하게 스레드 처리를 하여 높은 처리량을 기대할 수 있다고 하니, 자바버전 업그레이드도 고려해볼 수 있습니다. 또한 Spring Boot 3.2에서는 Webflux 대안으로 RestClient가 추가되었다고 하니 함께 검토해볼 수 있습니다.
3. 운영환경에서는 Redis는 클러스터로 구성하여 고가용성을 확보합니다.

