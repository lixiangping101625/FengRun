package com.ruoyi.framework.config;

import com.ruoyi.framework.interceptor.PermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 配置拦截器
 */
@Configuration
public class SpringMVCConfig extends WebMvcConfigurationSupport{

  @Bean
  public PermissionInterceptor getPermissionInterceptor() {
    return new PermissionInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    InterceptorRegistration ir = registry.addInterceptor(getPermissionInterceptor());
    ir.addPathPatterns("/**")
      .excludePathPatterns("/smsCode")
      .excludePathPatterns("/smsLogin")
      .excludePathPatterns("/swagger-resources/**", "/static/**","/img/*", "/webjars/**", "/v2/**", "/swagger-ui.js/**");
      super.addInterceptors(registry);
  }

  @Override
  protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/resources/")
            .addResourceLocations("classpath:/static/")
            .addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("swagger-ui.js")
            .addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    super.addResourceHandlers(registry);
  }

  @Override
  protected void addCorsMappings(CorsRegistry registry) {
    super.addCorsMappings(registry);
    registry.addMapping("/**")
            .allowedOrigins("*");
  }

}