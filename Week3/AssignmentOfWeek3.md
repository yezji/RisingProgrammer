# Assignment of Week3

## ListView, RecyclerView
> ListView, RecyclerView를 이용해서 앱 만들기 (CRUD 기본 기능 포함)

### MusicApp
#### goal
    * 음악 앱에 필요한 플레이리스트 기능들 구성
    * 데이터베이스 활용
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

| activity name || result                                      | reference                                                  |
| ------------------------------------------- | ------------------------------------------- | ---------------------------------------------------------- | ---------------------------------------------------------- |
|chart| <img src="./week2_result_chart.jpg" width="100%"> | <img src="./week2_references_chart_tab1.jpg" width="100%"> |
|login|<img src="./week2_result_login.png" width="100%">|없음 (새로 만듦)|
|register|<img src="./week2_result_register.png" width="100%">|없음 (새로 만듦)|
