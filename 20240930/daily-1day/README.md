
멤버 / 프로젝트 / 프로젝트 멤버

세 가지 모델이 있는 간단한 API를 만들어봅니다.


* [ ] 멤버 locale  프로젝트 type은 enum으로 관리합니다.
    * [ ] 생성 / 조회시 모두 소문자로 통일합니다. 
* [ ] 멤버 / 프로젝트 생성
    * [ ] 멤버, 프로젝트는 각각 id, code는 중복을 허용하지 않습니다. (적절한 HTTP Code - RestControllerAdvice)
* [ ] 멤버 / 프로젝트 리스트를 응답하는 API를 만듭니다.
    * [ ] 각 컨트롤러에서는 `Pageable pageable` 을 받아 페이징 처리를 합니다. 해당 페이지에 기본값은 page=0, size = 5이고 max size는 10입니다.
        * [ ] 관련 테스트 케이스를 추가합니다.
    * [ ]  프로젝트에 createAt은 LocalDate입니다. @JsonComponent을 이용하여 조회시 응답 포맷을 "yyyyMMdd"로 응답합니다.(20240929)
* [ ] 멤버를 프로젝트에 추가 / 프로젝트 멤버 목록을 조회하는 API를 작성합니다. (프로젝트는 여러명에 프로젝트 멤버가 포함되고, 멤버도 여러 프로젝트에 가입이 가능합니다.)
    * [ ] API Path를 고민해보세요. 
* [ ]  멤버 목록 조회 API에서 Accept Header가 text/csv로 들어온 경우 아래 처럼 응답합니다.(기본은 json)
  id,name,class,locale
  1,신건영,A,ko
  2,김건우,B,en
* [ ] 메신저 알림 보내는 기능을 FeignClient을 이용해서 구현해보세요.