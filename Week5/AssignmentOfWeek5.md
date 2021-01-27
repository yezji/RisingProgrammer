# Assignment of Week5

## Network Intro & API

> OPEN API 활용하여 프로그래밍 하기(SDK 사용 지양)
> 소셜 로그인(카카오톡 아이디 로그인 같은) 최소 한가지 이상 구현해오기(단순한 로그인 성공 화면이 아닌 이름,이메일 값을 활용한 프로그래밍하기. 단, 소셜 로그인은 SDK 허용)

### MusicApp

#### goal

* OPEN API 활용
* POSTMAN에서의 테스트 방법
* API 사용하여 안드로이드에서 파싱
* okhttp, retrofit에서 테스트 방법

#### functions

* DB 테이블 구성 + 데이터 넣기 (Song 곡정보 테이블, User - 플레이리스트 테이블, User - 좋아요 테이블)
  * 시간이 된다면 아티스트 테이블, 앨범 테이블 까지
* Playlist 생성/삭제
* 생성한 Playlist 안에서 Song 추가/삭제/순서변경(drag and drop)/셔플기능
* Song 좋아요 & 공유기능 버튼
* 좋아한 Song 모아보기, 카운팅
* 검색기능 (필터 적용해서 아티스트, 곡, 앨범, 플레이리스트, 가사 조회)
* 설정에서 Expandable ListView 활용

* layouts
  * bottom navigation view로 5개의 fragment 페이지
    1. home
    2. trend chart
    3. broadcast
    4. search
    5. drawer
  * 음악 재생바
  * trend chart에서 콘텐츠 별 chart 이동

---


### Result

* [MusicApp code link](https://github.com/yezji/RisingProgrammer/tree/main/Week3/MusicApp)

| activity name |                                                      | result                                                     | reference |
| ------------- | ---------------------------------------------------- | ---------------------------------------------------------- | --------- |
| chart         | <img src="./week2_result_chart.jpg" width="100%">    | <img src="./week2_references_chart_tab1.jpg" width="100%"> |           |
| login         | <img src="./week2_result_login.png" width="100%">    | 없음 (새로 만듦)                                           |           |
| register      | <img src="./week2_result_register.png" width="100%"> | 없음 (새로 만듦)                                           |           |

|      |      |      |      |
| ------------------------------------------- | ------------------------------------------- | ---------------------------------------------------------- | ---------------------------------------------------------- |
|      |      |      |      |
|      |      |      |      |
|      |      |      ||
