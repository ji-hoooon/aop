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