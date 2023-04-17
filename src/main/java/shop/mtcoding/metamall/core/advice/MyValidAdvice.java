package shop.mtcoding.metamall.core.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import shop.mtcoding.metamall.core.exception.Exception400;

//컨트롤러의 BindingResult를 Errors로 대체
//: 어노테이션으로 유효성 검사
//즉 BindingResult의 부모가 Errors

@Aspect
@Component
public class MyValidAdvice {
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMapping() {
    }

    @Before("postMapping() || putMapping()")
    //Around는 앞뒤 체크 -> 여기서는 앞에서만 체크하면 됨.
    public void validationAdvice(JoinPoint jp) {
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            if (arg instanceof Errors) {
                Errors errors = (Errors) arg;
                //파라미터에 에러가 존재할 때만 체크
                //: Error의 자손인 Exception
                if (errors.hasErrors()) {
                    throw new Exception400(
                            errors.getFieldErrors().get(0).getField(),
                            errors.getFieldErrors().get(0).getDefaultMessage()
                    );
                }
            }
        }
    }
}