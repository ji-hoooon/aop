package shop.mtcoding.metamall.core.session;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginUser {
    //이후로, 세션 유저를 로그인 유저로 바꾼다.
    private Long id;
    private String role;

    @Builder
    public LoginUser(Long id, String role) {
        this.id = id;
        this.role = role;
    }
}
