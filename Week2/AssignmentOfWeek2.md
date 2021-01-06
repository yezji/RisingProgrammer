# Assignment of Week2

[toc]

## Activity LifeCycle, Intent
> Activity LifeCycle, Intent를 모두 활용해서 앱 만들기 (화면 전환 및 데이터 주고받기 포함)

### MusicApp 구축

#### goal

* Activity의 life cycle에 따라 음악 재생 보이기
* Intent를 활용한 음악 실행과 화면 전환

#### functions

* onCreate()
* onRestart()
  * 서비스가 틀고 있는 음악의 화면 보이기
* onStart()
* onResume()
* onPause()
  * 서비스 시작 (음악 재생)
* onStop()
* onDestroy()
  * 데이터 저장
  * 음악 종료

#### layouts

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


### Result

* [MusicApp code link](,,,)

|result|reference|
|------|---------|
|<img src="./week1_result.jpg" width="100%">|<img src="./week1_reference.jpg" width="100%">|

