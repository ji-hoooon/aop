package shop.mtcoding.metamall.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.metamall.core.util.MyDateUtil;
import shop.mtcoding.metamall.model.user.User;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

// 응답 DTO는 서비스 배우고 나서 하기 (할 수 있으면 해보기)
public class UserResponseDTO {
    @Getter
    @Setter
    public static class JoinRespDTO {
        private Long id;
        //유니크 제약조건, 255자가 기본값
        private String username;
        private String email;
        private String role; // USER(고객), SELLER(판매자), ADMIN(관리자)
        //추후에 Enum으로 변경
        private String status; //true 활성, false 비활성계정
        private String createdAt;
        private String updatedAt;

        public JoinRespDTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.role =user.getRole();
            this.status = user.getStatus()+"";
//            this.createdAt = user.getCreatedAt();
            this.createdAt = MyDateUtil.toStringFormat(user.getCreatedAt());
        }
    }

}
