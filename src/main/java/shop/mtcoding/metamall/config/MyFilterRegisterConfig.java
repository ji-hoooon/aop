package shop.mtcoding.metamall.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shop.mtcoding.metamall.core.filter.JwtVerifyFilter;


@Configuration
public class MyFilterRegisterConfig {

    @Bean
    public FilterRegistrationBean<?> jwtVerifyFilterAdd() {
        FilterRegistrationBean<JwtVerifyFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new JwtVerifyFilter());
        //인증 체크하는 필터
        registration.addUrlPatterns("/users/*");    //토큰 체크
        registration.addUrlPatterns("/products/*"); //토큰 체크
        registration.addUrlPatterns("/orders/*");   //토큰 체크
        //인터셉터로 구현하는 권한 체크
        registration.addUrlPatterns("/admin/*");    //토큰 체크, 권한 체크
        registration.addUrlPatterns("/seller/*");   //토큰 체크, 권한 체크
        registration.setOrder(1);
        return registration;
    }

}
