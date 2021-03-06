# Summary of Week2
<!-- TOC -->

- [Summary of Week2](#summary-of-week2)
  - [Review of Assignment1](#review-of-assignment1)
    - [Layout 조사](#layout-조사)
    - [개발 자세](#개발-자세)
  - [Life Cycle, Intent 개념](#life-cycle-intent-개념)
    - [Life Cycle](#life-cycle)
    - [Intent](#intent)

<!-- /TOC -->

---

## Review of Assignment1

### Layout 조사
* FrameLayout
  * 이미지를 단순히 전환하는 역할하는 역할도 있지만 **카카오톡 앱 아이콘 위에 알림박스 겹치기**와 같은 역할도 가능
* Intent
  * 명시적 인텐트
    * 실행할 컴포넌트명 지정해서 데이터를 넘김
    * ex. Gmail 앱에서 이메일 보내기 활동을 시작'하도록 시스템에 지시
  * 암시적 인텐트
    * 특정 이름을 지정하지 않고 조건에 부합하는 컴포넌트를 실행
    * AndroidManifest.xml의 &lt;activity&gt;태그에서 &lt;intent-filter&gt; 속성을 선언하여 조건에 맞는 것을 골라냄
* 다른 앱 블루스크린처럼 보기
  * 개발자 설정 > 레이아웃 범위 표시 체크

### 개발 자세
* 소통 방법
  * 사정이 있어서 업무를 하지 못한다면 팀장에게 꼭 공유하자!
  * 공유하지 못해 생긴 결과와 불이익은 모두 내 책임이 된다.
* 정보 조사
  * **예시를 찾아서 정확히 이해하자!**
  * 다 찾지 못한 지식은 출처나 reference 링크를 남겨두자!
    * 긁어서 넣은 지식은 기억에 남지 않는다. 나중에 스스로 찾을 수 있도록 링크를 남기고, 이해하면 내 언어로 작성할 것
  
---

## Life Cycle, Intent 개념

### Life Cycle
* 왜 Activity Life Cycle이 생기게 되었을까?
  1. 리소스의 부족함
     * **불편함**의 발견
       * PC에서 Mobile 환경으로 넘어오면서 **화면이 작아졌다!**
     * 개선하며 **발명**
       * PC보다 화면이 작아져서 모든 앱을 보여주지 못했기에 조치가 필요해졌다!
  2. 상태를 효율적으로 관리하기 위해
     * 화면을 사용하다 전화가 왔을 때와 같은 상황에서 사용자의 입장에서 조금 더 효율적으로 만들자
  * 결론
    * **상태에 따른 기능을 적용**할 수 있는 **Life Cycle 탄생!**

* Life Cycle
  * 상태에 따른 기능을 정의
    * 적용하고 싶은 해당 시점에 기능을 작성할 수 있다.
  * 순서
    * onCreate() -> onStart(): 생성
    * onStart() -> onResume(): 생성
    * onResume() -> onPause(): 알림이나 다이얼로그가 뜨고 뒤에 흐릿하게 원래 액티비티가 보일 때, 상태 저장
    * onResume() -> onStop(): 현재 액티비티가 포커스를 잃고 완전히 다른 액티비티에 가려졌을 때
    * onPause() -> onResume(): 알림이나 다이얼로그를 끄고 다시 돌아왔을 때, 상태 복구
    * onPause() -> onStop(): 아예 액티비티가 포커스를 잃고 화면에서 보이지 않을 때
    * onStop() -> onRestart(): 포커스를 잃고 가려졌었지만 다시 불려졌을 때
    * onStop() -> onDestroy(): 완전히 액티비티가 종료되었을 때

```mermaid
graph LR
	onCreate --> onStart
	onStart --> onResume
	onResume --> onPause
	onPause --> onResume
	onPause --> onStop
	onStop --> onRestart
	onRestart --> onStart
	onStop --> onDestroy
```

### Intent
* 명시적 인텐트
  * `A`가 `택배박스`에 `물건`을 담아 `B`에게 보내는 일
    * `A` : A 액티비티
    * `택배박스` : 인텐트
    * `물건` : 정보들 (variable, data, flag...)
    * `B` : B 액티비티
  * **=> `A화면`이 `B화면`에게 지명하여, `인텐트`에 `정보`를 담아 보내는 것!**

* 암시적 인텐트
  * `투수 A`가 `포수들`을 향해 `빨간색 공`을 던지면, 포수들 중 `빨간색 공을 잡아햐 하는 포수 B, D`가 공을 잡기 위해 나오는 일
    * `투수 A` : A 앱
    * `포수들` : 모든 앱
    * `빨간색 공` : 특정 action(ex. 사진기능, 공유기능)을 가리키기 위해 날리는 조건
    * `포수 B, D` : 빨간색 공을 잡는 조건을 가진 B 앱, D 앱
  * **AndroidManifest.xml에서 intent-filter**를 설정해두면 `빨간색 공`을 잡을 수 있게 된다.
  * **=> `A앱`이 `모든 앱` 중 공유기능이 있는 앱을 고르기 위해 `인텐트 조건`을 날리면, 조건에 해당하는(공유기능이 있는) `B앱, D앱`이 걸러지는 것!**
