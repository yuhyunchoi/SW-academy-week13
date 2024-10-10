
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

--- day2

* [ ] 멤버 / 프로젝트 / 프로젝트 멤버를 레디스에 저장하세요.
  * [ ] 프로젝트는 타입을 구분해서 저장하세요.
* [ ] 시스템에 인증 가능한 사용자를 만드세요. (2명) (InMemoryUser)
  * role: admin,member
* [ ] admin은 항상 member에 권한을 갖을수 있게 만드세요.
* [ ] GET /public-projects API
  * [ ] 멤버 / 어드민 허용
* [ ] GET /private-projects API
  * [ ] 어드민만 허용


--- day3

* [ ] 로그인을 레디스에 있는 정보를 이용해서 로그인 할 수 있도록 수정합니다.
* [ ] 구글 로그인을 지원합니다.(조교님들 이메일 주소를 로그인 가능하게 GOOGLE 설정 해야함)
  * [ ] 구글로 로그인 한 사용자는 ROLE_GOOGLE_USER 라는 ROLE 을 갖도록 합니다.
* [ ] 레디스에 인증정보를 저장하여, 서버를 내렸다 올려도 인증이 유지 될 수 있도록 수정합니다.
* [ ] 멤버 생성 API에 RequestHeader Cotent-Type: text/csv를 허용해줍니다.
  * [ ] csv 헤더에는 필드에 순서가 적혀있고, 해당 헤더에 순서대로 필드값을 채워줍니다.
  ```
  id,name,password,...등
  baek,백종원,p123123,....
  ```
  ```
  password, id, name,,...등
  p123123, baek, 백종원,,....
  ```