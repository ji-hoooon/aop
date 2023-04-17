package shop.mtcoding.metamall.core.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import shop.mtcoding.metamall.core.annotation.MyErrorLogRecord;
import shop.mtcoding.metamall.core.exception.*;
import shop.mtcoding.metamall.dto.ResponseDTO;
import shop.mtcoding.metamall.model.log.error.ErrorLogRepository;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class MyExceptionAdvice {

//    private final ErrorLogRepository errorLogRepository;
    //: AOP로 대체 -> @MyErrorLogRecord

    @MyErrorLogRecord
    @ExceptionHandler(Exception400.class)
    public ResponseEntity<?> badRequest(Exception400 e){
        //로그 레벨에 따른 출력 범위 차이
        //개발시에는 debug, 운영시에는 info
        //: trace -> debug -> info -> warn -> error
        log.debug("디버그 : "+e.getMessage());
        log.info("인포 : "+e.getMessage());
        log.warn("경고: "+e.getMessage());
        log.error("에러: "+e.getMessage());
        log.trace("추적 : "+e.getMessage());
        return new ResponseEntity<>(e.body(), e.status());
    }

    @MyErrorLogRecord
    @ExceptionHandler(Exception401.class)
    public ResponseEntity<?> unAuthorized(Exception401 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @MyErrorLogRecord
    @ExceptionHandler(Exception403.class)
    public ResponseEntity<?> forbidden(Exception403 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

//    @ExceptionHandler(Exception404.class)
//    public ResponseEntity<?> notFound(Exception404 e){
//        return new ResponseEntity<>(e.body(), e.status());
//    }
//
//    @ExceptionHandler(Exception500.class)
//    public ResponseEntity<?> serverError(Exception500 e){
//        return new ResponseEntity<>(e.body(), e.status());
//    }

    //어떠한 예외도 처리할 수 있도록
    @MyErrorLogRecord
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> notFound(NoHandlerFoundException e){
//        return new ResponseEntity<>(e.body(), e.status());
        //직접 작성
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        responseDTO.fail(HttpStatus.NOT_FOUND, "notFound", e.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
        //커스텀 예외 작성하지 않았으므로 직접 작성
    }

    //모든 예외처리
    //:모든 예외의 부모 처리 - 알 수 없는 예외로 로그처리 필수
    //checkpoint : AOP로 로그처리
    @MyErrorLogRecord
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> serverError(Exception e){
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        responseDTO.fail(HttpStatus.INTERNAL_SERVER_ERROR, "unknownServerError", e.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
        //커스텀 예외 작성하지 않았으므로 직접 작성
    }
}
