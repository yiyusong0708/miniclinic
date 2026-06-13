package tw.edu.fju.miniclinic.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.lang.NonNull; // 👈 匯入這個
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, 
                             @NonNull HttpServletResponse response, 
                             @NonNull Object handler) throws Exception { // 👈 三個參數都加上 @NonNull
        
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedInDoctorId") != null) {
            return true;
        }

        String uri = request.getRequestURI();
        if (uri.startsWith("/api/")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"message\": \"Login required (401)\"}");
        } else {
            response.sendRedirect("/login");
        }
        return false;
    }
}