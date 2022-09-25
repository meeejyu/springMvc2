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
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    /*
     * HTTP 요청이 오면 doFilter이 호출된다
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

    @Override
    public void destroy() {
        log.info("log filter destory");
    }
    

}
