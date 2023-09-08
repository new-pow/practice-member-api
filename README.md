# Member API 연습 프로젝트

## 주요 구현사항

### 사용자 로그인
- Access token 발행시 refresh Token 을 발행하여 로그인을 다시 하지 않고도 Access Token을 갱신할 수 있도록 구현하였습니다.

### 사용자 회원가입
- 비밀번호 암호화를 해시를 통해 단방향 구현하였습니다.
- 비밀번호를 DB에 직접적으로 저장하지 않고, 해시와 솔트를 저장하여 비밀번호 검증을 하는 형태로 구현하였습니다.

### 게시글 목록 조회
- 게시글 내용은 목록에서 조회하지 않으므로 별도 테이블로 분리하여 최적화를 시도하였습니다.
- 커서 방식의 페이지네이션을 구현하여 많은 데이터에도 API 효율을 유지할 수 있도록 하였습니다.

---

## 데이터베이스 테이블 구조
![wanted-internship-diagram](https://github.com/new-pow/wanted-pre-onboarding-backend/assets/103120173/b978191a-d65a-48e6-b887-4d59fbe9959c)

---

### 인프라 구조도

![image](https://github.com/new-pow/wanted-pre-onboarding-backend/assets/103120173/8b34ceaf-5bd8-470f-809d-2981be7c1346)

---

## API 데모 영상

[링크](https://youtu.be/Ug1_f1Ap9A4)

---

## API 명세
> 자세한 사항은 다음의 문서에서 확인 가능합니다. [API 문서](https://documenter.getpostman.com/view/26643106/2s9Y5R15uW)

|  No.   | desc                       | Method    | URI                        |
|:------:|-------------------------------|:----------|------------------------------|
|   1    | 사용자 회원가입 | `POST`    | `/api/auth/signup`         |
|   2    | 사용자 로그인                | `POST`    | `/api/auth/signin`         |
|   3    | 새로운 게시글 생성             | `POST`    | `/api/posts`               |
|   4    | 게시글 목록 조회              | `GET`     | `/api/posts?last={postId}` |
|   5    | 특정 게시글 조회              | `GET`     | `/api/posts/{postId}`      |
|   6    | 특정 게시글 수정              | `PUT`     | `/api/posts/{postId}`      |
|   7    | 특정 게시글 삭제              | `DELETE`  | `/api/posts/{postId}`      |
|  none  | Access token 갱신        | `POST`    | `/api/auth/refresh`        |

### 1. `POST` 사용자 회원가입
- Method : `POST`
- URI : `/api/auth/signup`
#### Request
##### Body
```json
{
  "email": "testUser@gmail.com",
  "password": "passMe1234",
  "username": "테스트유저"
}
```
#### Response
- `201 CREATED` 회원가입 성공
```json
{
    "message": "회원가입에 성공하였습니다.",
    "data": {
        "id": 8,
        "email": "testUser@gmail.com",
        "username": "테스트유저"
    }
}
```
- `400 BAD REQUEST` email 혹은 password 형식 오류
- `409 CONFLICT` 중복 email 혹은 중복 username 중복

---

### 2. `POST` 사용자 로그인
- Method : `POST`
- URI : `/api/auth/signin`
#### Request
##### Body
```json
{
  "email": "testUser@gmail.com",
  "password": "passMe1234"
}
```

#### Response
- `200 OK` 로그인 성공
```json
// Header
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbmVkIjoiOCIsImV4cCI6MTY5MTc1NjA1N
```
```json
{
  "message": "로그인에 성공하였습니다.",
  "data": {
    "username": "테스트유저",
    "tokens": {
      "ownId": 8,
      "accessToken": {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJsb2dpbmVkIjoiOCIsImV4cCI6MTY5MTc1NjA1NiwiaWF0IjoxNjkxNzQ4ODU2fQ.WVVLXy6ZoPABWK3gCpABuHY8CuLLmnT8JMH189lOyWM",
        "expiration": "2023-08-11T12:14:16.842240Z"
      },
      "refreshToken": {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6IjgiLCJleHAiOjE2OTQzNDA4NTYsImlhdCI6MTY5MTc0ODg1Nn0.3td6wmXX9lkuujmQzCo6q_4t9xBit7jxD0SDtyB6wtk",
        "expiration": "2023-09-10T10:14:16.842240Z"
      },
      "created": "2023-08-11T10:14:16.842240Z",
    }
  }
}
```
- `401 UNAUTHORIZED` 해당하는 회원 email이 존재하지 않음
- `401 UNAUTHORIZED` password가 일치하지 않음

---

### 3. `POST` 새로운 게시글 생성
- Method : `POST`
- URI : `/api/posts`
#### Request
##### Header
| Header | Value                          |
|--------|--------------------------------|
| Authorization | Bearer {로그인 후 발급된 AccessToken} |

##### Body
```json
{
  "title": "string",
  "contents": "string"
}
```
#### Response
- `200 OK` 글 작성 성공
```json
{
  "message": "글이 작성되었습니다.",
  "data": {
    "id": 1 // 작성된 글 ID
  }
}
```
- `401 UNAUTHORIZED` 로그인 상태가 아닙니다. (요청 시 header 누락)
- `401 UNAUTHORIZED` 유효하지 않는 Access token (기간 만료 등)

---

### 4. `GET` 게시글 목록 조회
- Method : `GET`
- URI : `/api/posts`
> 10개 단위로 게시글 목록을 조회할 수 있습니다. 조회시 글 내용은 조회하지 않습니다.

#### Request
##### Query
```text
last={postId} | null
```
- 첫 페이지를 조회할 때
```http request
GET /api/posts HTTP/1.1
```
- 두번째 페이지 이후, 쿼리에 마지막 글 ID를 함께 요청한다.
```http request
GET /api/posts?last=3 HTTP/1.1
Host: localhost:8080
```
#### Response
- `200 OK` 조회 성공
```json
{
    "message": "글 목록이 조회되었습니다.",
    "data": {
        "lastId": 1,
        "postPage": {
            "content": [
                {
                    "id": 2,
                    "title": "string",
                    "author": {
                        "authorId": 1,
                        "authorname": "테스트유저"
                    },
                    "createdAt": "2023-08-15T19:04:47Z",
                    "updatedAt": "2023-08-15T19:04:47Z"
                }
              // 중략
            ],
            "pageable": {
                "sort": {
                    "empty": true,
                    "unsorted": true,
                    "sorted": false
                },
                "offset": 0,
                "pageNumber": 0,
                "pageSize": 10,
                "paged": true,
                "unpaged": false
            },
            "last": true,
            "totalPages": 1,
            "totalElements": 2,
            "sort": {
                "empty": true,
                "unsorted": true,
                "sorted": false
            },
            "first": true,
            "number": 0,
            "size": 10,
            "numberOfElements": 2,
            "empty": false
        }
    }
}
```
- `400 BAD REQUEST` 요청 쿼리 오류 (탐색범위를 벗어났을 때)

---

### 5. `GET` 특정 게시글 조회
- Method : `GET`
- URI : `/api/posts/{postId}`
#### Response
- `200 OK` 조회 성공
```json
{
    "message": "글을 조회했습니다.",
    "data": {
        "id": 1,
        "author": {
            "authorId": 1,
            "authorname": "테스트유저"
        },
        "title": "string",
        "content": "string",
        "createdAt": "2023-08-16T12:03:25Z"
    }
}
```
- `400 BAD REQUEST` 존재하지 않는 글 조회 요청시

---

### 6. `PUT` 특정 게시글 수정
- Method : `PUT`
- URI : `/api/posts/{postId}`
#### Request
##### Header
| Header | Value                          |
|--------|--------------------------------|
| Authorization | Bearer {로그인 후 발급된 AccessToken} |

##### Body
```json
{
  "title": "수정된 글 제목",
  "contents": "수정된 글 내용"
}
```
#### Response
- `200 CREATED` 수정 성공
```json
{
  "message": "글을 수정했습니다.",
  "data": {
    "id": 1 // 수정한 글 ID
  }
}
```
- `400 BAD REQUEST` 해당하는 글이 없음
- `401 Unauthorized` 자신이 작성한 글이 아님
- `401 Unauthorized` 로그인 상태가 아님

---

### 7. `DELETE` 특정 게시글 삭제
- Method : `DELETE`
- URI : `/api/posts/{postId}`
#### Request
##### Header
| Header | Value                          |
|--------|--------------------------------|
| Authorization | Bearer {로그인 후 발급된 AccessToken} |

#### Response
- `200 OK` 삭제 성공 (hard delete)
```json
{
    "message": "글을 삭제 완료 했습니다.",
    "data": 1
}
```
- `400 BAD REQUEST` 해당하는 글이 없음
- `401 Unauthorized` 자신이 작성한 글이 아님
- `401 Unauthorized` 로그인 상태가 아님

---

- [상세 API 문서](https://documenter.getpostman.com/view/26643106/2s9Y5R15uW)
