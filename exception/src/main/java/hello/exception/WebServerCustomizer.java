package hello.exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


// 서블릿 컨테이너가 제공하는거
// @Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory>{

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        
        // 스프링부트가 제공하는 에러 페이지
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404"); // 해당 컨트롤러 호출
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");

        ErrorPage erroPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");
        factory.addErrorPages(errorPage404, errorPage500, erroPageEx);
        
    }

    /*
    * WAS -> 필터 -> 서블릿 -> 인터셉터 -> 컨트롤러(예외발생)
      -> 인터셉터 -> 서블릿 -> 필터 -> WAS '/error-page/500' 다시 요청 -> 필터
      -> 서블릿 -> 컨트롤러(/error-page/500) -> View  
    */
    
}

/*
 * 오류에 대해서 개발자는 오류 페이지만 등록하면됨
 * BasicErrorController에 기본적인 로직이 모두 개발되어 있다.
 * 
 * 뷰 선택 우선순위
 * 1. 뷰 템플릿
 *    resources/templates/error/여기
 *    ex)
 *    resources/templates/error/500.html 
 * 2. 정적 리소스(static, public)
 *    resources/static/error/여기
 *    ex)
 *    resources/static/error/400.ㅗ싀
 * 3. 적용 대상이 없을 대 뷰 이름 error
 *    resources/templates/error.html
 */
