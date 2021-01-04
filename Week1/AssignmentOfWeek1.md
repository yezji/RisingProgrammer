# Assignment of Week1
## 1. Manifest
> 본인의 기준으로 우선순위를 정하고 documentation 조사
### 역할
1. **앱 구성 요소 선언 (주요 역할) :**
   * Android System은 Activity, Service, BroadcastReceiver, ContentProvider와 같은 앱 구성 요소를 시작하기 위해 AndroidManifest.xml 파일을 읽어 해당 구성 요소가 존재하는지 확인합니다.
   * 따라서, 앱은 이 파일 안에 모든 구성 요소를 선언해야 합니다.
2. 모든 사용자 권한 식별 :
   * 앱이 요구하는 모든 권한 식별 (인터넷 액세스, 연락처 읽기 액세스 등)
3. 최소 API 선언 :
   * 앱이 어느 API를 사용하는지를 근거로 앱에서 요구하는 최소 API 레벨을 선언
4. HW/SW 기능 선언 :
   * 앱에서 사용하거나 요구하는 하드웨어 및 소프트웨어 기능 선언 (카메라, 블루투스, 멀티터치 화면 등)
5. API 라이브러리 선언 :
   * 앱이 링크되어야 하는 API라이브러리 선언 (Google Maps 라이브러리 등, Android 프레임워크 API는 제외)
### 앱 구성 요소 선언
 * &lt;activity&gt;
 * &lt;service&gt;
 * &lt;receiver&gt;
 * &lt;provider&gt;
### AndroidManifest.xml 주요 요소
1. &lt;manifest&gt; (필수, 유일)
   * AndroidManifest.xml 파일의 양 끝을 감싸는 루트 태그
   * 속성
     - 앱의 package name과 version 정보를 정의
2. &lt;application&gt; (필수, 유일)
   * application 태그 안에 컴포넌트 정보 및 앱에 대한 각종 정보를 정의
   * 속성
     - `android:label` : 앱의 label(제목) 정의
     - `android:icon` : 엡의 icon을 정의
3. &lt;activity&gt;
   * 포함된 위치
     - `<application>`
   * 포함 가능한 항목
     - `<intent-filter>, <meta-data>, <layout>`
   * 4대 컴포넌트 중에 Activity를 정의
   * 주요 속성
     - `android:name` : Activity class name 정의
     - `android:label` : Activity icon 재정의
     - `android:icon` : Activity label(제목)
4. &lt;intent-filter&gt;
   * 해당 컴포넌트가 어떤 암시적 intent를 처리하는지 정의
   * 포함된 위치
     - `<activity>`
     - `<activity-alias>`
     - `<service>`
     - `<receiver>`
   * 포함 필수 항목
     - `<action>` : 작업 처리 정의
   * 포함 가능한 항목
     - `<category>` : 컴포넌트 유형 정의
     - `<data>` : 컴포넌트 유형 정의
   * 속성
     - `android:label` : Intent icon 재정의
     - `android:icon` : Intent label(제목)
     - `android:priority` : Intent 우선순위 부여
5. &lt;service&gt;
   * Service 컴포넌트를 정의 (UI없는 백그라운드 작업 등 구현)
   * 포함된 위치
     - `<application>`
   * 포함 가능한 항목
     - `<intent-filter>, <meta-data>`
   * 속성
     - `android:name` : Service class name 정의
     - `android:label` : Service icon 재정의
     - `android:icon` : Service label(제목)
6. &lt;receiver&gt;
   * BroadcastReceiver 컴포넌트를 정의(앱 실행되고 있지 않을 때도 시스템이나 다른 앱에서 브로드캐스팅하는 인텐트를 앱에서 수신 가능)
   * 포함된 위치
     - `<application>`
   * 포함 가능한 항목
     - `<intent-filter>, <meta-data>`
   * 속성
     - `android:name` : BroadcastReceiver class name 정의
     - `android:label` : BroadcastReceiver icon 재정의
     - `android:icon` : BroadcastReceiver label(제목)
7. &lt;provider&gt;
   * ContentProvider 컴포넌트를 정의 (앱에서 관리되는 데이터를 인식)
   * 포함된 위치
     - `<application>`
   * 포함 가능한 항목
     - `<meta-data>, <grant-uri-permissions>, <path-permission>`
   * 속성
     - `android:name` : ContentProvider class name 정의
     - `android:label` : ContentProvider icon 재정의
     - `android:icon` : ContentProvider label(제목)
## 2. Palette
> *Fragment 조사는 선택 사항
* View와 ViewGroup
  * 눈에 보이는 요소 `Widget`, Widget을 담는 틀 `Layout`
* Common
   |분류|뷰 이름|특징|용도|
   |---|---|---|---|
   |Common|TextView|||
   |Common|Button|||
   |Common|ImageView|||
   |Common|RecyclerView|||
   |Common|&lt;Fragment&gt;|||
   |Common|ScrollView|||
   |Common|Switch|||
* Text
   |분류|뷰 이름|특징|용도|
   |---|---|---|--|
   |Text|TextView|||
   |Text|Plain Text|||
   |Text|Password|||
   |Text|Password (Numeric)|||
   |Text|E-mail|||
   |Text|Phone|||
   |Text|Postal Address|||
   |Text|Multiline Text|||
   |Text|Time|||
   |Text|Date|||
   |Text|Number|||
   |Text|Number (Signed)|||
   |Text|Number (Decimal)|||
   |Text|AutoCompleteTextView|||
   |Text|MultiAutoCompleteTextView|||
   |Text|CheckedTextView|||
   |Text|TextInputLayout|||
* Buttons
   |분류|뷰 이름|특징|용도|
   |---|---|---|--|
   |Buttons|Button|||
   |Buttons|ImageButton|||
   |Buttons|ChipGroup|||
   |Buttons|Chip|||
   |Buttons|CheckBox|||
   |Buttons|RadioGroup|||
   |Buttons|RadioButton|||
   |Buttons|ToggleButton|||
   |Buttons|Swtich|||
   |Buttons|FloatingActionButton|||
* Widgets
   |분류|뷰 이름|특징|용도|
   |---|---|---|--|
   |Widgets|View|||
   |Widgets|ImageView|||
   |Widgets|WebView|||
   |Widgets|VideoView|||
   |Widgets|CalendarView|||
   |Widgets|ProgressBar|||
   |Widgets|ProgressBar (Horizontal)|||
   |Widgets|SeekBar|||
   |Widgets|SeekBar (Discrete)|||
   |Widgets|RatingBar|||
   |Widgets|SearchView|||
   |Widgets|TextureView|||
   |Widgets|SurfaceView|||
   |Widgets|Horizontal Divider|||
   |Widgets|Vertical Divider|||
* Layouts
   |분류|뷰 이름|특징|용도|
   |---|---|---|--|
   |Layouts|ConstraintLayout|||
   |Layouts|LinearLayout (horizontal)|||
   |Layouts|LinearLayout (vertical)|||
   |Layouts|FrameLayout|||
   |Layouts|TableLayout|||
   |Layouts|TableRow|||
   |Layouts|Space|||
* Containers
   |분류|뷰 이름|특징|용도|
   |---|---|---|--|
   |Containers|Spinner|||
   |Containers|RecyclerView|||
   |Containers|ScrollView|||
   |Containers|HorizontalScrollView|||
   |Containers|NestedScrollView|||
   |Containers|ViewPager2|||
   |Containers|CardView|||
   |Containers|AppBarLayout|||
   |Containers|BottomAppBar|||
   |Containers|NavigationView|||
   |Containers|BottomNavigationView|||
   |Containers|Toolbar|||
   |Containers|TabLayout|||
   |Containers|Tabitem|||
   |Containers|ViewStub|||
   |Containers|&lt;include&gt;|||
   |Containers|&lt;fragment&gt;|||
   |Containers|NavHostFragment|||
   |Containers|&lt;view&gt;|||
   |Containers|&lt;requestFocus&gt;|||
* Helpers
   |분류|뷰 이름|특징|용도|
   |---|---|---|--|
   |Helpers|Group|||
   |Helpers|Barrier (Horizontal)|||
   |Helpers|Barrier (Vertical)|||
   |Helpers|Flow|||
   |Helpers|Guideline (Horizontal)|||
   |Helpers|Guideline (Vertical)|||
   |Helpers|Layer|||
   |Helpers|ImageFilterView|||
   |Helpers|ImageFilterButton|||
   |Helpers|MockView|||
* Google
   |분류|뷰 이름|특징|용도|
   |---|---|---|--|
   |Google|AdView|||
   |Google|MapView|||
* Legacy
   |분류|뷰 이름|특징|용도|
   |---|---|---|--|
   |Legacy|GridLayout|||
   |Legacy|ListView|||
   |Legacy|TabHost|||
   |Legacy|RelativeLayout|||
   |Legacy|GridView|||
## 3. Layout
> Linear, Relative, Frame, Table, Grid, Constraint 6가지 Layout 조사
1. Linear Layout
   * 세로 또는 가로의 단일 방향으로 모든 하위 요소를 정렬하는 뷰 그룹. 왼쪽 위부터 아래쪽 또는 왼쪽에서 오른쪽으로 차례로 배치
   * 특징
     * 요소에 가중치 할당 지원 (width와 height 0dp로 한 뒤, weight "1"로 설정)
   * 규칙
2. Relative Layout
   * 위젯 자신이 속한 레이아웃의 상하좌우의 위치를 지정하여 배치
3. Constraint Layout
   * 복잡한 레이아웃을 깊은 계층을 가지지 않고 배치
   * 특징
     * 레이아웃 사이의 관계에 따라 배치가 결정된다는 점에서 `RelativeLayout`과 유사하지만, `RelativeLayout`보다 유연하고 Android Studio의 Layout Editor와 함께 사용하기가 더 쉽기에 개발자가 인터페이스를 더욱 풍부한 방식으로 표현할 수 있다.
   * 규칙
     * 가로와 세로 하나씩 두 개 이상의 제약조건이 필요
   * 크기 조정 방법
      ![layout_editor](https://developer.android.com/images/training/constraint-layout/constraint-layout-editor-attributes-2x.png)
      1. 크기를 비율로 설정 (toggle aspect ratio constraint)
         * 너비:높이 입력
      2. 제약조건 삭제
      3. 높이/너비 모드
         1. 고정 (fixed) ![fixed](https://developer.android.com/studio/images/buttons/layout-width-fixed.png)
            * view의 크기를 지정
         2. 콘텐츠 래핑 (wrap content) ![content_wrapping](https://developer.android.com/studio/images/buttons/layout-width-wrap.png)
            * view가 콘텐츠에 맞게 필요한 만큼만 확장
          1. 제약조건과 일치 (match constraints) ![match_constraints](https://developer.android.com/studio/images/buttons/layout-width-match.png)
            * view가 양쪽의 제약조건에 맞게 최대한 많이 확장
      4. 여백
      5. 제약조건 편향 제어
      6. 제약조건 목록에서 개별 제약조건을 강조 가능
   * ```Java
      java.lang.Object
         ViewGroup
            androidx.constraintlayout.widget.ConstraintLayout
      ```
4. Frame Layout
   * 위젯을 왼쪽 위에 일률적으로 겹쳐서 배치하여 중복되어 보이는 효과를 냄. 여러 개의 위젯을 배치한 후 상황에 따라서 필요한 위젯을 보이는 방식에 주로 활용
5. Table Layout
   * 위젯을 행과 열의 개수를 지정한 테이블 형태로 배열
6.  Grid Layout
   * 테이블 레이아웃과 비슷하지만, 행 또는 열을 확장하여 다양하게 배치

## 4. Layout 화면 구축
> 3번의 Layout 전부 활용하여 실제 Product 수준의 화면 구축
* [PaymentLayout link](https://github.com/yezji/RisingProgrammer/tree/main/Week1/PaymentLayout)