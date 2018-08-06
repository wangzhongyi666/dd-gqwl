package com.e365.flexiblebe;

import com.e365.flexiblebe.interceptor.AuthInterceptor;
import com.e365.flexiblebe.interceptor.ExceptionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 注册拦截器
 */

public class SessionConfiguration extends WebMvcConfigurerAdapter {


     public void addInterceptors(InterceptorRegistry registry){
        /* super.addInterceptors(registry);
         registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/flexiblebe/**");
         registry.addInterceptor(new ExceptionInterceptor()).addPathPatterns("/flexiblebe/**");*/
     }
}
