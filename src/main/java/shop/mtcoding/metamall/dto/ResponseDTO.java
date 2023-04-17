package shop.mtcoding.metamall.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import shop.mtcoding.metamall.model.user.User;

@Getter
public class ResponseDTO<T> {
    //응답 DTO 작성하는 3가지 방법
    //(1) 응답이 존재하지 않는 요청 성공 - DELETE
//    ResponseDTO<User> v1 = new ResponseDTO<>();

    //(2) 응답이 존재하는 요청 성공 - GET, POST, PUT
//    ResponseDTO<User> v2 = new ResponseDTO<>().data("값");

    //(3) 요청 실패 - GET, POST, PUT, DELETE
//    ResponseDTO<User> v3 = new ResponseDTO<>().fail();

    private Integer status; // 에러시에 의미 있음.
    private String msg; // 에러시에 의미 있음. ex) badRequest
    private T data; // 에러시에는 구체적인 에러 내용 ex) username이 입력되지 않았습니다

    //delete 요청의 성공 응답에서 데이터가 없는 DTO
    public ResponseDTO(){
        this.status = HttpStatus.OK.value();
        this.msg = "성공";
        this.data = null;
    }


    //get, post, put 요청의 성공 응답에서 데이터를 본문으로 가지는 DTO
    public ResponseDTO(Integer status, String msg, T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ResponseDTO<?> data(T data){
        this.data = data; // 응답할 데이터 바디
        return this;
    }

    //4가지 HTTP 메서드 요청 실패시 응답하는 DTO
    public ResponseDTO<?> fail(HttpStatus httpStatus, String msg, T data){
        this.status = httpStatus.value();
        this.msg = msg; // 에러 제목
        this.data = data; // 에러 내용
        return this;
    }
}
