package hello.exception;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import hello.exception.filter.LogFilter;
import hello.exception.interceptor.LogInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/error-page/**"); // 오류페이지 경로 추가
    }
    /*
     * /error-ex 요청
     * 필터는 DispatchType으로 중복 호출 제거(dispathchType=REQUEST)
     * 인터셉터는 경로 정보로 중복 호출 제거 (excludePathPatterns("error-page/**"))
     */

    // @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);
        // 기본 값이 DispatcherType.REQUEST이다. 오류 페이지도 필터에 적용할 것이 아니면 그대로 사용하면 된다 
        
        return filterRegistrationBean;
    }
}
