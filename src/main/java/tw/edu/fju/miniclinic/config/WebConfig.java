package tw.edu.fju.miniclinic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull; // 1. 引入 Spring 的 NonNull
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tw.edu.fju.miniclinic.interceptor.LoginRequiredInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginRequiredInterceptor loginInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) { // 2. 這裡加上 @NonNull
        
        // 3. 確保 loginInterceptor 不是 null 才塞進去，徹底消除 IDE 的憂慮
        if (loginInterceptor != null) {
            registry.addInterceptor(loginInterceptor)
                .addPathPatterns(
                    "/dashboard",
                    "/dashboard/**",
                    "/api/auth/me",
                    "/api/appointments/*/status",
                    "/password"
                )
                .excludePathPatterns(
                    "/login",
                    "/logout"
                );
        }
    }
}