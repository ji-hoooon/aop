package shop.mtcoding.metamall.core.advice;


import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import shop.mtcoding.metamall.core.annotation.MySessionStore;
import shop.mtcoding.metamall.core.exception.Exception403;
import shop.mtcoding.metamall.core.session.SessionUser;
import shop.mtcoding.metamall.model.log.error.ErrorLog;
import shop.mtcoding.metamall.model.user.User;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Aspect
@Component
public class MySameUserIdAdvice {

    private final HttpSession session;

    // 깃발에 별칭주기
    @Pointcut("@annotation(shop.mtcoding.metamall.core.annotation.MySameUserIdCheck)")
    public void mySameUserId(){}

    @Before("mySameUserId()")
    public void sameUserIdAdvice(JoinPoint jp) {
        Object[] args = jp.getArgs();
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();

        IntStream.range(0, parameters.length).forEach(
                (i) -> {
                    if(parameters[i].getName().equals("id") && parameters[i].getType() == Long.class){
                        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
                        Long id = (Long) args[i];
                        if(sessionUser.getId() != id){
                            throw new Exception403("해당 id에 접근할 권한이 없습니다");
                        }
                    }
                }
        );
    }
}