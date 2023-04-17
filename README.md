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
12. AOP를 이용해 코드의 중복 제거와 로직의 일관성 제공
    - AOP는 annoataion과 advice
    - @MyErrorLogRecord
    - @MySameUserIdCheck
    - @MySessionStore
13. MySessionArgumentResolver로 MySessionStore 구현
    - MySessionStore가 null이 아니거나, MySeesionStore의 파라미터가 SessionUser타입이면 세션에 세션 유저 주입
    - 권한 체크하는 인터셉터 작성
      - MyAdminInterceptor
      - MySellerInterceptor
    - WebMvcConfig 설정
      - CORS 설정
      - MessageConverter 설정
      - ViewResolver 설정
      - Interceptor 설정
      - ArgumentResolver 설정
    - FilterRegistrationBean에 필터 등록
14. 회원가입, 로그인
    - LoginDTO, JoinDTO
15. DTO 작성
    - Entity에 ReqDTO를 만들 수 있도록 생성자에 @Builder 필요
    - ReqDTO의 경우 유효성 검사 필요 (INSERT의 경우 toEntity 메서드 필요, 없으면 매번 생성해야하므로, UPDATE는 더티체킹)
    - RespDTO의 경우 Entity로 만들기 때문에 엔티티를 이용해 생성자 작성 (JSON으로 보여주므로, 모두 문자열로 처리)
16. JPA에서의 페이징
    - @PageableDefault과 Pageable로 간단하게 페이징 구현
    - 쿼리 결과는 모두 Page 객체로, 제네릭 타입을 전달한다.
    - 즉, http://localhost:8080/products?page=2로 호출 가능
    ```java
    {
     {
    "status": 200,
    "msg": "성공",
    "data": {
        "content": [
            {
                "id": 21,
                "seller": {
                    "id": 2,
                    "username": "seller",
                    "email": "ssar@nate.com",
                    "role": "SELLER",
                    "status": true,
                    "createdAt": "2023-04-17T15:25:55.023427",
                    "updatedAt": "2023-04-17T15:30:44.096868"
                },
                "name": "바나나",
                "price": 3000,
                "qty": 50,
                "createdAt": "2023-04-17T15:40:00.697425",
                "updatedAt": null
            },
            {
                "id": 22,
                "seller": {
                    "id": 2,
                    "username": "seller",
                    "email": "ssar@nate.com",
                    "role": "SELLER",
                    "status": true,
                    "createdAt": "2023-04-17T15:25:55.023427",
                    "updatedAt": "2023-04-17T15:30:44.096868"
                },
                "name": "바나나",
                "price": 3000,
                "qty": 50,
                "createdAt": "2023-04-17T15:40:03.248966",
                "updatedAt": null
            },
            {
                "id": 23,
                "seller": {
                    "id": 2,
                    "username": "seller",
                    "email": "ssar@nate.com",
                    "role": "SELLER",
                    "status": true,
                    "createdAt": "2023-04-17T15:25:55.023427",
                    "updatedAt": "2023-04-17T15:30:44.096868"
                },
                "name": "바나나",
                "price": 3000,
                "qty": 50,
                "createdAt": "2023-04-17T15:40:03.662179",
                "updatedAt": null
            },
            {
                "id": 24,
                "seller": {
                    "id": 2,
                    "username": "seller",
                    "email": "ssar@nate.com",
                    "role": "SELLER",
                    "status": true,
                    "createdAt": "2023-04-17T15:25:55.023427",
                    "updatedAt": "2023-04-17T15:30:44.096868"
                },
                "name": "바나나",
                "price": 3000,
                "qty": 50,
                "createdAt": "2023-04-17T15:40:05.083647",
                "updatedAt": null
            }
        ],
        "pageable": {
            "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
            },
            "offset": 20,
            "pageNumber": 2,
            "pageSize": 10,
            "paged": true,
            "unpaged": false
        },
        "last": true,
        "totalPages": 3,
        "totalElements": 24,
        "size": 10,
        "number": 2,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "first": false,
        "numberOfElements": 4,
        "empty": false
    }
}
    ```

16. 상품상세보기
    - EAGER 전략으로, JOIN이 발생시 쿼리
    ```sql
    Hibernate:
    select
    product0_.id as id1_4_0_,
    product0_.created_at as created_2_4_0_,
    product0_.name as name3_4_0_,
    product0_.price as price4_4_0_,
    product0_.qty as qty5_4_0_,
    product0_.seller_id as seller_i7_4_0_,
    product0_.updated_at as updated_6_4_0_,
    user1_.id as id1_5_1_,
    user1_.created_at as created_2_5_1_,
    user1_.email as email3_5_1_,
    user1_.password as password4_5_1_,
    user1_.role as role5_5_1_,
    user1_.status as status6_5_1_,
    user1_.updated_at as updated_7_5_1_,
    user1_.username as username8_5_1_
    from
    product_tb product0_
    left outer join
    user_tb user1_
    on product0_.seller_id=user1_.id
    where
    product0_.id=?
    ```
    - LAZY 전략으로, JOIN 발생하지 않는다.
    - hibernateLazyInitializer 에러 발생 : 하이버네이트가 지연 로딩시 발생
    ```sql
    Hibernate: 
    select
        product0_.id as id1_4_0_,
        product0_.created_at as created_2_4_0_,
        product0_.name as name3_4_0_,
        product0_.price as price4_4_0_,
        product0_.qty as qty5_4_0_,
        product0_.seller_id as seller_i7_4_0_,
        product0_.updated_at as updated_6_4_0_ 
    from
        product_tb product0_ 
    where
        product0_.id=?
    ```
    ```sql
    Hibernate: 
    select
        user0_.id as id1_5_0_,
        user0_.created_at as created_2_5_0_,
        user0_.email as email3_5_0_,
        user0_.password as password4_5_0_,
        user0_.role as role5_5_0_,
        user0_.status as status6_5_0_,
        user0_.updated_at as updated_7_5_0_,
        user0_.username as username8_5_0_ 
    from
        user_tb user0_ 
    where
        user0_.id=?
    ```
    ```yaml
    {
    "status": 200,
    "msg": "성공",
    "data": {
    "id": 1,
    "seller": {
    "id": 2,
    "username": "seller",
    "email": "ssar@nate.com",
    "role": "SELLER",
    "status": true,
    "createdAt": "2023-04-17T15:50:31.935774",
    "updatedAt": null,
    "hibernateLazyInitializer"
    }
    }
    }{
    "status": 500,
    "msg": "unknownServerError",
    "data": "Type definition error: [simple type, class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor]; nested exception is com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: shop.mtcoding.metamall.dto.ResponseDTO[\"data\"]->shop.mtcoding.metamall.model.product.Product[\"seller\"]->shop.mtcoding.metamall.model.user.User$HibernateProxy$XdMe7J00[\"hibernateLazyInitializer\"])"
    }
    ```
    ```yaml
    #hibernateLazyInitializer 오류 해결법 (4)
    jackson:
    serialization:
    fail-on-empty-beans: false
    ```
    ```sql
    Hibernate: 
    select
        product0_.id as id1_4_0_,
        product0_.created_at as created_2_4_0_,
        product0_.name as name3_4_0_,
        product0_.price as price4_4_0_,
        product0_.qty as qty5_4_0_,
        product0_.seller_id as seller_i7_4_0_,
        product0_.updated_at as updated_6_4_0_ 
    from
        product_tb product0_ 
    where
        product0_.id=?
     ```
    ```sql
    Hibernate: 
    select
        user0_.id as id1_5_0_,
        user0_.created_at as created_2_5_0_,
        user0_.email as email3_5_0_,
        user0_.password as password4_5_0_,
        user0_.role as role5_5_0_,
        user0_.status as status6_5_0_,
        user0_.updated_at as updated_7_5_0_,
        user0_.username as username8_5_0_ 
    from
        user_tb user0_ 
    where
        user0_.id=?
    ```
    - hibernateLazyInitializer 해결법
    1. 서비스에서 직접 Lazy Loading 발동시키기
    2. Join Fetch로 변경
    3. Eager 전략으로 변경
    4. fail-on-empty-beans : false