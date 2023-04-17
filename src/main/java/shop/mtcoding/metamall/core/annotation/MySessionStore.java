package shop.mtcoding.metamall.core.annotation;

import org.springframework.web.bind.annotation.GetMapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//세션값을 자동 주입하는 어노테이션
//@GetMapping("/")
//public String ok(@MySessionStore User user){
//
//}

//MySessionArgumentResolver로 구현
//: SessionUser를 MySessionArgumentResolver으로 주입
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface MySessionStore {
}