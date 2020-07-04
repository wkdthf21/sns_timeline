# sns timeline 프로젝트


### 개요
---
사람들을 구독하여 타임라인에서 글을 받아보고, <br>
게시글을 올려 본인의 타임라인을 만들어가는 sns 타임라인 구현 프로젝트

<br>

### 기능
---

#### 구독

- 친구 찾기
- 친구 구독 하기
- 구독 취소
- 나를 구독중인 친구 조회
- 내가 구독중인 친구 조회

#### 콘텐츠

- 글 작성 / 수정 / 삭제
- 사진 업로드 / 동영상 업로드

#### 타임라인

- 구독한 유저들의 타임라인 조회
- 특정 유저의 타임라인 조회


<br>


## 개발 환경 및 사용 Library
---
- ide : IntelliJ
- language : Java
- framework : Spring Boot
- build tool : Gradle
- cloud : Ncloud
- Database : MySQL + H2
- SpringMVC + JPA
- SpringCache
- Lombok
- Swagger
- JUnit4

<br>


## ERD
---

![hackday_erd](https://user-images.githubusercontent.com/38368820/82672456-589fce80-9c7b-11ea-9ade-66c0ee6aef2e.png)

<br>

## Git
---

**Branch**

- master
- develop
- feature/기능요약

**Merge**

- master-develop: rebase merge
- develop-featrue: merge
