package shop.mtcoding.metamall.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shop.mtcoding.metamall.core.interceptor.MyAdminInterceptor;
import shop.mtcoding.metamall.core.interceptor.MySellerInterceptor;
import shop.mtcoding.metamall.core.resolver.MySessionArgumentResolver;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    private final MyAdminInterceptor adminInterceptor;
    private final MySellerInterceptor sellerInterceptor;
    private final MySessionArgumentResolver mySessionArgumentResolver;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*") // GET, POST, PUT, DELETE (Javascript 요청 허용)
                .allowedOriginPatterns("*") // 모든 IP 주소 허용 (프론트 앤드 IP만 허용하게 변경해야함. * 안됨)
                .allowCredentials(true)
                .exposedHeaders("Authorization"); // 옛날에는 디폴트로 브라우저에 노출되어 있었는데 지금은 아님
    }


    // AOP는 매개변수 값 확인해서 권한 비교해야할 때 사용
    // Interceptor는 세션 권한으로 체크할 때 사용
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**");

        registry.addInterceptor(sellerInterceptor)
                .addPathPatterns("/seller/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(mySessionArgumentResolver);
    }
}