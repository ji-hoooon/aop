package shop.mtcoding.metamall.core.session;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SessionUser {
    private Long id;
    private String role;

    @Builder
    public SessionUser(Long id, String role) {
        this.id = id;
        this.role = role;
    }
}
