package com.example.project_v2._core.config;

import com.example.project_v2._core.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration // IoC
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/boards/{id:\\d+}/detail", "/api/resumes/{id:\\d+}/detail", "/api/resumes");
    }

    // 외부 폴더에서 이미지파일 리소스를 가져오는 방법
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry.addResourceHandler("/api/user/**") // user 정보를 수정 할때 개방 폴더를 찾기
                .addResourceLocations("file:./images/")// 파일 경로 수정 및 해당 폴더 개방
                .setCachePeriod(60*60)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}
