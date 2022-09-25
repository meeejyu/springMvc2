package hello.login.web.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// 필터를 사용하려면 필터 인터페이스를 구현해야 한다.
public class LogFilter implements Filter{
    
    // 필터 초기화 메서드, 서블릿 컨테이너가 생성될 때 호출
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    /*
     * HTTP 요청이 오면 doFilter이 호출된다
     * 고객의 요청이 올 때마다 해당 메소드가 호출된다. 필터의 로직을 구현하면 된다
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        log.info("log filter doFilter");

        // ServletRequest는 HTTP 요청이 아닌 경우까지 고려해서 만든 인터페이스 이므로
        // HttpServletRequest로 다운 캐스팅 하면 된다
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString();

        try {
            log.info("REQUEST [{}] [{}", uuid, requestURI);
            // 다음 필터가 있으면 필터를 호출하면 없으면 필터가 없으면 서블릿을 호출한다
            // 이 로직을 호출하지 ㅇ낳으면 다음 단계로 진행되지 않는다.
            chain.doFilter(request, response);            
        } catch (Exception e) {
            throw e;
        }
        finally {
            log.info("RESPONSE [{}] [{}]", uuid, requestURI);
        }
    }

    // 필터 종료 메서드, 서블릿 컨테이너가 종료될 때 호출된다
    @Override
    public void destroy() {
        log.info("log filter destory");
    }
}

/**
 * 서블릿 필터
 *      필터 흐름
 *       - HTTP 요청 -> WAS -> 필터 -> 서블릿 -> 컨트롤러
 *      필터 제한
 *       - HTTP 요청 -> WAS -> 필터 -> 서블릿 -> 컨트롤러 // 로그인 사용자
 *       - HTTP 요청 -> WAS -> 필터(적절하지 않은 요청이라 판단, 서블릿 호출 x) // 비 로그인 사용자
 */
