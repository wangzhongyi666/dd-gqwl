package com.dongdao.gqwl;

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
