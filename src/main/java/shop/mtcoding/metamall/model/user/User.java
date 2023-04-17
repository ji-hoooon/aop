package shop.mtcoding.metamall.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter // DTO 만들면 삭제해야됨 -> 엔티티에는 setter가 존재하면 안 된다.
@Getter
@Table(name = "user_tb")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    //유니크 제약조건, 255자가 기본값
    private String username;

    @JsonIgnore //서비스가 없을 때는 DTO가 아닌 엔티티로 반환을 하기 때문에 패스워드는 무시하도록
    @Column(nullable = false, length = 60)  //단방향 암호화시 60바이트로 변환된다.
    private String password;
    @Column(nullable = false, length = 50)
    private String email;

//    @Enumerated(EnumType.STRING)  //Enum을 문자열로 받기위한 어노테이션
    @Column(nullable = false, length = 10)  //컬럼은 일반적으로 여유를 남겨서 설정한다.
    private String role; // USER(고객), SELLER(판매자), ADMIN(관리자)
    //추후에 Enum으로 변경

    //사용자의 활성여부
    @Column(nullable = false, length = 10)
    private Boolean status; //true 활성, false 비활성계정


    //update는 처음에는 null일 수 있으므로 created에만 nullable=false 설정
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //User와 관련된 메서드 작성 - 객체 상태 변경

    //권한 변경 (관리자)
    //: 관리자 권한 설정하는 메서드로 setter를 사용하지 않고, 의미있는 메서드를 작성한다.
    public void updateRole(String role){
        if(this.role.equals(role)){
            //checkpoint : throw 동일한 권한으로 변경할 수 없습니다.
        }
        this.role=role;
    }

    //회원 탈퇴
    public void delete(){
        this.status=false;
    }



    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


    //빌더 패턴
    @Builder
    public User(Long id, String username, String password, String email, String role, Boolean status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
