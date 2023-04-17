package shop.mtcoding.metamall.core.advice;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import shop.mtcoding.metamall.core.session.SessionUser;
import shop.mtcoding.metamall.model.log.error.ErrorLog;
import shop.mtcoding.metamall.model.log.error.ErrorLogRepository;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Aspect
@Component
//myErrorLog 어노테이션이 붙어있을 때 발동
//: 세션과 비교해서 아이디가 같은지 여부를 체크한다.
public class MyErrorLogAdvice {

    private final HttpSession session;
    private final ErrorLogRepository errorLogRepository;

    @Pointcut("@annotation(shop.mtcoding.metamall.core.annotation.MyErrorLogRecord)")
    public void myErrorLog(){}

    @Before("myErrorLog()")
    public void errorLogAdvice(JoinPoint jp) throws HttpMessageNotReadableException {
        Object[] args = jp.getArgs();

        for (Object arg : args) {
            //매개변수를 돌면서 Exception이 존재하는지 체크한다.
            //: Exception의 자식까지 모두 확인
            if(arg instanceof Exception){
                Exception e = (Exception) arg;
                SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
                if(sessionUser != null){
                    ErrorLog errorLog =ErrorLog.builder().userId(sessionUser.getId()).msg(e.getMessage()).build();
                    //에러 로그의 아이디, 에러 로그 메시지를 전달해 객체 생성
                    errorLogRepository.save(errorLog);
                }
            }
        }
    }
}