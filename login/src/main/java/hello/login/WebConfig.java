package hello.login;

import java.util.List;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import hello.login.web.argumentresolver.LoginMemberArgumentResolver;
import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;


/**
 * ServletComponentScan @WebFilter(filterName="logFilter", urlPatterns = "/*")로
 * 필터 등록이 가능하지만 필터 순서 조절이 안된다, 따라서 FilterRegistrationBean을 사용하자
 */

@Configuration
// 필터를 사용하려면 빈을 등록해줘야한다
public class WebConfig implements WebMvcConfigurer {
    
    // @Bean
    public FilterRegistrationBean logFilter() {
     
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        
        // 등록할 필터를 지정한다.
        filterRegistrationBean.setFilter(new LogFilter());
        
        // 필터는 체인으로 동작한다. 순서를 지정해줌. 낮을수록 먼저 동작
        filterRegistrationBean.setOrder(1);

        // 필터를 적용할 URL 패턴 지정
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    // @Bean
    public FilterRegistrationBean loginCheckFilter() {
        
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*"); // 모든 요청에 로그인 필터를 적용, 이미 걸렀기 때문에 상관없음
        return filterRegistrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LogInterceptor()) // 인터셉터 등록
                .order(1) // 순서
                .addPathPatterns("/**") // 인터셉터를 적용할 패턴, 패턴 공식문서 참고
                //  https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/ springframework/web/util/pattern/PathPattern.html
                .excludePathPatterns("/css/**", "/*.ico", "/error"); // 인터셉터에서 제외할 패턴

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/add", "/login", "/logout",
                                    "/css/**", "/*.ico", "/error");       
        
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }



















}
