package shop.mtcoding.metamall.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//PathVariable의 id와 세션의 id 값을 비교해서 권한 체크
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MySameUserIdCheck {
}

