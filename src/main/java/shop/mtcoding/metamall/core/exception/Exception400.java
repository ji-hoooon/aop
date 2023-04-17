package shop.mtcoding.metamall.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.mtcoding.metamall.dto.ResponseDTO;
import shop.mtcoding.metamall.dto.ValidationDTO;


// 유효성 실패, 잘못된 파라미터 요청시
@Getter
public class Exception400 extends RuntimeException {
    private String key;
    private String value;

//    public Exception400(String message) {
//        super(message);
//    }

    public Exception400(String key, String value) {
        super(value);
        this.key = key;
        this.value = value;
    }

    public ResponseDTO<?> body(){
//        ResponseDTO<String> responseDto = new ResponseDTO<>();
//        responseDto.fail(HttpStatus.BAD_REQUEST, "badRequest", getMessage());

//      getMessage() 대신 key, value 데이터로 전달하기 위해 ValidationDTO 작성
        ResponseDTO<ValidationDTO> responseDto = new ResponseDTO<>();
        ValidationDTO validationDTO=new ValidationDTO(key, value);
        responseDto.fail(HttpStatus.BAD_REQUEST, "badRequest", validationDTO);
        return responseDto;
    }

    public HttpStatus status(){
        return HttpStatus.BAD_REQUEST;
    }
}