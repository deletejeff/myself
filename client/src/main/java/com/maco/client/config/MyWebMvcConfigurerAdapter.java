package com.maco.client.config;

import com.maco.client.interceptor.AllHandlerInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@AllArgsConstructor
public class MyWebMvcConfigurerAdapter implements WebMvcConfigurer {
    public final AllHandlerInterceptor allHandlerInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(allHandlerInterceptor);
    }
}
