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
 ---
 ##  💉트러블 슈팅💉
#### 1. 파일업로드
>- 배포 후 access denied 문제 : aws의존성 버전 재설정, Iam key 재발급 및 권한 재설정(S3FullRequest)
>- 이후 permission denied 문제 : 받은 파일 임시저장폴더를 ec2서버에 생성하지 못해 발생한 오류. 임시저장폴더를 생성가능한 곳으로 변경 후 저장폴더 생성하여 오류해결


#### 2. nickname으로 검색하여 검색된 전체 post 불러올때 user table select N+1 문제
>- nativeQuery로 Post 와 user join하여 Post와 nickname을 한번에 불러올 수 있도록 처리
>- countQuery 사용하여 페이징처리도 동시 적용

#### 3. 전체 post내의 comment에서 comment username통해 User nickname 값 가져올때 N+1문제
>- nativeQuery로 Comment와 User table을 join하여 nickname값 같이 불러오도록 설정

 
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
 
