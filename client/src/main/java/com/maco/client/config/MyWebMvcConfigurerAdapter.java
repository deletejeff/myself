package com.maco.client.config;

import com.maco.client.interceptor.AllHandlerInterceptor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Component
@AllArgsConstructor
public class MyWebMvcConfigurerAdapter implements WebMvcConfigurer {
    public final AllHandlerInterceptor allHandlerInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(allHandlerInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/**/*.html",            //html静态资源
                        "/**/*.js",              //js静态资源
                        "/**/*.css",             //css静态资源
                        "/**/*.woff",
                        "/**/*.ttf",
                        "/**/*.jpg",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.ico",
                        "/**/*.eot",
                        "/**/*.svg"
                );
    }
}
