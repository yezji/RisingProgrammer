# Assignment of Week2

<!-- TOC -->

- [Assignment of Week2](#assignment-of-week2)
  - [Activity LifeCycle, Intent](#activity-lifecycle-intent)
    - [MusicApp](#musicapp)
    - [Result](#result)

<!-- /TOC -->

## Activity LifeCycle, Intent
> Activity LifeCycle, Intent를 모두 활용해서 앱 만들기 (화면 전환 및 데이터 주고받기 포함)

### MusicApp
* goal
    * Activity의 life cycle에 따라 음악 재생 보이기
    * Intent를 활용한 음악 실행과 화면 전환
* functions
  * onCreate()
    * UI 리소스 초기화/복원
  * onRestart()
    * 최근 화면 불러오기
    * 서비스가 틀고 있는 음악의 화면 보이기
  * onStart()
    * activity가 forground로 전환되어 상호작용을 준비
  * onResume()
    * focus가 떠날 때까지 상호작용하도록 기능 활성화 (ex. 카메라 미리보기 시작 기능)
  * onPause()
    * 배터리 수명에 영향을 미치는 모든 리소스 해제(GPS와 같은 센서 핸들..)
    * 데이터 저장X, 네트워크 호출X, 데이터베이스 트랜잭션X -> 저장하기에는 시간이 짧기에 onStop()에서 실행
    * 🚨서비스 시작? (음악 재생)
      * onPause()에서 서비스를 시작해도 되는 것인지 물어보자
      * ㄴㄴ인듯..? 노래 실행/중지도 어차피 resume에서 하는데 안보인다고 음악까지 중지하면 안되니까!
  * onStop()
    * UI 상태 저장
      * 가벼운 방법 : onSaveInstanceState()
    * 영구데이터 저장 (ex. 사용자 기본 설정, 데이터베이스 데이터)
      * activity가 forground에 있을 때 저장하거나, onStop()메서드에서 저장
  * onDestroy()
    * 서비스 종료
    * 아직 해제되지 않은 모든 리소스 (ex. onStop()) 해제

* layouts
  * bottom navigation view로 5개의 fragment 페이지
    1. home
    2. **trend chart (주요 기능)**
    3. broadcast
    4. search
    5. drawer
  * 음악 재생바 만들기 -> 서비스로 재생
  * trend chart에서 콘텐츠 별 chart 이동
  * 콘텐츠 별 chart 안에 제작자, tab 4개
    1. collection songs
    2. related songs
    3. collection info
    4. collection comment
  * each song page
    * 메뉴 안의 공유기능, 인스타그램 포스팅 기능


### Result

* [MusicApp code link](,,,)

|result|reference|
|------|---------|
|<img src="./week1_result.jpg" width="100%">|<img src="./week1_reference.jpg" width="100%">|

