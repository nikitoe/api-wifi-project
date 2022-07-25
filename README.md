# 자바 기반 Pure 자바 프로젝트

# 프로젝트 소개
- PublicWifi, open souce API를 이용하여, 내 위치 기반 공공 와이파이 정보를 제공하는 웹 서비스 개발
- 공공 와이파이를 파악하고 사용함으로써, 각자 개인의 자산인 스마트폰의 데이터 사용량을 아낄 수 있습니다.
- 공공 와이파이 정보를 계속 업데이트해서 사용할 수 있도록 구성했습니다.
- 현재 내 위치를(위도, 경도) 바로 받아 올 수 있어서, 주변 공공 와이파이 정보를 검색할 수 있습니다.
- 검색한 위치를 저장해서 다시 해당 위치에 대한 공공 와이파이 정보를 검색할 수 있습니다.

# 사용 기술
- [서울시 오픈 API]([https://data.seoul.go.kr/dataList/OA-20883/S/1/datasetView.do](https://data.seoul.go.kr/dataList/OA-20883/S/1/datasetView.do))
- Java(JDK 1.8)
- SQLite 3.39.1
- Eclipse
- Tomcat v9.0

# 라이브러리
- gson
- sqlite-jdbc

```java
  <!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
  <dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.9.0</version>
  </dependency>
  <!-- https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc -->
  <dependency>
   <groupId>org.xerial</groupId>
   <artifactId>sqlite-jdbc</artifactId>
   <version>3.36.0.3</version>
  </dependency>
```

# 데이터베이스
![public_wifi](https://user-images.githubusercontent.com/36691759/180746135-1082ae01-2697-4612-ae5c-a77eb36930b9.jpg)

# 실행 방법
1. eclipse에 clone주소([https://github.com/nikitoe/api-wifi-project.git](https://github.com/nikitoe/api-wifi-project.git))를 입력후 파일을 import한다.
2. eclipse encoding 설정 하기(HTTP, CSS, XML) 
    - Window → Preferences → encoding → UTF-8 설정
3. Java version 설정하기 (Java 1.8)
    - Window → Preferences → Jaca → Installed JREs → Add.. →JDK 선택
4. Tomcat sever 설정하기
    - Window → Preferences → Server → Runtime Environments → Add.. → Apache 선택

※  the import javax servlet http cannot be resolved 에러 발생시
- 프로젝트 → Properties → Project Factes → runtimes → Apache Tomcat 체크 → Apply and close
<img width="600px" alt="the import javax servlet http cannot be resolved 에러 발생시" src= "https://user-images.githubusercontent.com/36691759/180746421-df68369c-50fd-4705-ab83-26e93859027f.jpg">

# 기능 소개
**📄주요 기능**
1. **Open API 와이파이 정보 가져오기 기능**
    - index.jsp 페이지에서 Open API 와이파이 정보 가져오기 링크 클릭시 공공 와이파이 정보를 가져올 수 있습니다.
    - 해당 와이파이 정보를 담을 데이터베이스 테이블(TB_PUBLIC_WIFI_INFO)을 생성하고 데이터들을 테이블에 삽입합니다.
    - load-wifi.jsp 페이지에서 가져온 총 데이터 수를 출력합니다.
    <img width="700px" alt="get_open_wifiAPI" src="https://user-images.githubusercontent.com/36691759/180759695-98046c24-35cf-4adc-9fbc-58b39f990791.gif">

2. **내 위치 정보(위도, 경도) 입력 시, 가까운 위치에 있는 와이파이 정보 저장 및 출력 기능**
    - LAT와 LNT의 input text에 입력후, 근처 WIPI 정보 보기 버튼 클릭시 search.jsp 페이지로 이동한다.
    - 입력한 내위치 정보(위도, 경도)에서 가장 가까운 순으로 공공 와이파이 정보를 20개 출력 합니다.
    - 입력한 내 위치 정보(위도, 경도)를 담을 테이블(TB_USER_HISTORY)을 생성후, 데이터들을 삽입합니다.
    <img width="700px" alt="serach_wifi" src="https://user-images.githubusercontent.com/36691759/180759022-053846c0-93ad-4626-b004-43014e5fed0a.gif">

3. **위치 히스토리 목록 출력 기능**
    - index.jsp 페이지에서 위치 히스토리 목록 링크를 클릭시 입력한 위치정보들을 가져올 수 있습니다.
    - TB_USER_HISTOR테이블에서 모든 정보를 조회합니다.
    - history.jsp 페이지에서 입력한 위치정보들을 출력합니다.
    <img width="700px" alt="historyList" src="https://user-images.githubusercontent.com/36691759/180759168-fb12d7b7-d659-4140-86fc-ee2aac2edad8.gif">

**📄부가 기능**
1. **조회 전 Open API 와이파이 정보 유효성 검사**
    - Open API 와이파이 정보 가져오기 링크 클릭 전에 근처 WIPI 정보 보기 클릭시 알림창을 띄웁니다.
    - TB_PUBLIC_WIFI_INFO 테이블 존재 여부와 데이터 조회한 후 존재 하지 않으면 return 값 0, 존재 하면 1을을 반환합니다.(return : 0 존재하지 않음, return : 1 존재 함)
2. **LAT와 LNT의 input text값 유효성 검사**
    - 숫자와 . 만 입력할 수 있습니다.
    - 문자값은 입력할 수 없습니다.
    - . 2개 이상은 입력할 수 없습니다.
    - LAT와 LNT 2개의 input 값에 모두 입력해야 합니다.(하나라도 입력 안되있을시 알림창을 띄웁니다.)
3. **내 햔제 위치 가져오기 기능**
    - 임이의 값이 아닌 현재 내 위치 정보를 자동으로 가져와 LAT와 LNT input text에 표시합니다.
4. **위치 히스토리 정보 삭제**
    - 삭제하고 싶은 위치 히스토리 정보를 삭제할 수 있습니다.

# 추가하고 싶은 일
- 기능
    - 구글 맵 API  추가
- 리팩터링
    - 싱글턴 패턴 추가
