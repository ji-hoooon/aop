package shop.mtcoding.metamall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.metamall.core.exception.Exception400;
import shop.mtcoding.metamall.core.exception.Exception401;
import shop.mtcoding.metamall.core.jwt.JwtProvider;
import shop.mtcoding.metamall.dto.ResponseDTO;
import shop.mtcoding.metamall.dto.user.UserRequestDTO;
import shop.mtcoding.metamall.dto.user.UserResponseDTO.JoinRespDTO;
import shop.mtcoding.metamall.model.log.login.LoginLog;
import shop.mtcoding.metamall.model.log.login.LoginLogRepository;
import shop.mtcoding.metamall.model.user.User;
import shop.mtcoding.metamall.model.user.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final LoginLogRepository loginLogRepository;
    private final HttpSession session;

    //회원가입 핸들러 메서드
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequestDTO.JoinReqDTO joinReqDTO, Errors errors) {

        //매번 빌더패턴을 이용해서 엔티티 생성해야하는 불편함
//        User user = User.builder()
//                .username(joinReqDTO.getUsername())
//                .password(joinReqDTO.getPassword())
//                .email(joinReqDTO.getEmail())
//                .role(joinReqDTO.getRole())
//                .status(true)
//                .build();
        //: DTO에 toEntity()를 미리 작성해둔다.
        //REST API의 경우 INSERT, UPDATE, SELECT 모두 데이터를 응답한다
//        User userPS = userRepository.save(joinReqDTO.toEntity());

        JoinRespDTO joinRespDTO = new JoinRespDTO(userRepository.save(joinReqDTO.toEntity()));


        //기능상으로 같은 코드
//        return ResponseEntity.ok().body(responseDto);
//        return ResponseEntity.ok(responseDto);
        //첫 번째 코드에서는 ok() 메소드의 반환 값으로 생성된 ResponseEntity 객체의 body() 메소드를 호출하여 응답 바디를 설정
        //두 번째 코드에서는 생성된 ResponseEntity 객체를 직접 반환할 때, ok() 메소드의 인자로 응답 바디를 전달함으로써 응답 바디를 설정

        // ResponseDTO<?> responseDto = new ResponseDTO<>().data(joinRespDTO);
        //: 두 문장을 합친 리턴문
        return new ResponseEntity<>(new ResponseDTO<>(1, "회원가입 완료", joinRespDTO), HttpStatus.OK);
    }

    //로그인 핸들러 메서드
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequestDTO.LoginReqDTO loginReqDto, Errors errors,
                                   HttpServletRequest request) {
        Optional<User> userOP = userRepository.findByUsername(loginReqDto.getUsername());
        if (userOP.isPresent()) {
            // 1. 유저 정보 꺼내기
            User loginUser = userOP.get();

            // 2. 패스워드 검증하기
            if(!loginUser.getPassword().equals(loginReqDto.getPassword())){
                throw new Exception401("인증되지 않았습니다");
            }

            // 3. JWT 생성하기
            String jwt = JwtProvider.create(userOP.get());

            // 4. 최종 로그인 날짜 기록 (더티체킹 - update 쿼리 발생)
            loginUser.setUpdatedAt(LocalDateTime.now());

            // 5. 로그 테이블 기록
            LoginLog loginLog = LoginLog.builder()
                    .userId(loginUser.getId())
                    .userAgent(request.getHeader("User-Agent"))
                    .clientIP(request.getRemoteAddr())
                    .build();
            loginLogRepository.save(loginLog);

            // 6. 응답 DTO 생성
            ResponseDTO<?> responseDto = new ResponseDTO<>().data(loginUser);
            return ResponseEntity.ok().header(JwtProvider.HEADER, jwt).body(responseDto);
        } else {
            throw new Exception400("userError","유저네임 혹은 아이디가 잘못되었습니다");
            //checkpoint : 400에러
        }
    }
}
