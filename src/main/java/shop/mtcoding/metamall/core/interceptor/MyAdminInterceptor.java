package shop.mtcoding.metamall.core.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import shop.mtcoding.metamall.core.exception.Exception403;
import shop.mtcoding.metamall.core.session.SessionUser;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
public class MyAdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");

        if(!sessionUser.getRole().equals("ADMIN")){
            throw new Exception403("권한이 없습니다");
        }
        return true;
    }
}