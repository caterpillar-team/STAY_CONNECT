# STAY CONNECT(숙박예약웹) 프로젝트

DEVELOPMENT MOTIVATION
---
> 숙박 예약 사이트 -
> > -- 개발동기 
> > -- <br> 
 

HISTORY
---
[강지영]() | [윤지원]() | [이재형]() | [조은파]() | 


PLANS
---
 |LANGUAGE|PLAN|IMPLEMENT|DESCRIPTION|
 |-|-|-|-|
 |JAVA|2024/03/05 ~ 2024/04/23|-|-|
 |JSP/SERVLET|2024/04/24 ~ 2024/05/03|-|-|
 |SPRING STS3|-|-|-|
 |SPRING BOOT|-|-|-|



MEMBERERS
--- 
|NAME|ROLE|DETAILS|DESCRIPTION| 
|---|---|---|---|
|강지영|BN| reservation - 예약삭제 / 예약수정 |---|
|윤지원|BN| reservation - 예약생성 / 예약목록 |---|
|이재형|BN| member - 로그인 / 로그아웃 / 회원탈퇴 |---|
|조은파|BN| member - 회원등록 / 회원목록 |---|


시작가이드
---
#### REQUIREMENTS
---
- 
#### INSTALLATION
---
```
```
##### BAEKEND
---
```
```
##### FRONTEND
---
```
```

SKILLS
---

#### FN
---
<img src="https://img.shields.io/badge/HTML5-3366CC?style=for-the-badge&logo=htmlacademy&logoColor=white"> <img src="https://img.shields.io/badge/CSS-1572B6?style=for-the-badge&logo=css3&logoColor=white"> <img src="https://img.shields.io/badge/JAVASCRIPT-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white"> <img src="https://img.shields.io/badge/JQUERY-0769AD?style=for-the-badge&logo=jquery&logoColor=white"> 


#### BN
---
<img src="https://img.shields.io/badge/JAVA-005571?style=for-the-badge&logo=doubanread&logoColor=white"> <img src="https://img.shields.io/badge/JSP-1E8CBE?style=for-the-badge&logo=doubanread&logoColor=white"> <img src="https://img.shields.io/badge/SERVLET-4B4B77?style=for-the-badge&logo=doubanread&logoColor=white"> <img src="https://img.shields.io/badge/SPRING-STS3-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/SPRINGBOOT-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> 

#### DATABASE
---
<img src="https://img.shields.io/badge/MYSQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white">  


#### DEVOPS
---
<img src="https://img.shields.io/badge/GIT-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/GITHUB-181717?style=for-the-badge&logo=github2&logoColor=white"> <img src="https://img.shields.io/badge/AWS-EC2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white"> <img src="https://img.shields.io/badge/DOCKER-2496ED?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/DOCKERHUB-2496ED?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/JENKINS-D24939?style=for-the-badge&logo=jenkins&logoColor=white"> 


END POINT DOC
---
|URI|REQUEST METHOD|REQUEST PARAMETER TYPE|RESPONSE VALUE TYPE|DESCRIPTION|
|---|---|---|---|---|
|/member/delete|GET/POST|id|boolean|member 탈퇴|
|/member/register|GET/POST|MemberDto|boolean|member 등록|
|/member/update|GET/POST|username, password, email, phone|boolean|member 정보 수정|
|/member/select|GET/POST|username|MemberDto|(관리자)member 상세 조회|
|/member/selectAll|POST|---|List<MemberDto>|(관리자)member 목록 조회|
|/member/login|GET/POST|username, password, session|boolean|member 로그인|
|/member/logout|GET|---|boolean|member 로그아웃|
|---|---|---|---|---|
|/reservation/add|GET/POST|ReservDto|boolean|숙소 예약|
|/reservation/list|GET/POST|userId|ReservDto|member가 예약한 숙소 목록 조회|
|/reservation/update|GET/POST|reservationDto|boolean|member가 예약한 정보 수정|
|/reservation/delete|GET/POST|user_id|boolean|member가 예약한 숙소 취소|
|---|---|---|---|---|

DEPENDENCIES LIST
---
|CAT|NAME|DESCRIPTION|LINK|-|-|
|-|-|-|-|-|-|
|FN|-|-|-|-|-|
|FN|-|-|-|-|-|
|BN|-|-|-|-|-|
|BN|-|-|-|-|-|
|DB|-|-|-|-|-|
|DEVOPS|-|-|-|-|-|


주요기능
---
-
-
-

ERD[stayconnect]
---
![image](https://github.com/CATERPILLAR-TEAM/TEST_SERVLET/assets/155939946/fbf64d7c-94e0-4700-b3b9-79e5172d0a4d)


FILE TREES[JSP/SERVLET]
--- 
```STAY_CONNECT
├── README.md
├── SECURITY.md
├── build.gradle
├── docker
│   ├── docker-compose.mysql.yml
│   ├── docker-compose.yml
│   └── dockerfile
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── makefile
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── caterpillars
    │   │           └── StayConnect
    │   │               ├── StayConnectApplication.java
    │   │               ├── component
    │   │               │   ├── entryPoint
    │   │               │   ├── filter
    │   │               │   │   └── JWTAuthenticationFilter.java
    │   │               │   ├── handler
    │   │               │   │   ├── JWTLoginSuccessHandler.java
    │   │               │   │   ├── JWTLogoutSuccessHandler.java
    │   │               │   │   └── OAuth2UserLoginFailureHandler.java
    │   │               │   ├── initializer
    │   │               │   │   ├── TestAccDataInitializer.java
    │   │               │   │   └── TestUserDataInitializer.java
    │   │               │   └── provider
    │   │               │       └── JWTokenProvider.java
    │   │               ├── configuration
    │   │               │   └── SecurityConfig.java
    │   │               ├── controller
    │   │               │   ├── AccommodationController.java
    │   │               │   ├── AdminController.java
    │   │               │   ├── AuthController.java
    │   │               │   ├── ErrorController.java
    │   │               │   ├── ReservationController.java
    │   │               │   ├── URIController.java
    │   │               │   └── UserController.java
    │   │               ├── model
    │   │               │   ├── dto
    │   │               │   │   ├── PaymentDto.java
    │   │               │   │   ├── ReviewDto.java
    │   │               │   │   ├── RoomInfoDto.java
    │   │               │   │   └── UserSignUpDto.java
    │   │               │   ├── entities
    │   │               │   │   ├── Accommodation.java
    │   │               │   │   ├── AccommodationGrade.java
    │   │               │   │   ├── Category.java
    │   │               │   │   ├── Reservation.java
    │   │               │   │   ├── Review.java
    │   │               │   │   ├── Role.java
    │   │               │   │   ├── RoomInfo.java
    │   │               │   │   └── User.java
    │   │               │   └── repository
    │   │               │       ├── AccommodationGradeRepository.java
    │   │               │       ├── AccommodationRepository.java
    │   │               │       ├── CategoryRepository.java
    │   │               │       ├── ReservationRepository.java
    │   │               │       ├── ReviewRepository.java
    │   │               │       ├── RoleRepository.java
    │   │               │       ├── RoomInfoRepository.java
    │   │               │       └── UserRepository.java
    │   │               └── service
    │   │                   ├── AuthService.java
    │   │                   ├── PortOnePaymentService.java
    │   │                   ├── ReservationService.java
    │   │                   ├── ReviewService.java
    │   │                   ├── ReviewServiceImpl.java
    │   │                   └── UserService.java
    │   └── resources
    │       ├── application.properties
    │       ├── static
    │       │   ├── css
    │       │   │   ├── common
    │       │   │   │   ├── footer.css
    │       │   │   │   └── header.css
    │       │   │   ├── layouts
    │       │   │   │   └── layout.css
    │       │   │   └── pages
    │       │   │       ├── accom_detail.css
    │       │   │       ├── admin
    │       │   │       │   ├── reviewList.css
    │       │   │       │   └── userList.css
    │       │   │       ├── auth
    │       │   │       │   ├── signIn.css
    │       │   │       │   └── signUp.css
    │       │   │       ├── index.css
    │       │   │       └── user
    │       │   │           └── myPage.css
    │       │   ├── fonts
    │       │   │   └── fonts.googleapis.woff2
    │       │   ├── img
    │       │   │   ├── StayConnect-main.jpg
    │       │   │   ├── StayConnect_main2.jpg
    │       │   │   ├── StayConnect_main3.jpg
    │       │   │   ├── chat.png
    │       │   │   ├── city
    │       │   │   │   ├── busan.jpg
    │       │   │   │   ├── daegu.jpg
    │       │   │   │   ├── daejeon.jpg
    │       │   │   │   ├── damyang.jpg
    │       │   │   │   ├── gangwon.jpg
    │       │   │   │   ├── gyeongju.jpg
    │       │   │   │   ├── jeju.jpg
    │       │   │   │   ├── jeonju.jpg
    │       │   │   │   ├── pohang.jpg
    │       │   │   │   └── seoul.jpg
    │       │   │   ├── github_logo.png
    │       │   │   ├── google_logo.png
    │       │   │   ├── kakao_logo.png
    │       │   │   ├── logo.svg
    │       │   │   ├── naver_logo.png
    │       │   │   └── notion_logo.png
    │       │   ├── js
    │       │   │   ├── index
    │       │   │   │   ├── index_scroll.js
    │       │   │   │   └── kakaoMapAPI.js
    │       │   │   └── signUp
    │       │   │       ├── checkBoxConsent.js
    │       │   │       └── formatPhoneNumber.js
    │       │   └── lib
    │       │       ├── ajax
    │       │       │   ├── popper.min.js
    │       │       │   └── popper.min.js.map
    │       │       ├── axios
    │       │       │   └── axios.min.js
    │       │       ├── bootstrap
    │       │       │   ├── bootstrap.min.css
    │       │       │   ├── bootstrap.min.css.map
    │       │       │   ├── bootstrap.min.js
    │       │       │   └── bootstrap.min.js.map
    │       │       ├── googleapis
    │       │       │   └── fonts.css
    │       │       ├── jquery
    │       │       │   └── jquery.min.js
    │       │       ├── portone
    │       │       │   └── iamport.js
    │       │       └── swiper
    │       │           ├── swiper-bundle.min.js
    │       │           └── swiper-bundle.min.js.map
    │       └── templates
    │           ├── deprecated
    │           │   ├── 403.html
    │           │   ├── 404.html
    │           │   └── default.html
    │           ├── fragments
    │           │   ├── common
    │           │   │   ├── footer_frag.html
    │           │   │   └── header_frag.html
    │           │   └── index
    │           │       ├── conditionalSection_frag.html
    │           │       └── mainSection_frag.html
    │           ├── layouts
    │           │   └── layout.html
    │           └── pages
    │               ├── admin
    │               │   ├── reviewList.html
    │               │   └── userList.html
    │               ├── auth
    │               │   ├── signIn.html
    │               │   └── signup.html
    │               ├── index.html
    │               └── user
    │                   ├── accom_detail.html
    │                   └── myPage.html
    └── test
        └── java
            └── com
                └── caterpillars
                    └── StayConnect
                        └── StayConnectApplicationTests.java
```
