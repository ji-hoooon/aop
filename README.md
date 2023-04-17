# AOP 학습을 위한 리포지토리

## 개발 순서 Model -> Service -> Repository -> Controller
1. 필터와 인터셉터의 차이
   - 요청을 언제 필터링할지 타이밍의 차이
   - 필터에서 처리한 것들
     - CORS 필터
     - CSRF 필터
     - 인코딩 필터
     - stateless 필터
   - 인터셉터에서 처리한 것들
     - 인증 처리 
     - 권한 처리
     - 인가 처리
     - 로깅 처리
2. DTO
   - ResponseDTO
   - ValidationDTO
3. AOP
   - Log
   - OrderSheet
4. 의존성 주입 (spring-boot-starter)
   - AOP
   - JWT
   - Validation
5. OOP - 상태(변수)는 행위(메서드)를 통해 변경한다. (캡슐화)
6. JPA - OOP를 이용해 객체간의 관계를 통한 영속성 관리 가능
   - 즉, MyBatis의 경우 데이터베이스의 테이블 구조에 의존해야 하는 단점이 있다. 
   - 데이터베이스의 스키마가 변경되면 SQL 쿼리와 매핑되는 XML 파일도 변경되어야 하기 때문이다.
   - 반면에 JPA를 사용하면 데이터베이스의 스키마 변경이 있더라도 대부분 자동으로 처리되므로, 비교적 유연한 코드 작성이 가능하다.
7. 양방향 매핑 : OrderProduct - OrderSheet
   - 연관관계의 비주인에서 mappedBy 속성으로 연관관계의 주인을 명시한다. 
   - 무한참조 방지해야 함
   - DB에서는 id를 통해 join한다.
   - 싱크를 위한 양방향 편의 메서드 필요
   - 구조가 복잡해진다.
8. 응답 DTO 작성하는 3가지 방법
 1) 응답이 존재하지 않는 요청 성공 - DELETE
```java
   ResponseDTO<User> v1 = new ResponseDTO<>();
```
   2) 응답이 존재하는 요청 성공 - GET, POST, PUT
   ```java
     ResponseDTO<User> v2 = new ResponseDTO<>().data("값");
```
   3) 요청 실패 - GET, POST, PUT, DELETE
   ```java
    ResponseDTO<User> v3 = new ResponseDTO<>().fail();
 ```
9. Filter는 예외 핸들러로 처리 불가능하므로, 예외의 응답DTO를 생성하는 유틸 클래스 작성
10. 옵저버 패턴을 사용하는 프론트앤드
    - 화면에 해당하는 상태를 관리하는 스토어를 가지고 있고, 해당 스토어를 화면에 뿌린다.
    - 스토어들은 스토어에 해당하는 리포지토리가 존재하고, 리포지토리는 서버의 API에 데이터를 요청한다.
    - 리포지토리와 API가 연동되어있는 상태에서, 화면이 스토어를 구독하고 있는 형태로 상태가 변경되면 변경감지를 통해 렌더링된다.
    - 따라서, 프론트엔드가 요청한 데이터에 해당하는 상태코드와 id값을 함께 전달해야한다. (GET, POST, PUT)
11. 유효성 실패, 잘못된 파리미터 요청시 커스텀 예외 클래스를 작성해 400 예외 반환
    - 유효성 실패 : 요청의 Body 데이터
    - 잘못된 파라미터 : @PathVariable
    - 400의 경우에는 메시지 뿐만 아니라, 잘못된 데이터를 함께 전달해야한다.