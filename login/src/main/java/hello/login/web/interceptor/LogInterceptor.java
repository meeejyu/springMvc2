package hello.login.web.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    
    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        
        String requestURI = request.getRequestURI();

        // LogInterceptor도 싱글톤처럼 사용되기 때문에 멤버변수를 사용하면 위험하다, 다른요청이 오면 값이 바껴버림
        String uuid = UUID.randomUUID().toString(); // 지역변수로 사용하기 어렵기 때문에 request에 담아줌
        request.setAttribute(LOG_ID, uuid); // uuid를 넘겨줘야됨

        // @RequestMapping : 핸들러 정보로 HandlerMethod가 넘어온다
        // 정적 리소스 : ResourceHttpRequestHandler가 호출되는데 타입에 따라서 처리가 필요하다
        if(handler instanceof HandlerMethod) { 
            HandlerMethod hm = (HandlerMethod) handler; // 호출할 컨트롤러의 메서드의 모든 정보가 포함되어 있다
        }

        log.info("REQUEST [{}] [{}", uuid, requestURI);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler, ModelAndView modelAndView) throws Exception {
            log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex) throws Exception {

        String requestURI = request.getRequestURI();
        String logId = (String)request.getAttribute(LOG_ID);
        log.info("RESPONSE [{}] [{}]", logId, requestURI);
        if(ex != null) {
            log.error("afterCompletion error!!", ex);
        }
    }


}

/**
 * 스프링 인터셉터
 * 서블릿 필터는 서블릿이 제공하는 기술
 * 스프링 인터셉터는 스프링 MVC가 제공하는 기술
 * 
 *      스프링 인터셉터
 *       - HTTP 요청 -> WAS -> 필터 -> 서블릿 -> 스프링 인터셉터 -> 컨트롤러
 *      스프링 인터셉터 제한
 *       - HTTP 요청 -> WAS -> 서블릿 -> 스프링 인터셉터 -> 컨트롤러 // 로그인 사용자
 *       - HTTP 요청 -> WAS -> 서블릿 -> 스프링 인터셉터(적절하지 않은 요청이라 판단, 컨트롤러 호출X) // 비 로그인 사용자
 *      스프링 인터셉터 체인
 *       - HTTP 요청 -> WAS -> 필터 -> 서블릿 -> 인터셉터1 -> 인터셉터2 -> 컨트롤러
 * 
 *      스프링 인터셉터 정상 흐름
 *       - preHandle : 컨트롤러 호출 전에 호출
 *          preHandle의 응답값이 true이면 다음으로 진행, false이면 더는 진행X
 *       - postHandle : 컨트롤러의 호출 후 호출
 *       - afterCompletion : 뷰가 렌더링 된 이후에 호출
 * 
 *      스프링 인터셉터 예외 상황
 *       - preHandle : 컨트롤러 호출 전에 호출
 *       - postHandle : 컨트롤러에서 예외가 발생하면 postHandle은 호출되지 않는다
 *       - afterCompletion : 항상 호출된다 예외(ex)를 파라티러로 받아서 어떤 예외가 발생했는지 로그로 출력할 수 있다.
 */
