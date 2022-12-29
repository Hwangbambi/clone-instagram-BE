### 2조 클론 프로젝트(SA)

# <img src="https://cdn-icons-png.flaticon.com/512/174/174855.png" width="35px"> Instagram (BE) 

인스타그램과 동일한 기능 구현


---

 ## ⭐주요기능
 -  회원가입, 로그인 기능 (security 적용)
 -  카카오 API 로그인 기능 <br><br>
 -  게시글 작성, 이미지 첨부, 해시태그 추가, 수정, 삭제 (S3, SoftDelete 적용)
 -  전체 게시글 조회시 최신순으로 5개씩 보이도록 처리, 무한 스크롤 기능 구현
 -  유저 이름, 해시태그 검색 기능 구현
 -  게시글 좋아요, 좋아요 취소 (게시글별 좋아요 갯수 및 최초 좋아요 한 유저의 이름 표현)
 -  게시글 좋아요 갯수 클릭시 좋아요를 누른 유저 목록 조회 및 로그인한 유저의 팔로잉 유무 확인
 -  게시글에 댓글 작성, 삭제 (SoftDelete 적용) 기능 <br><br>
 -  댓글 좋아요, 좋아요 취소 <br><br>
 -  로그인한 유저가 팔로우 하지 않은 다른 유저 추천 목록 기능 구현 (게시글 작성 갯수가 많은 유저로 정렬, 최대 20명까지 조회)
 -  유저 팔로우, 언팔로우 
 <br><br>
 ## ⭐API 명세서 [LINK](https://descriptive-handbell-23e.notion.site/1674fd9af2fa4342933f7f0aace9f809?v=232c43f95d5e4df8ad00a649b1c3fe63)

 ## ⭐ERD
 <img width="784" alt="스크린샷_20221229_051305" src="https://user-images.githubusercontent.com/100077017/209923194-d0a86b53-1531-46ee-9f98-759195864b82.png">

 ---
 ##  💉트러블 슈팅💉
 
#### 1. 카카오 oAuth에러 
1) 문제 상황
- 카카오 oAuth에러 를 이용한 로그인 중 로컬에서는 정상작동하나, 백엔드 배포서버에서 구동시 에러
- 간헐적으로 502 에러 발생
- 정상작동시에도 서버에서 넘겨준 responsDTO에 프론트에서 접근하지 못함, 인가코드 요청작업 후 프론트서버로 돌아오지 않음

2) 문제 원인
- 배포한 백엔드 서버가 http였기 때문에 허가하지 않음
- 카카오로그인 버튼클릭을 redirect로 설정하여 작업 완료후 서버에서 response를 보내주더라도 프론트서버에서 접근하지 못함
- 인가코드를 요청한 서버와 인가코드 요청시 설정된 redirect주소가 일치하지 않아 카카오에서 허가하지 않음

3) 해결 방법
- 배포한 ec2 를 https 로 변환
- 카카오 로그인버튼 구현부를 axio로 설정
- 카카오에 인가코드요청 후 백엔드가아닌 프론트로 보내고, 프론트에서 다시 서버로 인가코드 전달하도록 하여 프론트와 서버를 연결 및 프론트 서버를 이용하기에 간헐적 502에러도 해결 (차후 백엔드서버의 aws https 설정부분도 수정 필요)
- 인가코드요청후 액세스토큰 요청시 설정하는 redirectUrl도 프론트서버주소로 지정하여 주어야 동일한 주소로 인식하여 accessToken 발급가능

#### 2. 해시태그 및 유저네임 검색 부분 에러
1) 문제상황
- 미관상 검색창에 유저네임, 해시태그 토글창을 둘 수 없는 상황이지만 두 종류를 검색하는 기능구현 필요
- requestParam 으로 받은 검색어가 깨지는 현상
- #의 유무로 해시태그나 유저네임 검색으로 분류하려 하였으나, #이 들어간 단어가 null로 들어오게 됨

2) 문제원인
- 원칙적으로 URL에는 ASCII가 아닌 문자를 사용할 수 없기 때문에 한글깨짐은 정상적인 결과
- requestParam 에선 # 등 특수기호를 인식하지 못하기 때문에 #으로 유저네임와 해시태그를 분류하여 검색하는 것은 불가

3) 해결방법 
- application.properties에 설정을 추가하여 글자깨짐 해결
- 유저테이블, 해시태그 테이블에 검색시 필요한 컬럼들 인덱싱 처리하여 like 속도 향상
- 단어 검색시 유사단어를 가진 유저네임과 해시태그를 가진 게시글을 모두 불러오도록 쿼리 작성
여러 테이블에서 값을 가져와야 하기에 nativeQuery를 통해 한번의 쿼리로 값을 불러올 수 있도록 쿼리문 작성

 
## 🧩swagger [3.34/98/133/api/doc](http://3.34.98.133/swagger-ui/index.html#/)

## 🌟기술스택🌟

 - 🔒백엔드

![자바](https://user-images.githubusercontent.com/108880977/209101862-e833ffc2-7cab-4114-8b74-5766d25b226b.svg)
![스프링부트](https://user-images.githubusercontent.com/108880977/209099782-f0f6fbb6-8c55-4a0e-a7a2-53fd5a000493.svg)
![시큐리티](https://user-images.githubusercontent.com/108880977/209101809-e972b9cf-36e1-4db3-a9ed-6474bc88770e.svg)
![JPA](https://user-images.githubusercontent.com/108880977/209104203-cccd4e80-5279-4e89-9453-c9d2333570b5.svg)
![JWT](https://user-images.githubusercontent.com/108880977/209102757-eb3f840f-ca24-4c89-a2b5-c60fff46bf49.svg)
![GRADLE](https://user-images.githubusercontent.com/108880977/209101888-8ea11829-e1b1-4de2-b7b4-8716e99dcf05.svg)
![MYSQL](https://user-images.githubusercontent.com/108880977/209101897-c8a4fa60-6fb0-4501-b30f-06269e75ce11.svg)
![아마존 RDS](https://user-images.githubusercontent.com/108880977/209103424-828b0d5b-9419-4ebb-8a85-24bbc3072213.svg)
![아마존 AWS](https://user-images.githubusercontent.com/108880977/209103421-1cf57ef4-8620-4932-8704-60d0ec14ed1f.svg)
![EC22](https://user-images.githubusercontent.com/108880977/209104209-b04b40b7-a847-4263-aeb8-de19bc7fa8d9.svg)
<img src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=AmazonS3&logoColor=white">

 - 🔑프론트엔드 https://github.com/clone-instagram/clone-instagram-fe3
 ---
 ### Member 
 BE 😶장영주, 😶황수정
 
 FE 😀박선영, 😀정상욱
 
 
 - FE 프론트엔드 깃허브로 이동
 https://github.com/clone-instagram/clone-instagram-fe3
 
