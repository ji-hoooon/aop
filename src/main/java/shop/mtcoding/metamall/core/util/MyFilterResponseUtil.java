package shop.mtcoding.metamall.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import shop.mtcoding.metamall.core.exception.Exception400;
import shop.mtcoding.metamall.dto.ResponseDTO;
import shop.mtcoding.metamall.dto.ValidationDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//Filter는 예외 핸들러로 처리 불가능
//: 필터에서 인증처리와 권한처리시에 예외처리를 수행하는 메서드 유틸
public class MyFilterResponseUtil {
    public static void badRequest(HttpServletResponse resp, Exception400 e) throws IOException {
        resp.setStatus(400);
        resp.setContentType("application/json; charset=utf-8");
        ValidationDTO validationDTO = new ValidationDTO(e.getKey(), e.getValue());
        ResponseDTO<?> responseDto = new ResponseDTO<>().fail(HttpStatus.BAD_REQUEST, "badRequest", validationDTO);
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(responseDto);
        resp.getWriter().println(responseBody);
    }
    public static void unAuthorized(HttpServletResponse resp, Exception e) throws IOException {
        resp.setStatus(401);
        resp.setContentType("application/json; charset=utf-8");
        ResponseDTO<?> responseDto = new ResponseDTO<>().fail(HttpStatus.UNAUTHORIZED, "unAuthorized", e.getMessage());
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(responseDto);
        resp.getWriter().println(responseBody);
    }

    public static void forbidden(HttpServletResponse resp, Exception e) throws IOException {
        resp.setStatus(403);
        resp.setContentType("application/json; charset=utf-8");
        ResponseDTO<?> responseDto = new ResponseDTO<>().fail(HttpStatus.FORBIDDEN, "forbidden", e.getMessage());
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(responseDto);
        resp.getWriter().println(responseBody);
    }
}