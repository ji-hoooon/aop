package shop.mtcoding.metamall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.mtcoding.metamall.core.exception.Exception400;
import shop.mtcoding.metamall.dto.ResponseDTO;
import shop.mtcoding.metamall.dto.user.UserRequestDTO;
import shop.mtcoding.metamall.model.user.User;
import shop.mtcoding.metamall.model.user.UserRepository;

import javax.validation.Valid;

/**
 * 권한 변경
 */
@RequiredArgsConstructor
@RestController
public class AdminController {
    private final UserRepository userRepository;

    @Transactional // 트랜잭션이 시작되지 않으면 강제로 em.flush() 를 할 수 없고, 더티체킹도 할 수 없다. (원래는 서비스에서)
    @PutMapping("/admin/user/{id}/role")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody @Valid UserRequestDTO.RoleUpdateReqDTO roleUpdateReqDTO, Errors errors) {

        User userPS = userRepository.findById(id)
                .orElseThrow(() -> new Exception400("id", "해당 유저를 찾을 수 없습니다"));
        userPS.updateRole(roleUpdateReqDTO.getRole());

        ResponseDTO<?> responseDto = new ResponseDTO<>();
        return ResponseEntity.ok().body(responseDto);
    }
}
