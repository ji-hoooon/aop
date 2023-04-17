package shop.mtcoding.metamall.dto.user;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.metamall.model.user.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequestDTO {
    @Getter @Setter
    public static class LoginReqDTO {
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;
    }

    @Getter @Setter
    public static class JoinReqDTO {
        @NotEmpty
        @Size(min = 3, max = 20)
        private String username;
        @NotEmpty
        @Size(min = 4, max = 12)
        private String password;
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식이 아닙니다.")
        //정규 표현식
        private String email;

        @NotEmpty
        @Pattern(regexp = "USER|SELLER|ADMIN")
        private String role;


        //insert
        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .status(true)
                    .build();
        }
    }
    @Getter
    @Setter
    //관리자만 상태를 변경할 수 있는 DTO
    public static class RoleUpdateReqDTO{
        @NotEmpty
        private String role;
    }
}
